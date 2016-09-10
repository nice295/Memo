package com.nice295.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by PJH on 2016-09-10.
 */
public class DescActivity extends AppCompatActivity {
    private TextView desc_memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_desc);

        desc_memo = (TextView) findViewById(R.id.desc_memo);
        Intent intent =getIntent();
        String desc = intent.getExtras().getString("VALUE");
        desc_memo.setText(desc);

    }
}
