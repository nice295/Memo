package com.kgu.UNIMEMO;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nice295.memo.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.nice295.memo.R.id.play;

/**
 * Created by fepan_000 on 2016-11-25.
 */

public class Adapter_Recordfile extends BaseAdapter {
    private String outputFile = null;
    private static final String TAG = "Adapter_Recordfile";
    private MediaRecorder myAudioRecorder;
    ArrayList<Record_item> _recordformat;
    LayoutInflater inflater;
    int _layout;
    private Context _Context;
    private File file;
    NewRecordActivity mm;
    MediaPlayer player;
    DescRecordActivity playfile;
    private Toast rToast;
NewRecordActivity playfile2;
    int idx;String FILEPLAY;
    int idx1;
    public class Holder {

        TextView recordTitleName;
        ImageButton delete;
        ImageButton play;
        ImageButton pause;
    }

    public Adapter_Recordfile(DescRecordActivity context, int layout, ArrayList<Record_item> recordformat) {

        // this.formats = formats;
        _Context = context;
        _layout = layout;
        _recordformat = recordformat;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (convertView == null) {//if문으로 객체가 없을때 작동,목록에 행들을 출력할 때 모든 객체의 행이 생성되는것이나ㅣ고 화면에 보이는 행들만 생성됨
            holder = new Holder();
            convertView = inflater.inflate(R.layout.recorditem, null);

            holder.play = (ImageButton) convertView.findViewById(play);
            holder.pause = (ImageButton) convertView.findViewById(R.id.pause);
            holder.recordTitleName = (TextView) convertView.findViewById(R.id.record_name);
            holder.delete = (ImageButton) convertView.findViewById(R.id.list_delete);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Record_item records = _recordformat.get(position);
      //  String playgo =  _recordformat.get(position).getPaths().get(position).toString();
        holder.recordTitleName.setText(records.getfileTitle());


        holder.pause.setVisibility(View.INVISIBLE);

       holder.play.setOnClickListener(new View.OnClickListener(){


           @Override
            public void onClick(View v) {
               holder.play.setVisibility(View.INVISIBLE);
               holder.pause.setVisibility(View.VISIBLE);
                player=null;
               if( player != null ) {
                   player.stop();
                   player.release();
                   player = null;
               }
               player = new MediaPlayer();

               try {
                   String SDCardpath =new String(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Unimemo/");

                   FILEPLAY = new String(SDCardpath+"/"+ _recordformat.get(position).getfolder()+"/"+_recordformat.get(position).getName());
                   Log.d(TAG, "Path()="+ FILEPLAY);
                   player.setDataSource(FILEPLAY);
                   player.prepare();
               } catch(IOException e) {
                   Log.d("tag", "Audio Play error");
                   return;
               }
               player.start();



/*
               try{
                   File SDCardpath =new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Unimemo/");

                   FILEPLAY = new String(SDCardpath.getAbsolutePath()+"/"+ _recordformat.get(position).getfolder()+"/"+_recordformat.get(position).getName());
                   playAudio(FILEPLAY);

               } catch(Exception e){
                   e.printStackTrace();
               }Log.d(TAG, "Path()="+ FILEPLAY);*/
               //
              // _recordformat= Paper.book().read(Constants.FILENAME,new ArrayList<Record_item>());
         // String playgo =
             // Log.d(TAG, "Path()="+ playgo);
              // Log.d(TAG, "Path()="+ playgo);
               //ArrayList<Record_item> recordlist = new ArrayList<Record_item>();



                      // Log.d(TAG, "Path( ): " + recordlist.get(position).getPaths().get(position).toString());

              /*LinkedList<Record_item> ddr = Paper.book().read(Constants.FILENAME,new LinkedList<Record_item>());
               for (idx = 0; idx < ddr.size(); idx++ ) {

                   for (idx1 = 0; idx1 < ddr.get(idx1).getPaths().size(); idx1++) {

                   }
               }*/
               /*if (player != null) {
                    player.stop();
                    player.release();
                    player = null;
                }

                try {

                    // 오디오를 플레이 하기위해 MediaPlayer 객체 player를 생성한다.
                    player = new MediaPlayer ();

                  File SDCardpath =new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Unimemo/");

                   String FILEPLAY = new String(SDCardpath.getAbsolutePath() + "/Unimemo/" + _recordformat.get(position).getfolder()+"/"+_recordformat.get(position).getName());
                     //재생할 오디오 파일 저장위치를 설정
                    player.setDataSource(FILEPLAY );
                    // 웹상에 있는 오디오 파일을 재생할때
                    // player.setDataSource(Audio_Url);

                    // 오디오 재생준비,시작
                    player.prepare();
                    player.start();
                } catch (Exception e) {
                    Log.e("SampleAudioRecorder", "Audio play failed.", e);
                }*/
            }
        });
       holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _recordformat.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        holder.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
                player.release();
              holder.play.setVisibility(View.VISIBLE);
                holder.pause.setVisibility(View.INVISIBLE);
            }
        });
        return convertView;
    }
    /*private void playAudio(String url) throws Exception{
        killMediaPlayer();

        player = new MediaPlayer();
        player.setDataSource(url);
        player.prepare();
        player.start();
    }
    private void killMediaPlayer() {
        if(player != null){
            try {
                player.release();
            } catch(Exception e){
                e.printStackTrace();
            }
        }

    }*/
}