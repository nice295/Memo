package com.nice295.memo;

/**
 * Created by PJH on 2016-08-06.
 */

import android.content.Context;
import android.media.MediaRecorder;
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

public class Adapter_record extends BaseAdapter {
    private String outputFile = null;
    private MediaRecorder myAudioRecorder;
    List<Record_item> _formats;
    LayoutInflater inflater;
    int _layout;
    private Context _Context;

    public class Holder{

        TextView date;
        ImageButton cancel;
       // ImageButton play;
    }

    public Adapter_record(NewRecordActivity context, int layout, List<Record_item> formats) {

       // this.formats = formats;
         _Context= context;
        _layout = layout;
        _formats = formats;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
    }

    @Override
    public int getCount() {
        return _formats.size();
    }

    @Override
    public Object getItem(int position) {
        return _formats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if(convertView == null){//if문으로 객체가 없을때 작동,목록에 행들을 출력할 때 모든 객체의 행이 생성되는것이나ㅣ고 화면에 보이는 행들만 생성됨
            holder =new Holder();
            convertView= inflater.inflate(R.layout.record_message,null);

           // holder.play = (ImageButton) convertView.findViewById(R.id.play) ;
            holder.date= (TextView) convertView.findViewById(R.id.item_date);
            holder.cancel = (ImageButton) convertView.findViewById(R.id.initial_delete);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        Record_item Re_item = _formats.get(position);

        holder.date.setText(Re_item.getDate());



      /*  holder.play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                notifyDataSetChanged();
            }
        });*/
        holder.cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                _formats.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return  convertView;
    }


}