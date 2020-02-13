package com.olbigames.levels200finddifferences;

import java.io.File;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyExpansionHelper {

	boolean left;

	protected static Bitmap loadPic(Context c, int i, int scale){
		Bitmap bmp = MyExpansionHelper.loadPic( c, i, scale, 0);
		return bmp;
	}

	protected static Bitmap loadPic(Context c, int i, int scale, int side){

		String fileName = "pic" + String.format(Locale.US, "%04d",(i+1)) + "_1";

		if(scale != 0){
			fileName = "pic" + String.format(Locale.US, "%04d",(i+1));
		}else{
			fileName = "pic" + String.format(Locale.US, "%04d",(i+1)) + "_" + side;
		}

		//Bitmap b = null;

		/*
		if(i > 10){
			String zip_file_name = String.valueOf( (int) Math.ceil((i - 1) / 10) + 1 ) + ".zip";

			*/

		// Check that expansion file path exists
		//File expPath = new File(expansionFile.toString());
			/*
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				// Build the full path to the app's expansion files
				File root = Environment.getExternalStorageDirectory();
				String packageName = c.getPackageName();
				File zipPath = new File(root.toString() + "/Android/obb/" + packageName);
				if (zipPath.exists()) {
					Log.e("expPath", "exists");
				}

			*/
		/*
			File zipPath = c.getFilesDir();

		        ZipResourceFile expansionFile = null;
			    try {
					//expansionFile = new ZipResourceFile( c.getFilesDir() + File.separator + zip_file_name);
					Log.e("Path",  zipPath.getPath() + File.separator + zip_file_name);

					File ff = new File(zipPath.getPath() + File.separator + zip_file_name);

					if (ff.exists()) {
						Log.e("ff", "exists");
					}
					expansionFile = new ZipResourceFile( zipPath.getPath() + File.separator + zip_file_name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}


				String pathToFileInsideZip = fileName + ".jpg";//"raw" + File.separator +
			    if(scale != 0){
			        pathToFileInsideZip = fileName + ".jpg";// "raw" + File.separator + "thumb" + File.separator +
			    }

		        // Get an input stream for a known file inside the expansion file ZIPs
		        InputStream fileStream = null;
		        try {
					fileStream = expansionFile.getInputStream(pathToFileInsideZip);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}


		        if(fileStream != null){

				    BitmapFactory.Options dimensions = new BitmapFactory.Options();

					dimensions.inPreferredConfig = Bitmap.Config.ARGB_8888;
				    dimensions.inScaled = false;

				    b = BitmapFactory.decodeStream(fileStream, null, dimensions);

		        }

			//}

		}else{
	        */

		String pathToFile = "raw" + File.separator + fileName;

		if(scale != 0){
			pathToFile = "raw" + File.separator + fileName;
		}

		BitmapFactory.Options dimensions2 = new BitmapFactory.Options();

		dimensions2.inPreferredConfig = Bitmap.Config.ARGB_8888;

		dimensions2.inScaled = false;

		int id = c.getResources().getIdentifier(pathToFile, "raw", c.getPackageName());

		//}

		return BitmapFactory.decodeResource(c.getResources(), id, dimensions2);
	}



}

