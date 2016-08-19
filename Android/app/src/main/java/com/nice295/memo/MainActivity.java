package com.nice295.memo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.nice295.memo.model.Memo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    FloatingActionButton fab_plus, fab_memo, fab_record;
    Animation fbOpen, fbClose, fbClockwise, fbAnticlockwise;
    boolean isOpen = false;

    private List<Recycler_item> items; //khle
    private RecyclerView recyclerView; //khlee
    private RecyclerAdapter mAdapter; //khlee

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this); //khlee

        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_memo = (FloatingActionButton) findViewById(R.id.fab_newMemo);
        fab_record = (FloatingActionButton) findViewById(R.id.fab_newRecord);
        fbOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fbClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fbClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fbAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    fab_record.startAnimation(fbClose);
                    fab_memo.startAnimation(fbClose);
                    fab_plus.startAnimation(fbAnticlockwise);
                    fab_memo.setClickable(false);
                    fab_record.setClickable(false);
                    isOpen = false;

                } else {
                    fab_record.startAnimation(fbOpen);
                    fab_memo.startAnimation(fbOpen);
                    fab_plus.startAnimation(fbClockwise);
                    fab_memo.setClickable(true);
                    fab_record.setClickable(true);
                    isOpen = true;

                }
            }
        });

        //khlee: All click processed in one onClick() function below. // 하나의 onClick()으로 모으기
        fab_record.setOnClickListener(this);
        fab_memo.setOnClickListener(this);

        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view); //khlee
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //List<Recycler_item> items = new ArrayList<>();
        items = new ArrayList<>(); //khlee

        /* khlee: Increase test count as 20
        Recycler_item[] item = new Recycler_item[5];
        item[0] = new Recycler_item(R.drawable.a, "Memo1", "#1");
        item[1] = new Recycler_item(R.drawable.b, "Memo1", "#2");
        item[2] = new Recycler_item(R.drawable.c, "Memo1", "#3");
        item[3] = new Recycler_item(R.drawable.d, "Memo1", "#4");
        item[4] = new Recycler_item(R.drawable.e, "Memo1", "#5");
        for (int i = 0; i < 5; i++) items.add(item[i]);
        */

        /*khlee: delete again
        int testCount = 20;
        Recycler_item[] item = new Recycler_item[testCount];
        for (int idx = 0; idx < testCount; idx++) {
            item[idx] = new Recycler_item(R.drawable.a, "Memo " + idx, "#" + idx);
            items.add(item[idx]);
        }
        */

        /*
        LinkedList<Recycler_item> database = Paper.book().read(Constants.MEMOS, new LinkedList());
        Log.d(TAG, "Memo size: " + database.size());
        for (int idx = 0; idx < database.size(); idx++) {
            Log.d(TAG, "Memo: " + database.get(idx).getTitle() + ", " + database.get(idx).getdesc());
            items.add( database.get(idx) );
        }

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_main));
        */
        mAdapter = new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_main);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        LinkedList<Recycler_item> database = Paper.book().read(Constants.MEMOS, new LinkedList());
        Log.d(TAG, "Memo size: " + database.size());
        for (int idx = 0; idx < database.size(); idx++) {
            Log.d(TAG, "Memo: " + database.get(idx).getTitle() + ", " + database.get(idx).getdesc());
            items.add( database.get(idx) );
        }

        mAdapter.notifyDataSetChanged();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.miProfile) { //khlee: for debugging
            Paper.book().destroy();
            mAdapter.notifyDataSetChanged();
        }

        return true;
    }


    //khlee: All click are here
    @Override
    public void onClick(View v) {
        if (v == fab_memo) {
            Intent intent = new Intent(this, NewMemoActivity.class);
            startActivity(intent);
        }
        else if (v == fab_record) {
            Intent intent = new Intent(this, NewRecordActivity.class);
            startActivity(intent);
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        Context context;
        List<Recycler_item> items;
        int item_layout;

        public RecyclerAdapter(Context context, List<Recycler_item> items, int item_layout) {
            this.context = context;
            this.items = items;
            this.item_layout = item_layout;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
            return new ViewHolder(v);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Recycler_item item = items.get(position);
            Drawable drawable = context.getResources().getDrawable(item.getImage());
            holder.image.setBackground(drawable);
            holder.title.setText(item.getTitle());
            holder.desc.setText(item.getdesc());
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title;
            TextView desc;
            CardView cardview;

            public ViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.item_image);
                title = (TextView) itemView.findViewById(R.id.item_title);
                desc = (TextView) itemView.findViewById(R.id.item_desc);
                cardview = (CardView) itemView.findViewById(R.id.cardview);
            }
        }
    }

    public void startMemo(View view) {
        startActivity(new Intent(this, NewMemoActivity.class));
    }

}

