package com.example.ayushimathur.outsideselect;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by ayushimathur on 7/24/16.
 */
public class populateFinalList extends AsyncTask<Integer,Void,Void> {

    @Override
    protected Void doInBackground(Integer... integers) {
            while (true) {
                clashCheck.finalList.add(integers[0]);
                // in onCreate and do mHandler.post(new Runnable()) here
                if (Thread.currentThread().isInterrupted()) break;
            }
        return null;
    }
}
