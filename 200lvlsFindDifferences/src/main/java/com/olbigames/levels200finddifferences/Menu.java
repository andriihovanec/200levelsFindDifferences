package com.olbigames.levels200finddifferences;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Menu {

	private final int LEVEL_COUNT = AAAsettings.levelCount;
	private final int COL_COUNT ;
	private ImageView button[] = new ImageView[LEVEL_COUNT];
	private Button button_text[] = new Button[LEVEL_COUNT];
	private Button button_text2[] = new Button[LEVEL_COUNT];
	private ImageView button_krypton;
	private ImageView button_krypton_play;
	RelativeLayout layout_menu;
	ImageView soundButt;
	ImageView psButt;
	ImageView twButt;
	Context mCont;
	Bitmap son,soff,ps,fb,tw;
	private ImageView moreLvls;


    //public Menu(Context mContext, RelativeLayout layout_menu0, View.OnClickListener ocl, int displayW, int displayH, SQLiteHelper db, Button shareButton, Banner banner) {//LoginButton fbLoginButton

    public Menu(Context mContext, RelativeLayout layout_menu0, View.OnClickListener ocl, int displayW, int displayH, SQLiteHelper db, Banner banner) {//LoginButton fbLoginButton

			mCont = mContext;
    	layout_menu = layout_menu0;
    	
    	float inchesX =displayW / mContext.getResources().getDisplayMetrics().xdpi;
    	
    	int cols = 2 * ((int) (inchesX / 2));
    	
    	if(cols <= 2){
    		COL_COUNT = 4;
    	}else if(cols >= 5){
    		COL_COUNT = 10;
    	}else{
    		COL_COUNT = 2 * cols;
    	}
    	
    	int col_widht = displayW/(COL_COUNT+1);
    	int space = col_widht/(COL_COUNT+1);
    	
    	
    	float lvl_text_size = col_widht/7;
    	float dif_text_size = col_widht/5;
    	

		//-------sound button
		BitmapFactory.Options dimensions1 = new BitmapFactory.Options(); 
		dimensions1.inScaled = false;
		//dimensions.inSampleSize = 8;
		int id1 = mCont.getResources().getIdentifier("raw/sound", null, mCont.getPackageName());
		son = BitmapFactory.decodeResource(mCont.getResources(), id1, dimensions1);
		int id2 = mCont.getResources().getIdentifier("raw/soundoff", null, mCont.getPackageName());
		soff = BitmapFactory.decodeResource(mCont.getResources(), id2, dimensions1);
		

		RelativeLayout.LayoutParams aParams1 = new RelativeLayout.LayoutParams(
				col_widht/2,
				col_widht/2);
		aParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		aParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        aParams1.rightMargin = space;
        aParams1.topMargin = space/2;
		
        soundButt = new ImageView(mContext);
        soundButt.setId(1000);
        soundButt.setScaleType(ScaleType.CENTER_INSIDE);
		//button[i].setWidth(col_widht);
		//button[i].setHeight(col_widht);//displayH	" + (i+1) + "
		
		soundButt.setImageBitmap(son);
		layout_menu.addView(soundButt, aParams1);
		
		soundButt.setOnClickListener(ocl);
		//-------sound button end
		

		//-------play store button
		int idps = mCont.getResources().getIdentifier("raw/playstore", null, mCont.getPackageName());
		ps = BitmapFactory.decodeResource(mCont.getResources(), idps, dimensions1);
		
		RelativeLayout.LayoutParams psParams = new RelativeLayout.LayoutParams(
				col_widht/2,
				col_widht/2);
		psParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		psParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		psParams.topMargin = space/2;
		psParams.rightMargin = space + col_widht;
		
        psButt = new ImageView(mContext);
        psButt.setId(1002);
        psButt.setScaleType(ScaleType.CENTER_INSIDE);
		//button[i].setWidth(col_widht);
		//button[i].setHeight(col_widht);//displayH	" + (i+1) + "
		
        psButt.setImageBitmap(ps);
		layout_menu.addView(psButt, psParams);
		
		psButt.setOnClickListener(ocl);
		//-------play store  button end
		
/*
		//-------rate start
		RelativeLayout.LayoutParams rateParams = new RelativeLayout.LayoutParams(
				col_widht*2,
				col_widht/2);
		rateParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		rateParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rateParams.topMargin = 0;
		rateParams.rightMargin = 900;
		
		Button button_rate = new Button(mContext);
		
		button_rate.setId(1001);
		button_rate.setText("Rate");//CENTER_INSIDE

		layout_menu.addView(button_rate, rateParams);
		button_rate.setOnClickListener(ocl);
		//--------rate end
*/
		//--------facebook start
		/*
		int idfb = mCont.getResources().getIdentifier("raw/facebook", null, mCont.getPackageName());

		RelativeLayout.LayoutParams fbParams = new RelativeLayout.LayoutParams(
				col_widht/2,
				col_widht/2);
		fbParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		fbParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		fbParams.topMargin = space/2;
		fbParams.rightMargin = space + 2 * col_widht;
		
		
		shareButton.setBackgroundResource(idfb);
		shareButton.setText("");
		shareButton.setTextColor(Color.argb(0,0,0,0));
        
		layout_menu.addView(shareButton, fbParams);
		*/

		/*
		fbLoginButton.setBackgroundResource(idfb);
		fbLoginButton.setText("");
		fbLoginButton.setTextColor(Color.argb(0,0,0,0));
        
		layout_menu.addView(fbLoginButton, fbParams);
		*/
		//--------facebook end
		

		//--------twitter start
		
		int idtw = mCont.getResources().getIdentifier("raw/twitter", null, mCont.getPackageName());
		tw = BitmapFactory.decodeResource(mCont.getResources(), idtw, dimensions1);
		
		RelativeLayout.LayoutParams twParams = new RelativeLayout.LayoutParams(
				col_widht/2,
				col_widht/2);
		twParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		twParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		twParams.topMargin = space/2;
		twParams.rightMargin = space + 3 * col_widht;

		twButt = new ImageView(mContext);
		twButt.setId(1003);
		twButt.setScaleType(ScaleType.CENTER_INSIDE);

		twButt.setImageBitmap(tw);

		layout_menu.addView(twButt, twParams);

		twButt.setOnClickListener(ocl);
		//--------twitter end

		int checkid = mContext.getResources().getIdentifier("raw/check", null, mContext.getPackageName());
		Bitmap checkbmp = BitmapFactory.decodeResource(mContext.getResources(), checkid, dimensions1);
		ImageView check;

		int reloadid = mContext.getResources().getIdentifier("raw/reload", null, mContext.getPackageName());
		Bitmap reloadbmp = BitmapFactory.decodeResource(mContext.getResources(), reloadid, dimensions1);
		ImageView reload;
		
		BitmapFactory.Options dimensions = new BitmapFactory.Options(); 
		dimensions.inScaled = true;
		dimensions.inSampleSize = 8;
		
		/*
		int loaded_zips = 1;
		File dir = mContext.getFilesDir();
		for(int n = 2; n < 16; n++){
			File file = new File(dir + File.separator + n + ".zip");
			if(!file.exists()){
				loaded_zips = n - 1;
				n = 16;
			}
		}
		*/

        int i=0;
		do {
		    String difCount = db.getDifCount(i+1);

			int leftmargin = (1+(i*2)%COL_COUNT)*space + i*2%COL_COUNT*col_widht;
			int topMargin = (int)(((int)(i*2/COL_COUNT))*(space + col_widht) + col_widht/2 + space);
			
			RelativeLayout.LayoutParams aParams = new RelativeLayout.LayoutParams(
					col_widht,
					col_widht);
			aParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			aParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        aParams.leftMargin = leftmargin;
	        aParams.topMargin = topMargin;//space +
			
			button[i] = new ImageView(mContext);
			
			button[i].setId(i+1);
			button[i].setScaleType(ScaleType.CENTER_CROP);//CENTER_INSIDE
			//button[i].setWidth(col_widht);
			//button[i].setHeight(col_widht);//displayH	" + (i+1) + "

			button[i].setBackgroundColor(Color.argb(255,0,0,0));

			//button[i].setImageResource(mContext.getResources().getIdentifier("raw/pic" + String.format("%04d",(i+1)) + "_1", null, mContext.getPackageName()));
/*
			int id = mContext.getResources().getIdentifier("raw/pic" + String.format("%04d",(i+1)) + "_1", null, mContext.getPackageName());
			Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), id, dimensions);
*/
			
			//Bitmap bmp = MyExpansionHelper.loadPic(mContext,"pic" + String.format("%04d",(i+1)) + "_1", 8);
			//button[i].setImageBitmap(bmp);
			loadBitmap( button[i], i);
			
			//button[i].setBackground(bmp);
/*
			button[i].setText("0/10");
			button[i].setTextColor(Color.argb(255,255,255,255));
			button[i].setTextSize(30f);
			button[i].setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
*/
			layout_menu.addView(button[i], aParams);
			button[i].setOnClickListener(ocl);


			if(Integer.valueOf(difCount) == 10){
			
			RelativeLayout.LayoutParams aParamscheck = new RelativeLayout.LayoutParams(
					col_widht/3,
					col_widht/3);
			aParamscheck.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			aParamscheck.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			aParamscheck.leftMargin = leftmargin;
			aParamscheck.topMargin = topMargin;
			
			check = new ImageView(mContext);
			check.setImageBitmap(checkbmp);
			layout_menu.addView(check, aParamscheck);

			
			RelativeLayout.LayoutParams aParamsReload = new RelativeLayout.LayoutParams(
					col_widht,
					col_widht);
			aParamsReload.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			aParamsReload.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			aParamsReload.leftMargin = leftmargin;
			aParamsReload.topMargin = topMargin;

			reload = new ImageView(mContext);
			reload.setId(i+1 + 2000);
			reload.setImageBitmap(reloadbmp);
			layout_menu.addView(reload, aParamsReload);
			reload.setOnClickListener(ocl);
			
			}
			
			
			RelativeLayout.LayoutParams aParams2 = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			/*
			aParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			aParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        aParams2.leftMargin = (1+(i)%COL_COUNT)*space + i%COL_COUNT*col_widht;
	        aParams2.topMargin = (int)(((int)(1+i/COL_COUNT))*(space + col_widht));
*/
	        aParams2.addRule(RelativeLayout.RIGHT_OF, button[i].getId());
	        aParams2.addRule(RelativeLayout.ALIGN_TOP, button[i].getId());
	        button_text[i] = new Button(mContext);
	        button_text[i].setId(i+301);

			button_text[i].setWidth(col_widht);
			//button_text[i].setHeight(space + (int)spToPixels( mContext, 20f));//displayH	" + (i+1) + "
			button_text[i].setBackgroundColor(Color.argb(0,0,0,0));
			String levelStr = mContext.getResources().getString(R.string.level);
			button_text[i].setText(levelStr + " " + (i+1));
			button_text[i].setTextColor(Color.argb(255,255,255,255));
			button_text[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, lvl_text_size);
			button_text[i].setTypeface(null,Typeface.BOLD);
			
			layout_menu.addView(button_text[i], aParams2);
			
			button_text[i].setOnClickListener(ocl);
			

			
			RelativeLayout.LayoutParams aParams3 = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			/*
			aParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			aParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        aParams2.leftMargin = (1+(i)%COL_COUNT)*space + i%COL_COUNT*col_widht;
	        aParams2.topMargin = (int)(((int)(1+i/COL_COUNT))*(space + col_widht));
*/
			aParams3.addRule(RelativeLayout.RIGHT_OF, button[i].getId());
			aParams3.addRule(RelativeLayout.BELOW, button_text[i].getId());
	        button_text2[i] = new Button(mContext);
	        button_text2[i].setId(i+601);

			button_text2[i].setWidth(col_widht);
			
			button_text2[i].setBackgroundColor(Color.argb(0,0,0,0));
			
			
			button_text2[i].setText(difCount+"/10");
			if(Integer.valueOf(difCount) == 10){
				button_text2[i].setTextColor(Color.argb(255,60,240,60));
			}else{
				button_text2[i].setTextColor(Color.argb(255,255,255,255));
			}
			button_text2[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, dif_text_size);
			button_text2[i].setTypeface(null,Typeface.BOLD);

			
			layout_menu.addView(button_text2[i], aParams3);
			
			button_text2[i].setOnClickListener(ocl);
			
			
			i++;
		} while (i<LEVEL_COUNT);
		//} while (i<loaded_zips*10);
		


		
		//int id9 = mCont.getResources().getIdentifier("raw/add", null, mCont.getPackageName());
		//Bitmap moreLvlsBM = BitmapFactory.decodeResource(mCont.getResources(), id9, dimensions1);
		
		int leftmargin = (1+(i*2)%COL_COUNT)*space + i*2%COL_COUNT*col_widht;
		int topMargin = (int)(((int)(i*2/COL_COUNT))*(space + col_widht) + col_widht/2 + space);
		
		/*RelativeLayout.LayoutParams aParams_moreLvls = new RelativeLayout.LayoutParams(
				col_widht,
				col_widht);*/
		RelativeLayout.LayoutParams aParams_moreLvls = new RelativeLayout.LayoutParams(
				0,
				0);
		aParams_moreLvls.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		aParams_moreLvls.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		aParams_moreLvls.leftMargin = leftmargin;
		aParams_moreLvls.topMargin = topMargin;
		
		moreLvls = new ImageView(mContext);
		
		moreLvls.setId(1007);
		/*moreLvls.setScaleType(ScaleType.CENTER_CROP);

		moreLvls.setBackgroundColor(Color.argb(255,0,0,0));
		moreLvls.setImageBitmap(moreLvlsBM);*/
		
		layout_menu.addView(moreLvls, aParams_moreLvls);
	//	moreLvls.setOnClickListener(ocl);
		
		

		boolean show_banner = false;
		String banner_src = banner.getImgSrc();
		
		if(banner_src != "dontshowabanner"){
		
		int id6 = mCont.getResources().getIdentifier(banner_src, null, mContext.getPackageName());
		
		/*if ( id6 != 0 ) {  // the resouce exists...
		
		show_banner = true;

		Bitmap ftd = BitmapFactory.decodeResource(mCont.getResources(), id6, dimensions1);
		
		RelativeLayout.LayoutParams aParamsFtd = new RelativeLayout.LayoutParams(
				(int)(4 * col_widht),
				(int) (col_widht / 1.6));
		aParamsFtd.leftMargin = (displayW - 4 * col_widht) / 2;
		aParamsFtd.topMargin = 2 * space;
		

		aParamsFtd.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		//aParamsFtd.addRule(RelativeLayout.BELOW, moreLvls.getId());
		aParamsFtd.addRule(RelativeLayout.BELOW, moreLvls.getId());
		button_krypton = new ImageView(mContext);
		button_krypton.setId(1008);
		
		
		button_krypton.setBackgroundColor(Color.argb(0,0,0,0));
		button_krypton.setImageBitmap(ftd);
		
		layout_menu.addView(button_krypton, aParamsFtd);
		
		button_krypton.setOnClickListener(ocl);
		
	    }*/
		
		}
		

		int id4 = mCont.getResources().getIdentifier("raw/olbi", null, mCont.getPackageName());
		Bitmap krypton = BitmapFactory.decodeResource(mCont.getResources(), id4, dimensions1);
		
		RelativeLayout.LayoutParams aParamsKrypton = new RelativeLayout.LayoutParams(
				(int)(2.717 * col_widht / 2),
				col_widht / 2 +  2 * space);
		aParamsKrypton.leftMargin = space;
		if(show_banner){
			aParamsKrypton.topMargin = 2 * space + (int)(col_widht / 1.6);
		}else{
			aParamsKrypton.topMargin = 2 * space;
		}
		

		aParamsKrypton.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		//aParamsKrypton.addRule(RelativeLayout.BELOW, button[i-1].getId());
		aParamsKrypton.addRule(RelativeLayout.BELOW, moreLvls.getId());
		button_krypton = new ImageView(mContext);
		button_krypton.setId(1004);
		
		
		button_krypton.setBackgroundColor(Color.argb(0,0,0,0));
		button_krypton.setImageBitmap(krypton);
		
		layout_menu.addView(button_krypton, aParamsKrypton);
		
		button_krypton.setOnClickListener(ocl);
		

		

		int id5 = mCont.getResources().getIdentifier("raw/en_app_rgb_wo_60", null, mCont.getPackageName());
		Bitmap krypton_play = BitmapFactory.decodeResource(mCont.getResources(), id5, dimensions1);
		
		RelativeLayout.LayoutParams aParamsKryptonPlay = new RelativeLayout.LayoutParams(
				(int)(2.86666 * col_widht / 2),
				col_widht / 2 +  2 * space);
		aParamsKryptonPlay.rightMargin = space;
		if(show_banner){
			aParamsKryptonPlay.topMargin = 2 * space + (int)(col_widht / 1.6);
		}else{
			aParamsKryptonPlay.topMargin = 2 * space;
		}
		

		aParamsKryptonPlay.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//aParamsKryptonPlay.addRule(RelativeLayout.BELOW, button[i-1].getId());
		aParamsKryptonPlay.addRule(RelativeLayout.BELOW, moreLvls.getId());
		button_krypton_play = new ImageView(mContext);
		button_krypton_play.setId(1005);
		
		
		button_krypton_play.setBackgroundColor(Color.argb(0,0,0,0));
		button_krypton_play.setImageBitmap(krypton_play);
		
		layout_menu.addView(button_krypton_play, aParamsKryptonPlay);
		
		button_krypton_play.setOnClickListener(ocl);

		

    }

    public void loadBitmap( ImageView imageView, int ii) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, mCont, ii);
        task.execute(ii);
    }
    
