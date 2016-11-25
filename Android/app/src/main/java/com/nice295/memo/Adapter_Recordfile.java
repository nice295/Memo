package com.nice295.memo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

/**
 * Created by fepan_000 on 2016-11-25.
 */

public class Adapter_Recordfile extends BaseAdapter {
    private String outputFile = null;
    private MediaRecorder myAudioRecorder;
    List<Record_list> _recordformat;
    LayoutInflater inflater;
    int _layout;
    private Context _Context;

    public class Holder{

        File recordpath;
        TextView titleName;
        ImageButton delete;
        ImageButton play;
    }

    public Adapter_Recordfile(DescRecordActivity context, int layout, List<Record_list> recordformat) {

        // this.formats = formats;
        _Context= context;
        _layout = layout;
        _recordformat = recordformat;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
    }

    @Override
    public int getCount() {
        return _recordformat.size();
    }

    @Override
    public Object getItem(int position) {
        return _recordformat.get(position);
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
            convertView= inflater.inflate(R.layout.record_list,null);

            holder.play = (ImageButton) convertView.findViewById(R.id.play) ;
            holder.titleName= (TextView) convertView.findViewById(R.id.record_name);
            holder.delete = (ImageButton) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        Record_list Re_item = _recordformat.get(position);

       // holder.titleName.setText(Re_item.getTitle());



      /*  holder.play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                notifyDataSetChanged();
            }
        });*/
        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                _recordformat.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return  convertView;
    }


}
