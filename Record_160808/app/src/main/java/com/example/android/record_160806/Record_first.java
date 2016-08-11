package com.example.android.record_160806;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Record_first extends AppCompatActivity {
 private  static Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_first);
        OnClickButtonListener();


            }

    public void OnClickButtonListener(){
        button = (Button)findViewById(R.id.button_first);
        button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent =new Intent("com.example.android.record_160806.Record_second");
                        startActivity(intent);
                    }
                }
        );
    }
}
