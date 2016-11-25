package com.nice295.memo;

import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nice295.memo.Audio_Cuttermaster.CheapSoundFile;
import com.nice295.memo.Audio_Cuttermaster.Util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import io.paperdb.Paper;

public class NewRecordActivity extends AppCompatActivity {
    private static final String TAG = "NewRecordActivity";
    public int i = 0;
    int ii;

    public MediaRecorder myAudioRecorder = null;

    private boolean flag;
    private static Button button;
    private Button mButton;
    private ProgressBar mProgressBar;
    private ImageButton mdelete;
    private TextView mText;
    private long lastTimeBackPressed;
    private Runnable mRunnable;
    private Handler mHandler;
    private Menu mMenu; //khlee
    String fileName;
    private Toast rToast;
    //File recodeFile_2;
    boolean isRecording;
    String title;
    File myDataPath;
    File mRecodeFile;
    String folder_name;
    File filenow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Record_item> formats = new ArrayList<Record_item>();
        // formats = getData();

        final Adapter_record adapter = new Adapter_record(this, R.layout.activity_new_record, formats);
        // final File edicc = Environment.getExternalStorageDirectory();
        isRecording = false;
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
                isRecording = true;
                //menu.findItem(R.id.actoin_complete).setVisible(true);
                button.setVisibility(View.GONE);
                fileName = DATE() + ".3gp";
                try {
                    File SDCardpath = Environment.getExternalStorageDirectory();
                    myDataPath = new File(SDCardpath.getAbsolutePath() + "/Unimemo/녹음");
                    if (!myDataPath.exists())
                        myDataPath.mkdirs();
                    mRecodeFile = new File(SDCardpath.getAbsolutePath() + "/Unimemo/녹음/" + fileName);
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    myAudioRecorder.setOutputFile(mRecodeFile.getAbsolutePath());
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

                mHandler.postDelayed(mRunnable, 1000);

                mProgressBar.setVisibility(ProgressBar.VISIBLE);
                mText.setVisibility(View.VISIBLE);

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMenu.findItem(R.id.actoin_complete).setVisible(true);

                final ListView list = (ListView) findViewById(R.id.list);
                ArrayList<Record_item> formats = new ArrayList<Record_item>();
                mButton.setEnabled(false);
                mHandler = new Handler();

                if (myAudioRecorder != null) {
                    rToast = Toast.makeText(getApplicationContext(), "Success recording, file saved", Toast.LENGTH_LONG);
                    rToast.show();
                    myAudioRecorder.stop();
                    myAudioRecorder.reset();
                    myAudioRecorder.release();
                    myAudioRecorder = null;

                    //trim
                    CheapSoundFile.ProgressListener listner = new CheapSoundFile.ProgressListener() {
                        public boolean reportProgress(double frac) {
                            //Log.d(TAG, "frac: " + frac);
                            return true;
                        }
                    };

                    CheapSoundFile cheapSoundFile = null;
                    Log.d(TAG, "CheapSoundFile fileName: " + mRecodeFile);
                    try {
                        cheapSoundFile = CheapSoundFile.create(mRecodeFile.getAbsolutePath(), listner);
                        int mSampleRate = cheapSoundFile.getSampleRate();
                        int mSamplesPerFrame = cheapSoundFile.getSamplesPerFrame();
                        int frames = cheapSoundFile.getNumFrames();

                        int endTime = frames * mSamplesPerFrame / mSampleRate;
                        Log.d(TAG, "end time: " + endTime);

                        int startFrame = Util.secondsToFrames(endTime - 5.0, mSampleRate, mSamplesPerFrame);
                        int endFrame = Util.secondsToFrames(endTime, mSampleRate, mSamplesPerFrame);
                        Log.d(TAG, "endFrame: " + endFrame);
                        Log.d(TAG, "startFrame: " + startFrame);

                        if (endTime > 5) {
                            File outFile = new File(mRecodeFile.getAbsolutePath() + ".3gp");

                            Log.d(TAG, "outFile: " + outFile.getAbsolutePath() + ", " + endTime);
                            try {
                                cheapSoundFile.WriteFile(outFile, startFrame, endFrame - startFrame);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                fileName = DATE() + ".3gp";
                try {
                    File SDCardpath = Environment.getExternalStorageDirectory();
                    myDataPath = new File(SDCardpath.getAbsolutePath() + "/Unimemo/녹음");
                    if (!myDataPath.exists())
                        myDataPath.mkdirs();
                    mRecodeFile = new File(SDCardpath.getAbsolutePath() + "/Unimemo/녹음/" + fileName);
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    myAudioRecorder.setOutputFile(mRecodeFile.getAbsolutePath());
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

                mHandler.postDelayed(mRunnable, 1000);


                Record_item li = new Record_item(i, DATE());
                finalFormats.add(li);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();

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

                                if (isRecording == true) {
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

        mMenu = menu; //khlee

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
                if (isRecording == true) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(NewRecordActivity.this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.record_complete, null);

        builder.setView(dialogView);
        // final EditText memoname = (EditText) dialogView.findViewById(R.id.Title);
        final EditText input = new EditText(NewRecordActivity.this);
        builder.setTitle(getString(R.string.save));
        builder.setMessage(getString(R.string.finish_record));
        builder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        myAudioRecorder.stop();
                        myAudioRecorder.release();
                        myAudioRecorder = null;
                        //recodeFile_2.delete();
                        mRecodeFile.delete();
                        title = input.getText().toString();
                        folder_name = title;
                        File SDCardpath = Environment.getExternalStorageDirectory();
                        filenow = new File(SDCardpath.getAbsolutePath() + "/Unimemo/" + folder_name);
                        // khlee: Save new memo into database
                        if (myDataPath.renameTo(filenow)) {
                            Toast.makeText(getApplicationContext(), "변경 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            removeDir(folder_name);
                            Toast.makeText(getApplicationContext(), "변경 실패", Toast.LENGTH_SHORT).show();
                        }
                        LinkedList records = Paper.book().read(Constants.RECORD, new LinkedList());
                        records.add(new Recycler_item(R.drawable.ic_mic_black_24dp, title, "", DATE()));
                        Paper.book().write(Constants.RECORD, records);

                        finish();

                        // DO TASK
                    }
                });
        builder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // DO TASK
                    }
                });
// Set `EditText` to `dialog`. You can add `EditText` from `xml` too.
        //final EditText input = new EditText(NewRecordActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

// Initially disable the button
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                .setEnabled(false);
// OR you can use here setOnShowListener to disable button at first
// time.

// Now set the textchange listener for edittext
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Check if edittext is empty
                if (TextUtils.isEmpty(s)) {
                    // Disable ok button
                    ((AlertDialog) dialog).getButton(
                            AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    // Something into edit text. Enable the button.
                    ((AlertDialog) dialog).getButton(
                            AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }

            }
        });
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
