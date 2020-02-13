package com.olbigames.levels200finddifferences;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

class BitmapWorkerTask2 extends AsyncTask<Integer, Void, Bitmap> {
    private final WeakReference<Bitmap> bmpReference;
    //private int data = 0;
    private Context mContext;
    private int i;

    public BitmapWorkerTask2(Bitmap bmp, Context mCont, int ii) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        i = ii;
        mContext = mCont;
        bmpReference = new WeakReference<Bitmap>(bmp);
    }
    
    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        //data = params[0];
        return MyExpansionHelper.loadPic(mContext, i, 0, 0);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
    	
        if (bmpReference != null && bitmap != null) {
            Bitmap bmp = bmpReference.get();
            if (bmp != null) {
            	bmp = bitmap;
            }
        }
    }

}