package com.kgu.UNIMEMO;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

//mport com.google.android.gms.common.api.GoogleApiClient;

import com.nice295.memo.R;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

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
    private TabFragment1.MrecyclerAdapter mAdapter; //khlee
    private TextView fabtext;
    private TextView fabtext_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this); //khlee

        items = new ArrayList<>();


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


        //mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();



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
        /*if (item.getItemId() == R.id.miProfile) { //khlee: for debugging
            //Paper.book().destroy();
            mAdapter.notifyDataSetChanged();
        }*/
        if (item.getItemId() == R.id.about) { //khlee: added
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

}
