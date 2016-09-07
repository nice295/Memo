package com.nice295.memo;

/**
 * Created by PJH on 2016-08-06.
 */

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/**
 * Created by PJH on 2016-08-05.
 */

public class Adapter_record extends BaseAdapter {
    private String outputFile = null;
    private MediaRecorder myAudioRecorder;
    List<String> formats;
    LayoutInflater inflater;



    public class Holder{

        TextView date;
        ImageButton cancel;
        ImageButton play;
    }

    public Adapter_record(Context context,List<String> formats) {
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
        if(convertView == null){//if문으로 객체가 없을때 작동,목록에 행들을 출력할 때 모든 객체의 행이 생성되는것이나ㅣ고 화면에 보이는 행들만 생성됨
            holder =new Holder();
            convertView= inflater.inflate(R.layout.record_message,null);

            holder.play = (ImageButton) convertView.findViewById(R.id.play) ;
            holder.date= (TextView) convertView.findViewById(R.id.item_date);
            holder.cancel = (ImageButton) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }

        holder.date.setText("    녹음");



        holder.play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";;

                //do something
                myAudioRecorder=new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(outputFile);
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                // Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

                notifyDataSetChanged();
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                formats.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return  convertView;
    }


}