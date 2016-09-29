package com.nice295.memo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by PJH on 2016-09-10.
 */
public class DescActivity extends AppCompatActivity {
    private TextView desc_memo;
    private EditText edit_memo;
    private Menu mOptionsMenu;
    Menu Mmenu;
    private MenuItem medit_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_desc);

        edit_memo =(EditText) findViewById(R.id.desc_edit) ;
        edit_memo.setVisibility(View.GONE);
        desc_memo = (TextView) findViewById(R.id.desc_memo);
        Intent intent =getIntent();
        String desc = intent.getExtras().getString("VALUE");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Mmenu.findItem(R.id.action_edit).setVisible(false);
            Mmenu.findItem(R.id.edit_complete).setVisible(true);
            edit_memo.setVisibility(View.VISIBLE);
            desc_memo.setVisibility(View.GONE);
         /*   String temp = edit_memo.getText().toString();
            edit_memo.setText(temp);
           desc_memo.toString();*/
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
            finish();

        }
        if (id == android.R.id.home) {
            finish();
        }
        return true;

    }

}
