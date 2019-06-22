package com.example.susi.audio;

public interface AudioDataReceivedListener {
    void start();

    void onAudioDataReceived(byte[] data, int length);

    void stop();
}
