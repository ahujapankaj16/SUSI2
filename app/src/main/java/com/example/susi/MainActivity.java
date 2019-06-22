package com.example.susi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.susi.audio.AudioDataSaver;
import com.example.susi.audio.RecordingThread;

public class MainActivity extends AppCompatActivity {
    boolean isDetectionOn;
    RecordingThread recordingThread;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHotword();
        txt = findViewById(R.id.hello);
        txt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initHotword();
                    }
                }
        );
    }

    private void initHotword() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            AppResCopy.copyResFromAssetsToSD(this);

            recordingThread = new RecordingThread(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    MsgEnum message = MsgEnum.getMsgEnum(msg.what);
                    switch (message) {
                        case MSG_ACTIVE:
                            Log.d("hello   ", "hello     ");
                            break;
                        case MSG_INFO:
                            break;
                        case MSG_VAD_SPEECH:
                            break;
                        case MSG_VAD_NOSPEECH:
                            break;
                        case MSG_ERROR:
                            break;
                        default:
                            super.handleMessage(msg);
                            break;
                    }
                }
            }, new AudioDataSaver());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(recordingThread !=null && !isDetectionOn) {
            recordingThread.startRecording();
            isDetectionOn = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(recordingThread !=null && !isDetectionOn) {
            recordingThread.startRecording();
            isDetectionOn = true;
        }
    }
}
