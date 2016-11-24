package com.nice295.memo;

import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import io.paperdb.Paper;

public class NewRecordActivity extends AppCompatActivity {

    public int i = 0;
    public MediaRecorder myAudioRecorder = null;
    //public String outputFile= null;

    private boolean flag;
    private static Button button;
    private Button mButton;
    private ProgressBar mProgressBar;
    private ImageButton mdelete;
    private TextView mText;
    private long lastTimeBackPressed;
    private Runnable mRunnable;
    private Handler mHandler;
    String fileName;
    private Toast rToast;
    File recodeFile_2;
    boolean isRecording;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Record_item> formats = new ArrayList<Record_item>();
        // formats = getData();
        final Adapter_record adapter = new Adapter_record(this, R.layout.activity_new_record, formats);
        // final File edicc = Environment.getExternalStorageDirectory();
        isRecording=false;
        mText = (TextView) findViewById(R.id.recording);
        mButton = (Button) findViewById(R.id.button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        mdelete = (ImageButton) findViewById(R.id.delete);
        button = (Button) findViewById(R.id.button_first);


        mText.setVisibility(View.GONE);
        mButton.setVisibility(View.GONE);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mButton.setEnabled(true);
            }
        };

        final ArrayList<Record_item> finalFormats = formats;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRecording=true;
                //menu.findItem(R.id.actoin_complete).setVisible(true);
                button.setVisibility(View.GONE);
                fileName = DATE() + ".3gp";
                try {
                    File SDCardpath = Environment.getExternalStorageDirectory();

                    File myDataPath = new File(SDCardpath.getAbsolutePath() + "/Unimemo/녹음");
                    if (!myDataPath.exists())
                        myDataPath.mkdirs();
                    File recodeFile = new File(SDCardpath.getAbsolutePath() + "/Unimemo/녹음/" + fileName);
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    myAudioRecorder.setOutputFile(recodeFile.getAbsolutePath());
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();

                    rToast = Toast.makeText(getApplicationContext(), "Start recording", Toast.LENGTH_LONG);
                    rToast.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mButton.setVisibility(View.VISIBLE);
                mButton.setEnabled(false);
                mHandler = new Handler();

                mHandler.postDelayed(mRunnable, 5000);

                mProgressBar.setVisibility(ProgressBar.VISIBLE);
                mText.setVisibility(View.VISIBLE);

            }
        });



    }


    //20160826 jaewoo
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.back_title))
                        .setMessage(getString(R.string.back_message))
                        .setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(isRecording==true){
                                myAudioRecorder.release();
                                removeDir("녹음");
                                }
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.CANCEL), null).show();
                return false;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record, menu);
        menu.findItem(R.id.actoin_complete).setVisible(false);
        ArrayList<Record_item> formats = new ArrayList<Record_item>();
        // formats = getData();
        final Adapter_record adapter = new Adapter_record(this, R.layout.activity_new_record, formats);
        // final File edicc = Environment.getExternalStorageDirectory();
        final ArrayList<Record_item> finalFormats = formats;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.findItem(R.id.actoin_complete).setVisible(true);

                final ListView list = (ListView) findViewById(R.id.list);
                ArrayList<Record_item> formats = new ArrayList<Record_item>();
                mButton.setEnabled(false);
                mHandler = new Handler();
               /* CheapSoundFile.ProgressListener listener = new CheapSoundFile.ProgressListener() {
                    public boolean reportProgress(double frac) {

                        return true;
                    }
                };
*/

                if (myAudioRecorder != null) {
                    rToast = Toast.makeText(getApplicationContext(), "Success recording, file saved", Toast.LENGTH_LONG);
                    rToast.show();
                    myAudioRecorder.stop();
                    myAudioRecorder.reset();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                }
                fileName = DATE() + ".3gp";
                try {
                    File SDCardpath = Environment.getExternalStorageDirectory();
                    File myDataPath = new File(SDCardpath.getAbsolutePath() + "/Unimemo/녹음");
                    if (!myDataPath.exists())
                        myDataPath.mkdirs();
                    recodeFile_2 = new File(SDCardpath.getAbsolutePath() + "/Unimemo/녹음/" + fileName);
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    myAudioRecorder.setOutputFile(recodeFile_2.getAbsolutePath());
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();

                    rToast.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
/*
                CheapSoundFile cheapSoundFile = null;
                try {
                    cheapSoundFile = CheapSoundFile.create(fileName, listener);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int mSampleRate = cheapSoundFile.getSampleRate();

                int mSamplesPerFrame = cheapSoundFile.getSamplesPerFrame();

                int startFrame = Util.secondsToFrames(5.0, mSampleRate, mSamplesPerFrame);

                int endFrame = Util.secondsToFrames(10.0, mSampleRate, mSamplesPerFrame);
                String cut_record = "자른녹음파일.3gp";
                File SDCardpath = Environment.getExternalStorageDirectory();
                File myDataPath = new File(SDCardpath.getAbsolutePath() + "/Unimemo/" + DATE());
                if (!myDataPath.exists())
                    myDataPath.mkdirs();
                File recodeFile = new File(SDCardpath.getAbsolutePath() + "/Unimemo/" + DATE() + "/" + cut_record);


                try {
                    cheapSoundFile.WriteFile(recodeFile, startFrame, endFrame - startFrame);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                listener = new CheapSoundFile.ProgressListener() {
                    public boolean reportProgress(double frac) {

                        return true;
                    }
                };
*/

                mHandler.postDelayed(mRunnable, 5000);


                Record_item li = new Record_item(i, DATE());
                finalFormats.add(li);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actoin_complete) {




            showAddDialog();
        }
        if (id == android.R.id.home) {
            if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
                if(isRecording==true) {
                    myAudioRecorder.release();
                    removeDir("녹음");
                }
                finish();

            }
            Toast.makeText(this, "버튼 한번 더 누르면 녹음을 취소하고 메인화면으로 돌아갑니다", Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = System.currentTimeMillis();

        }
        return true;

    }


    private void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.record_complete, null);
        dialogBuilder.setView(dialogView);
        final EditText memoname = (EditText) dialogView.findViewById(R.id.Title);
        dialogBuilder.setTitle(getString(R.string.save));
        dialogBuilder.setMessage(getString(R.string.finish_record));
        //dialogBuilder.setMessage("");

        dialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String title = memoname.getText().toString();
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

                recodeFile_2.delete();

                // khlee: Save new memo into database

                LinkedList records = Paper.book().read(Constants.RECORD, new LinkedList());
                records.add(new Recycler_item(R.drawable.ic_mic_black_24dp, title, "",DATE()));
                Paper.book().write(Constants.RECORD, records);

                finish();
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.setCanceledOnTouchOutside(false);
        b.show();
    }

    public ArrayList<Record_item> getData() {
        ArrayList<Record_item> formats = new ArrayList<Record_item>();


        Record_item li = new Record_item(i, DATE());
        formats.add(li);
        return formats;

    }


    public String DATE() {
        long now = System.currentTimeMillis();
        java.util.Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNow = sdfNow.format(date);
        return strNow;
    }

    public static void removeDir(String dirName) {
        String mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Unimemo/" + dirName;

        File file = new File(mRootPath);
        File[] childFileList = file.listFiles();
        for (File childFile : childFileList) {
            if (childFile.isDirectory()) {
                removeDir(childFile.getAbsolutePath());    //하위 디렉토리
            } else {
                childFile.delete();    //하위 파일
            }
        }


        file.delete();    //
    }

}