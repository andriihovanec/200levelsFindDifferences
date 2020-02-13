package com.olbigames.levels200finddifferences;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    //private int data = 0;
    private Context mContext;
    private int i;

    public BitmapWorkerTask(ImageView imageView, Context mCont, int ii) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        i = ii;
        mContext = mCont;
        imageViewReference = new WeakReference<ImageView>(imageView);
    }
    
    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        //data = params[0];
        return MyExpansionHelper.loadPic(mContext, i, 8);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}