/*
    public Menu(Context mContext, RelativeLayout layout_menu0, View.OnClickListener ocl, int displayW, int displayH ) {
    	
    	layout_menu = layout_menu0;
    	
    	int col_widht = displayW/(COL_COUNT+1);
    	int space = col_widht/(COL_COUNT+1);
    	
    	int ROW_COUNT = 1 + (int) ((LEVEL_COUNT-1)/COL_COUNT);
    	tableLayout = new TableLayout(mContext);
		
        int i=0;
		do {

			TableRow tableRow = new TableRow(mContext);
			tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			

			for (int j = 0; j < COL_COUNT; j++) {
				
			button[i*COL_COUNT+j] = new Button(mContext);
			
			button[i*COL_COUNT+j].setWidth(col_widht);
			button[i*COL_COUNT+j].setHeight(col_widht);//displayH	" + (i+1) + "

			button[i*COL_COUNT+j].setBackgroundColor(Color.argb(255,0,0,0));
			button[i*COL_COUNT+j].setBackgroundResource(mContext.getResources().getIdentifier("raw/pic000" + (i+1) + "_1", null, mContext.getPackageName()));

			button[i*COL_COUNT+j].setText("0/10");
			button[i*COL_COUNT+j].setTextColor(Color.argb(255,255,255,255));
			button[i*COL_COUNT+j].setTextSize(30f);
			
			TableLayout.LayoutParams aParams1 = new TableLayout.LayoutParams(
					col_widht,
					col_widht);
			
				tableRow.addView(button[i*COL_COUNT+j], j, aParams1);
			}
			
			tableLayout.addView(tableRow, i);

			i++;
		} while (i<ROW_COUNT);
		

		layout_menu.addView(tableLayout,new TableLayout.LayoutParams(
		        LayoutParams.WRAP_CONTENT,
		        LayoutParams.WRAP_CONTENT));
		
    }
    */
    public int getLevel(int id){

        int i=0;
		do {
			if (id == button[i].getId()){
				return (i+1);
    		}else if(id == button_text[i].getId()){
				return (i+1);
    		}else if(id == button_text2[i].getId()){
				return (i+1);
    		}
			i++;
		} while (i<LEVEL_COUNT);
		
    	return 0;
    }
    
    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }
    
    public static float spToPixels(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px*scaledDensity;
    }
    

    public void setMute(float to){
    	if(to == 1f){
			soundButt.setImageBitmap(son);
    	}else{
			soundButt.setImageBitmap(soff);
    	}
    }

    
    
    
