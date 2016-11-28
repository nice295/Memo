package com.nice295.memo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.nice295.memo.model.Record;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by fepan_000 on 2016-11-25.
 */

public class DescRecordActivity extends AppCompatActivity {

    private static final String TAG = "DescRecordActivity";
    private List<Recycler_item> items;

    @Override
    public void onCreate(Bundle saveedInstanceState){
        super.onCreate(saveedInstanceState);
        setContentView(R.layout.recordlist);
        LinkedList<Recycler_item> recordlist = Paper.book().read(Constants.RECORD, new LinkedList());
        for (int idx = 0; idx < recordlist.size(); idx++ ) {
            Log.d(TAG, "Title: " + recordlist.get(idx).getTitle());

            for (int idx1 = 0; idx1 < recordlist.get(idx).getPaths().size(); idx1++ ) {
                Log.d(TAG, "Path(" + idx1 + "): " + recordlist.get(idx).getPaths().get(idx1).toString());
            }
        }


        final Adapter_Recordfile adapater = new Adapter_Recordfile(this, R.layout.recordlist, recordlist);
        final ListView list = (ListView) findViewById(R.id.recordlist);





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
