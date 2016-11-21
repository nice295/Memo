package com.nice295.memo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by Junyoung on 2016-06-23.
 */

public class TabFragment2 extends Fragment {
    private static final String TAG = ".RecordActivity";
    private RecyclerView rRecyclerView; //khlee
    private RrecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Recycler_item> items; //khle
    private int postion;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.frag_record, container, false);
        rRecyclerView = (RecyclerView) rootview.findViewById(R.id.my_record_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rRecyclerView.setLayoutManager(layoutManager);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle  savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        items = new ArrayList<>();
        mAdapter = new RrecyclerAdapter(getActivity(), items, postion);
        rRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();

        items.clear(); //jaewoo
        LinkedList<Recycler_item> database = Paper.book().read(Constants.RECORD, new LinkedList());

        Log.d(TAG, "Record size: " + database.size());
        for (int idx = 0; idx < database.size(); idx++) {
            Log.d(TAG, "Record: " + database.get(idx).getTitle() + ", " + database.get(idx).getdesc());
            items.add(database.get(idx));

        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

    }
    public class RrecyclerAdapter extends RecyclerView.Adapter<RrecyclerAdapter.ViewHolder> {
        Context context;
        private List<Recycler_item> items;
        int item_layout;

        public RrecyclerAdapter(Context context , List<Recycler_item> items, int item_layout) {
            this.context = context;
            this.items = items;
            this.item_layout = item_layout;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView image;
            public TextView title;
            public TextView desc;
            public CardView cardview;
            public TextView time;

            public ViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.item_image);
                title = (TextView) itemView.findViewById(R.id.item_title);
                desc = (TextView) itemView.findViewById(R.id.item_desc);
                cardview = (CardView) itemView.findViewById(R.id.cardview);
                time = (TextView) itemView.findViewById(R.id.item_time);

            }
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
                    Intent intent = new Intent(context ,DescActivity.class);
                    intent.putExtra("VALUE", item.getdesc());
                    intent.putExtra("VALUE_2", item.getTitle());
                    context.startActivity(intent);

                    Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("CLICK", "OnLongClickListener");
                    removal();
                    return true; // 다음 이벤트 계속 진행 false, 이벤트 완료 true



                }

                public void removal() {
                    items.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, items.size());
                    mAdapter.notifyDataSetChanged();
                    LinkedList memos = Paper.book().read(Constants.RECORD, new LinkedList());
                    memos.remove(position);
                    Paper.book().write(Constants.RECORD, memos);

                }
            });



        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setFilter(List<Recycler_item> item) {
            items = new ArrayList<>();
            items.addAll(item);
            notifyDataSetChanged();
        }


    }




}