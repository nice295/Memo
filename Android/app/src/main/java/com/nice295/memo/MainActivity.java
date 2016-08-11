package com.nice295.memo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    FloatingActionButton fab_plus, fab_memo, fab_record;
    Animation fbOpen, fbClose, fbClockwise, fbAnticlockwise;
    boolean isOpen = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        fab_plus= (FloatingActionButton) findViewById(R.id.fab_plus) ;
        fab_memo= (FloatingActionButton) findViewById(R.id.fab_newMemo) ;
        fab_record= (FloatingActionButton) findViewById(R.id.fab_newRecord) ;
        fbOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fbClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fbClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        fbAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
        fab_plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isOpen)
                {
                    fab_record.startAnimation(fbClose);
                    fab_memo.startAnimation(fbClose);
                    fab_plus.startAnimation(fbAnticlockwise);
                    fab_memo.setClickable(false);
                    fab_record.setClickable(false);
                    isOpen = false;

                }
                else
                {
                    fab_record.startAnimation(fbOpen);
                    fab_memo.startAnimation(fbOpen);
                    fab_plus.startAnimation(fbClockwise);
                    fab_memo.setClickable(true);
                    fab_record.setClickable(true);
                    isOpen = true;

                }
            }
        });







        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Recycler_item> items=new ArrayList<>();
        Recycler_item[] item=new Recycler_item[5];
        item[0]=new Recycler_item(R.drawable.a,"Memo1", "#1");
        item[1]=new Recycler_item(R.drawable.b,"Memo1", "#2");
        item[2]=new Recycler_item(R.drawable.c,"Memo1","#3");
        item[3]=new Recycler_item(R.drawable.d,"Memo1","#4");
        item[4]=new Recycler_item(R.drawable.e,"Memo1","#5");

        for(int i=0;i<5;i++) items.add(item[i]);

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(),items,R.layout.activity_main));


    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        Context context;
        List<Recycler_item> items;
        int item_layout;
        public RecyclerAdapter(Context context, List<Recycler_item> items, int item_layout) {
            this.context=context;
            this.items=items;
            this.item_layout=item_layout;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null);
            return new ViewHolder(v);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Recycler_item item=items.get(position);
            Drawable drawable=context.getResources().getDrawable(item.getImage());
            holder.image.setBackground(drawable);
            holder.title.setText(item.getTitle());
            holder.desc.setText(item.getdesc());
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,item.getTitle(),Toast.LENGTH_SHORT).show();
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
                image=(ImageView)itemView.findViewById(R.id.item_image);
                title=(TextView)itemView.findViewById(R.id.item_title);
                desc=(TextView)itemView.findViewById(R.id.item_desc) ;
                cardview=(CardView)itemView.findViewById(R.id.cardview);
            }
        }
    }

}

