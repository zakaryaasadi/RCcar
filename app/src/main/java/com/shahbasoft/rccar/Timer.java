package com.shahbasoft.rccar;

import android.os.Handler;

public class Timer {
    public interface CallbackTimer {
        void onTimerEnd();
        void onTimerRepeat();
    }

    private long startTime = 0;
    private long endTime = Long.MAX_VALUE;
    private long repeat = 0;
    private CallbackTimer callbackTimer;

    private int t1 = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            int time = (int) (System.currentTimeMillis() - startTime);
            int dt = time - t1;

            if(dt > repeat){
                callbackTimer.onTimerRepeat();
                t1 = time;
            }

            if(time > endTime) {
                callbackTimer.onTimerEnd();
                stop();
            }
            else
                timerHandler.postDelayed(this, 100);
        }
    };

    public void setCallbackTimer(CallbackTimer callbackTimer) {
        this.callbackTimer = callbackTimer;
    }

    public Timer(long repeatTime) {
        this.repeat = repeatTime;
    }

    public void start(){
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void stop(){
        timerHandler.removeCallbacks(timerRunnable);
    }

}
