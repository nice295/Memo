package com.kgu.UNIMEMO.Audio_Cuttermaster;

/**
 * Created by PJH on 2016-11-16.
 */

public class Util {
    public static int secondsToFrames(double seconds, int mSampleRate,
                                      int mSamplesPerFrame) {
        return (int) (1.0 * seconds * mSampleRate / mSamplesPerFrame + 0.5);
    }
}
