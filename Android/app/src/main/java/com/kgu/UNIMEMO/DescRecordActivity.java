package com.kgu.UNIMEMO;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.nice295.memo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fepan_000 on 2016-11-25.
 */

public class DescRecordActivity extends AppCompatActivity {

    private static final String TAG = "DescRecordActivity";
    private List<Record_item> items;
    private File file;
    private List myList;
    private ListView fFind_ListView;
    Recycler_item item;
    String filename;
    String title;
    File myDataPath;
    private int i =0;
    @Override
    public void onCreate(Bundle saveedInstanceState){
        super.onCreate(saveedInstanceState);
        setContentView(R.layout.recordlist);


        initView();


        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED)){
            findFolder();
        }else{
            Log.i("MAIN","Not find SDCard");
        }
        Intent intent =getIntent();
        String title = intent.getExtras().getString("VALUE_2");
        this.setTitle(title);
    }

    private void initView(){
        fFind_ListView = (ListView)findViewById(R.id.recordlist);
    }

    private void findFolder(){
        ArrayList<Record_item> fName = new ArrayList<>();
        File SDCardpath =new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        Intent intent =getIntent();

        //Postion = intent.getExtras().getString("VALUE3");
        title = intent.getExtras().getString("VALUE_2");
       myDataPath = new File(SDCardpath.getAbsolutePath() + "/Unimemo/"+title);

        Adapter_Recordfile filelist = new Adapter_Recordfile(this, R.layout.recordlist,fName);
        if(myDataPath.listFiles().length>0){
            for(File file : myDataPath.listFiles()){
                filename =file.getName();
                Record_item li = new Record_item(i, "",filename,title);
                fName.add(li);

            }
        }
        myDataPath = null;
        fFind_ListView.setAdapter(filelist);
        /*LinkedList<Record_item> recordlist = Paper.book().read(Constants.FILENAME, new LinkedList());
        for (int idx = 0; idx < recordlist.size(); idx++ ) {
            Log.d(TAG, "Title: " + recordlist.get(idx).getfileTitle());

            for (int idx1 = 0; idx1 < recordlist.get(idx).getPaths().size(); idx1++ ) {
                Log.d(TAG, "Path(" + idx1 + "): " + recordlist.get(idx).getPaths().get(idx1).toString());
            }
        }


        final Adapter_Recordfile adapater = new Adapter_Recordfile(this, R.layout.recordlist, recordlist);

        final ListView list = (ListView) findViewById(R.id.recordlist);

        Paper.book().write(Constants.FILENAME,recordlist);

        list.setAdapter(adapater);
        adapater.notifyDataSetChanged();



*/




        /*
        ArrayList<Record_list> fileformats = new ArrayList<Record_list>();
        final Adapter_Recordfile adpater = new Adapter_Recordfile(this, R.layout.record_list, fileformats);
        final ArrayList<Record_list> finalfileFormats = fileformats;
        final ListView list = (ListView) findViewById(R.id.recordlist);
        LinkedList recordlist = Paper.book().read(Constants.RECORDLIST, new LinkedList());
        Record_list rcl = new Record_list(mm.fileName, mm.recodeFile_2);
        //recordlist.add(new Record_list(R.drawable.ic_play_circle_outline_black_24dp, R.drawable.ic_clear_black_24dp, mm.fileName, mm.recodeFile_2));
        Paper.book().write(Constants.RECORDLIST,recordlist);
        finalfileFormats.add(rcl);
        list.setAdapter(adpater);
        adpater.notifyDataSetChanged();
        */
    }

}