/*
    public Menu(Context mContext, RelativeLayout layout_menu0, View.OnClickListener ocl, int displayW, int displayH ) {
    	
    	layout_menu = layout_menu0;
    	*/
    	/*
		BitmapFactory.Options dimensions = new BitmapFactory.Options(); 
		dimensions.inScaled = false;
		
		int id = mContext.getResources().getIdentifier("raw/pic0001_1", null, mContext.getPackageName());
		bmp = BitmapFactory.decodeResource(mContext.getResources(), id, dimensions);
		*/
		
		//picW = bmp.getWidth();
		//picH = bmp.getHeight();
    	/*
    	int col_widht = displayW/(COL_COUNT+1);
    	int space = col_widht/(COL_COUNT+1);
		
        int i=0;
		do {

			RelativeLayout.LayoutParams aParams = new RelativeLayout.LayoutParams(
					col_widht,
					col_widht);
			aParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			aParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        aParams.leftMargin = (1+(i)%COL_COUNT)*space + i%COL_COUNT*col_widht;
	        aParams.topMargin = (int)(space +((int)(i/COL_COUNT))*(space + col_widht));
			
			button[i] = new Button(mContext);

			button[i].setWidth(col_widht);
			button[i].setHeight(col_widht);//displayH	" + (i+1) + "

			button[i].setBackgroundColor(Color.argb(255,0,0,0));
			button[i].setBackgroundResource(mContext.getResources().getIdentifier("raw/pic000" + (i+1) + "_1", null, mContext.getPackageName()));

			button[i].setText("0/10");
			button[i].setTextColor(Color.argb(255,255,255,255));
			button[i].setTextSize(30f);

			button[i].setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		    
			layout_menu.addView(button[i], aParams);
			
			button[i].setOnClickListener(ocl);
			
			
			
			RelativeLayout.LayoutParams aParams2 = new RelativeLayout.LayoutParams(
					col_widht,
					space + (int)spToPixels( mContext, 20f));
			aParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			aParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        aParams2.leftMargin = (1+(i)%COL_COUNT)*space + i%COL_COUNT*col_widht;
	        aParams2.topMargin = (int)(((int)(1+i/COL_COUNT))*(space + col_widht));
			
	        button_text[i] = new Button(mContext);

			button_text[i].setWidth(col_widht);
			button_text[i].setHeight(space);//displayH	" + (i+1) + "
			button_text[i].setBackgroundColor(Color.argb(0,0,0,0));
			button_text[i].setText("Level " + (i+1));
			button_text[i].setTextColor(Color.argb(255,255,255,255));
			button[i].setTextSize(20f);
			
			layout_menu.addView(button_text[i], aParams2);
			
			button_text[i].setOnClickListener(ocl);
			
			
			
			i++;
		} while (i<LEVEL_COUNT);
		*/
		/*
		button[0] = new Button(mContext);
		
		button[0].setWidth(150);
		button[0].setHeight(150);
		button[0].setBackgroundResource(mContext.getResources().getIdentifier("raw/pic0001_1", null, mContext.getPackageName()));
		
		//button[0].setId(1000);
		
		layout_menu.addView(button[0], aParams);
		
		button[0].setOnClickListener(ocl);
    	*/
    //}
    
	
}
