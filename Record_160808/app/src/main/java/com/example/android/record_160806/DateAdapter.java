package com.example.android.record_160806;

/**
 * Created by PJH on 2016-08-06.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PJH on 2016-08-05.
 */

public class DateAdapter extends BaseAdapter {

    List<String> formats;
    LayoutInflater inflater;



    public class Holder{

        TextView date;
        ImageButton cancel;

    }

    public DateAdapter(Context context,List<String> formats) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        this.formats = formats;

    }

    @Override
    public int getCount() {
        return formats.size();
    }

    @Override
    public Object getItem(int position) {
        return formats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if(convertView == null){
            holder =new Holder();
            convertView= inflater.inflate(R.layout.message,null);

            holder.date= (TextView) convertView.findViewById(R.id.item_date);
            holder.cancel = (ImageButton) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }

        holder.date.setText("  녹음");


        holder.cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                formats.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return  convertView;
    }


}