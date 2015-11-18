package com.inclutec.louis.lib;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import com.inclutec.louis.Globals;

import java.util.Date;

/**
 * Created by martin on 1/21/15.
 */
public class TimeCounterTask extends AsyncTask<Void, Long, Long> {
    private long startTime;

    private TaskListener mTaskListener = null;
    private Boolean isCancelled = false;

    public interface TaskListener {
        public void onIntervalElapsed(Long time);
    }

    public TimeCounterTask(TaskListener listener){
        this.mTaskListener = listener;
    }

    @Override
    protected Long doInBackground(Void... params) {
        startTime = new Date().getTime();

        if(Looper.myLooper() == Looper.getMainLooper()) {
            Log.e(Globals.TAG, "on main threead");
        }
        else{
            Log.e(Globals.TAG, "NOT on main threead");
        }

        while(!isCancelled()){
            publishProgress(countTime());

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                break;
            }
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);



        if (this.mTaskListener != null){


            this.mTaskListener.onIntervalElapsed(values[0]);
        }
    }


    public long countTime(){
        return new Date().getTime() - startTime;
    }
}
