package com.olbigames.levels200finddifferences;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

class BitmapWorkerTask3 extends AsyncTask<Integer, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private Context mContext;

    public BitmapWorkerTask3(ImageView imageView, Context mCont) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        mContext = mCont;
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {

        int id = mContext.getResources().getIdentifier("raw/olbi", null, mContext.getPackageName());
        return BitmapFactory.decodeResource(mContext.getResources(), id);

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