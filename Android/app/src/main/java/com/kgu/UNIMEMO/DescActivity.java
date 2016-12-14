package com.kgu.UNIMEMO;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.nice295.memo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import io.paperdb.Paper;

import static com.nice295.memo.R.id.memoname_2;

/**
 * Created by PJH on 2016-09-10.
 */
public class DescActivity extends AppCompatActivity {
    private TextView desc_memo;
    private EditText edit_memo;
    private Menu mOptionsMenu;
    Menu Mmenu;
    private MenuItem medit_title;

    private Context mContext = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_desc);

        edit_memo =(EditText) findViewById(R.id.desc_edit) ;
        edit_memo.setVisibility(View.GONE);
        desc_memo = (TextView) findViewById(R.id.desc_memo);
        Intent intent =getIntent();

        String desc = intent.getExtras().getString("VALUE");
         //Postion = intent.getExtras().getString("VALUE3");
        String title = intent.getExtras().getString("VALUE_2");
        this.setTitle(title);
        desc_memo.setText(desc);
        edit_memo.setText(desc);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        Mmenu = menu;
        menu.findItem(R.id.action_edit).setVisible(true);
        menu.findItem(R.id.edit_complete).setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item2) {
        int id = item2.getItemId();
        if (id == R.id.action_edit) {
            Mmenu.findItem(R.id.action_edit).setVisible(false);
            Mmenu.findItem(R.id.edit_complete).setVisible(true);
            edit_memo.setVisibility(View.VISIBLE);
            desc_memo.setVisibility(View.GONE);
            edit_memo.requestFocus();



            edit_memo.setSelection(edit_memo.length());
            InputMethodManager imm = (InputMethodManager)
                    this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(this.edit_memo, InputMethodManager.SHOW_FORCED);
            imm.showSoftInputFromInputMethod (this.edit_memo.getApplicationWindowToken(),InputMethodManager.SHOW_FORCED);
        }


        if (id == R.id.edit_complete) {

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edit_memo.getWindowToken(), 0);


/*
            Intent intent = new Intent(this.getIntent());
            int position = intent.getIntExtra("VALUE_3",1);
            MMM.items.remove(position);
            MMM.notifyItemRemoved(position);
            MMM.notifyItemRangeChanged(position, MMM.items.size());
            MMM.notifyDataSetChanged();
            */
            LinkedList memos = Paper.book().read(Constants.MEMOS, new LinkedList());
            memos.removeLast();
            Paper.book().write(Constants.MEMOS, memos);

            showAddDialog_2();



        }
        if (id == android.R.id.home) {
            finish();
        }
        return true;

    }



    private void showAddDialog_2() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_memo_complete, null);
        dialogBuilder.setView(dialogView);

        //khlee: define views from dialog
        final EditText memoname = (EditText) dialogView.findViewById(memoname_2);

        Intent intent =getIntent();
        String title = intent.getExtras().getString("VALUE_2");
       // this.setTitle(title);
        //this.getTitle();
        memoname.setText(title);
        memoname.setSelection(memoname.length());
        dialogBuilder.setTitle(getString(R.string.save));
        dialogBuilder.setMessage(getString(R.string.edit_complete));
        //dialogBuilder.setMessage("");
        dialogBuilder.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // khlee: Get title from dialog
                String second_title = memoname.getText().toString();


                // khlee: Save new memo into database

                LinkedList memos_2 = Paper.book().read(Constants.MEMOS, new LinkedList());
                memos_2.add(new Recycler_item(R.drawable.ic_mode_edit_black_24dp, second_title, edit_memo.getText().toString(),DATE()));
                Paper.book().write(Constants.MEMOS, memos_2);

                finish();

            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.CANCEL), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    public String DATE(){
        long now = System.currentTimeMillis();
        java.util.Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNow = sdfNow.format(date);
        return strNow;
    }
}
