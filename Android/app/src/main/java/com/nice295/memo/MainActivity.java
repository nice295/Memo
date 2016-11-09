package com.nice295.memo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.paperdb.Paper;

//mport com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //khlee, private GoogleApiClient client;

    FloatingActionButton fab_plus, fab_memo, fab_record;
    Animation fbOpen, fbClose, fbClockwise, fbAnticlockwise;
    boolean isOpen = false;

    private TabLayout tabLayout;
    private ViewPager viewPager;



    private List<Recycler_item> items; //khle
    private RecyclerView recyclerView; //khlee
    private RecyclerAdapter mAdapter; //khlee
    private TextView fabtext;
    private TextView fabtext_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this); //khlee


        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("MEMO"));
        tabLayout.addTab(tabLayout.newTab().setText("RECORD"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        fabtext = (TextView) findViewById(R.id.textView);
        fabtext_2 = (TextView) findViewById(R.id.textView2);
        fabtext.setVisibility(View.GONE);
        fabtext_2.setVisibility(View.GONE);
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
                    Fabclose();

                } else {
                    fabtext.setVisibility(View.VISIBLE);
                    fabtext_2.setVisibility(View.VISIBLE);
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


        //mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();

        items.clear(); //jaewoo
        LinkedList<Recycler_item> database = Paper.book().read(Constants.MEMOS, new LinkedList());

        Log.d(TAG, "Memo size: " + database.size());
        for (int idx = 0; idx < database.size(); idx++) {
            Log.d(TAG, "Memo: " + database.get(idx).getTitle() + ", " + database.get(idx).getdesc());
            items.add(database.get(idx));

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
        if (item.getItemId() == R.id.miProfile) { //khlee: for debugging
            //Paper.book().destroy();
            mAdapter.notifyDataSetChanged();
        }
        else if (item.getItemId() == R.id.about) { //khlee: added
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        return true;
    }


    //khlee: All click are here
    @Override
    public void onClick(View v) {
        if (v == fab_memo) {
            Fabclose();
            Intent intent = new Intent(this, NewMemoActivity.class);
            startActivity(intent);
        } else if (v == fab_record) {
            Fabclose();
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
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final Recycler_item item = items.get(position);
            Drawable drawable = context.getResources().getDrawable(item.getImage());
            holder.image.setBackground(drawable);
            holder.title.setText(item.getTitle());
            holder.desc.setText(item.getdesc());
            holder.time.setText(item.getDate());
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DescActivity.class);
                    intent.putExtra("VALUE", item.getdesc());
                    intent.putExtra("VALUE_2", item.getTitle());
                    startActivity(intent);

                    Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("CLICK", "OnLongClickListener");


                    items.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position,items.size());
                    mAdapter.notifyDataSetChanged();
                    LinkedList memos = Paper.book().read(Constants.MEMOS, new LinkedList());
                    memos.remove(position);
                    Paper.book().write(Constants.MEMOS, memos);


                    return true; // 다음 이벤트 계속 진행 false, 이벤트 완료 true

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
            TextView time;

            public ViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.item_image);
                title = (TextView) itemView.findViewById(R.id.item_title);
                desc = (TextView) itemView.findViewById(R.id.item_desc);
                time = (TextView) itemView.findViewById(R.id.item_time);
                cardview = (CardView) itemView.findViewById(R.id.cardview);
            }
        }
    }

    private void Fabclose() {
        fab_record.startAnimation(fbClose);
        fab_memo.startAnimation(fbClose);
        fab_plus.startAnimation(fbAnticlockwise);
        fab_memo.setClickable(false);
        fab_record.setClickable(false);
        isOpen = false;
        fabtext.setVisibility(View.GONE);
        fabtext_2.setVisibility(View.GONE);
    }

    public void startMemo(View view) {
        startActivity(new Intent(this, NewMemoActivity.class));
    }

    public String DATE(){
        long now = System.currentTimeMillis();
        java.util.Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNow = sdfNow.format(date);
        return strNow;
    }
}
