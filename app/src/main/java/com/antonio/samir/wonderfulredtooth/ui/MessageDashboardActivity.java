package com.antonio.samir.wonderfulredtooth.ui;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.antonio.samir.wonderfulredtooth.R;

public class MessageDashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_dashboard);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.id_message_fragment, new MessageFragment());
        transaction.addToBackStack(null);

        transaction.commit();

    }


}
