package com.nice295.memo;

import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import io.paperdb.Paper;

public class NewRecordActivity extends AppCompatActivity {


    private boolean flag;
    private static Button button;
    private Button mButton;
    private ProgressBar mProgressBar;
    private ImageButton mdelete;
    private TextView mText;
    private long lastTimeBackPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ArrayList<String> formats = new ArrayList<String>();
        final Adapter_record adapter = new Adapter_record(this, formats);


        mText = (TextView) findViewById(R.id.recording);
        mButton = (Button) findViewById(R.id.button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        mdelete = (ImageButton) findViewById(R.id.delete);
        button = (Button) findViewById(R.id.button_first);


        mText.setVisibility(View.GONE);
        mButton.setVisibility(View.GONE);






        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView list = (ListView) findViewById(R.id.list);
                formats.add("yy.MM.dd  HH:mm");
                list.setAdapter(adapter);




                Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });


    }


    //20160826 jaewoo
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.back_title))
                        .setMessage(getString(R.string.back_message))
                        .setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.CANCEL), null).show();
                return false;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record, menu);
        menu.findItem(R.id.actoin_complete).setVisible(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button.setVisibility(View.GONE);
                mButton.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
                mText.setVisibility(View.VISIBLE);
                menu.findItem(R.id.actoin_complete).setVisible(true);



                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actoin_complete) {
            showAddDialog();
        }
        if (id == android.R.id.home) {
            if (System.currentTimeMillis() - lastTimeBackPressed <1500)
            {
                finish();

            }
            Toast.makeText(this,"버튼 한번 더 누르면 녹음을 취소하고 메인화면으로 돌아갑니다",Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = System.currentTimeMillis();

        }
        return true;

    }


    private void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.record_complete, null);
        dialogBuilder.setView(dialogView);
        final EditText memoname = (EditText) dialogView.findViewById(R.id.Title);
        dialogBuilder.setTitle(getString(R.string.save));
        dialogBuilder.setMessage(getString(R.string.finish_record));
        //dialogBuilder.setMessage("");
        dialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String title = memoname.getText().toString();


                // khlee: Save new memo into database

                LinkedList memos = Paper.book().read(Constants.MEMOS, new LinkedList());
                memos.add(new Recycler_item(R.drawable.ic_mic_black_24dp, title, ""));
                Paper.book().write(Constants.MEMOS, memos);

                finish();
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
