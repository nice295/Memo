
package com.example.android.record_160806;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class Record_second extends AppCompatActivity {


    private boolean flag;

    private Button mButton;
    private ProgressBar mProgressBar;
    private ImageButton mdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_second);


        final ArrayList<String> formats= new ArrayList<String>();


        final DateAdapter adapter = new DateAdapter(this,formats);


        mButton = (Button) findViewById(R.id.button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        mdelete =(ImageButton) findViewById(R.id.delete);






        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView list = (ListView) findViewById(R.id.list);




                formats.add("yy.MM.dd  HH:mm");



                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showAddDialog();
        return true;
    }

    private void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.complete, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(getString(R.string.Title));
        dialogBuilder.setMessage(getString(R.string.Desc));
        //dialogBuilder.setMessage("");
        dialogBuilder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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

