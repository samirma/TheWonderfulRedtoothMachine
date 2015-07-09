package com.antonio.samir.wonderfulredtooth.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.antonio.samir.wonderfulredtooth.R;
import com.antonio.samir.wonderfulredtooth.network.ListernerBluetoothThread;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.ProxyManager;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.ProxyManagerBluetooth;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.ProxyManagerHandle;

import java.io.IOException;

public class MainActivity extends Activity implements ProxyManagerHandle {

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private static final String TAG = "MainActivity";

    private Button connectButton;

    private Button startProxy;

    private TextView listening;

    private ProxyManager proxyManager;

    private Switch autoproxy;

    private ListernerBluetoothThread bluetoothThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proxyManager = new ProxyManagerBluetooth(this);

        bindVariables();

        bluetoothThread = new ListernerBluetoothThread(proxyManager);

        bluetoothThread.start();

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);

            }
        });

        startProxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                proxyManager.doProxy();

            }
        });

    }

    private void bindVariables() {
        connectButton = (Button) findViewById(R.id.connect_btn);

        startProxy = (Button) findViewById(R.id.id_start_proxy_btn);

        listening = (TextView) findViewById(R.id.id_listening_label);

        autoproxy = (Switch) findViewById(R.id.id_auto_proxy);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case REQUEST_CONNECT_DEVICE_SECURE:
                    // When DeviceListActivity returns with a device to connect
                    if (resultCode == Activity.RESULT_OK) {

                        connectDevice(data, true);

                    }
                    break;
                case REQUEST_CONNECT_DEVICE_INSECURE:
                    // When DeviceListActivity returns with a device to connect
                    if (resultCode == Activity.RESULT_OK) {
                        connectDevice(data, false);
                    }
                    break;
                case REQUEST_ENABLE_BT:
                    // When the request to enable Bluetooth returns
                    if (resultCode == Activity.RESULT_OK) {
                        // Bluetooth is now enabled, so set up a chat session
//                    setupChat();
                    } else {
                        // User did not enable Bluetooth or an error occurred
                        Log.d(TAG, "BT not enabled");
                        Toast.makeText(this, R.string.bt_not_enabled_leaving,
                                Toast.LENGTH_SHORT).show();
                        this.finish();
                    }
            }
        } catch (IOException e) {
            Log.e(TAG, "Fail to connect to device", e);
        }
    }

    private void connectDevice(final Intent data, final boolean secure) throws IOException {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        proxyManager.connectToAdress(address, secure);


    }


    @Override
    public void readyToStart() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (autoproxy.isChecked()) {
                    proxyManager.doProxy();
                } else {
                    startProxy.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void startPointReady() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listening.setText(getString(R.string.client_connected));
            }
        });

    }

    @Override
    public void endPointReady() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                connectButton.setText(String.format("Connected"));

                connectButton.setClickable(false);

                startProxy.setVisibility(View.INVISIBLE);

            }
        });


    }

    @Override
    public void proxyBroken() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectButton.setText(String.format("Try to connect again"));

                connectButton.setClickable(true);

                listening.setText("Conenction lost");
            }
        });
    }

    @Override
    public void proxyStarted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, R.string.proxy_started,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
