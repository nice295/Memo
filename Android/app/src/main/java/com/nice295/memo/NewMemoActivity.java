package com.nice295.memo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

import io.paperdb.Paper;

/**
 * Created by kyuholee on 2016. 7. 30..
 */
public  class NewMemoActivity extends AppCompatActivity {
    private static final String TAG = "NewMemoActivity";

    private EditText mEtMemo; //khlee
    private long lastTimeBackPressed;//jaewoo
    // khlee: delete useless views
    //private Toolbar tool_bar;                              // Declaring the Toolbar Object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**
         * khlee: delete useless views and view groups
         tool_bar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
         setSupportActionBar(tool_bar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
         */
        mEtMemo = (EditText) findViewById(R.id.editText); //khlee

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
        getMenuInflater().inflate(R.menu.menu_memo, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_mode) {
            showAddDialog();
        }
        if (id == android.R.id.home) {
            if (System.currentTimeMillis() - lastTimeBackPressed <1500)
            {
                finish();

            }
            Toast.makeText(this,"버튼 한번 더 누르면 메모를 취소하고 메인화면으로 돌아갑니다",Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = System.currentTimeMillis();
        }
        return true;

    }
    private void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.memo_complete, null);
        dialogBuilder.setView(dialogView);

        //khlee: define views from dialog
        final EditText memoname = (EditText) dialogView.findViewById(R.id.memoname);
        final EditText memodesc = (EditText) dialogView.findViewById(R.id.memodesc);

        dialogBuilder.setTitle(getString(R.string.save));
        dialogBuilder.setMessage(getString(R.string.finish_memo));
        //dialogBuilder.setMessage("");
        dialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // khlee: Get title from dialog
                String title = memoname.getText().toString();
                String tag = memodesc.getText().toString();

                // khlee: Save new memo into database
                LinkedList memos = Paper.book().read(Constants.MEMOS, new LinkedList());
                memos.add(new Recycler_item(R.drawable.ic_mode_edit_black_24dp, title, mEtMemo.getText().toString()));
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

