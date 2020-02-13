package com.olbigames.levels200finddifferences;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

	class LevelDownloader extends AsyncTask<String, String, String> {
		
		ProgressBar progressBar;
		int fname;
		Context mContext;
		RelativeLayout shadeView;
		RelativeLayout layout_menu;
		
	    protected LevelDownloader(Context mContext, int fname, ProgressBar progressBar, RelativeLayout shadeView, RelativeLayout layout_menu) {
	    	this.progressBar = progressBar;
	    	this.fname = fname;
	    	this.mContext = mContext;
	    	this.shadeView = shadeView;
	    	this.layout_menu = layout_menu;
	    }
		
	    /**
	     * Before starting background thread
	     * Show Progress Bar Dialog
	     * */
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        //showDialog(progress_bar_type);
	        progressBar.setProgress(0);
	    }
	 
	    /**
	     * Downloading file in background thread
	     * */
	    @Override
	    protected String doInBackground(String... f_url) {
	        int count;
	        try {
	            URL url = new URL(f_url[0]);
	            URLConnection conection = url.openConnection();
	            conection.connect();
	            // getting file length
	            int lenghtOfFile = conection.getContentLength();
	 
	            // input stream to read file - with 8k buffer
	            InputStream input = new BufferedInputStream(url.openStream(), 8192);
	 

				File dir = mContext.getFilesDir();//Environment.getExternalStorageDirectory()
				
	            // Output stream to write file
	            FileOutputStream output = new FileOutputStream(new File( dir.getPath(), fname + ".zip"));

				//Log.e("FileOutputStream",  dir.getPath() + File.separator + fname + ".zip");
				
	            byte data[] = new byte[1024];
	 
	            long total = 0;
	 
	            while ((count = input.read(data)) != -1) {
	                total += count;
	                // publishing the progress....
	                // After this onProgressUpdate will be called
	                publishProgress(""+(int)((total*100)/lenghtOfFile));
	 
	                // writing data to file
	                output.write(data, 0, count);
	            }
	            
	            // flushing output
	            output.flush();
	 
	            // closing streams
	            output.close();
	            input.close();
	 
	        } catch (Exception e) {
	            //Log.e("Error: ", e.getMessage());
	        }
	 
	        return null;
	    }
	 
	    /**
	     * Updating progress bar
	     * */
	    protected void onProgressUpdate(String... progress) {
	        // setting progress percentage
	        //pDialog.setProgress(Integer.parseInt(progress[0]));

	        progressBar.setProgress(Integer.parseInt(progress[0]));
	   }
	 
	    /**
	     * After completing background task
	     * Dismiss the progress dialog
	     * **/
	    @Override
	    protected void onPostExecute(String file_url) {
	        // dismiss the dialog after the file was downloaded
	    	
	    	layout_menu.removeView(shadeView);
	    	
	    }
	    /*
		public static boolean isExternalStorageWritable() {
		    String state = Environment.getExternalStorageState();
		    if (Environment.MEDIA_MOUNTED.equals(state)) {
		        return true;
		    }
		    return false;
		}
*/
	 
	}