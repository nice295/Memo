package com.nice295.memo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

/**
 * Created by kyuholee on 2016. 7. 30..
 */
public  class NewMemoActivity extends AppCompatActivity {
    private static final String TAG = "NewMemoActivity";
    // khlee: delete useless views
    //private Toolbar tool_bar;                              // Declaring the Toolbar Object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);
        /**
         * khlee: delete useless views and view groups
        tool_bar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(tool_bar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
         */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memo, menu);
        return true;
    }
}

