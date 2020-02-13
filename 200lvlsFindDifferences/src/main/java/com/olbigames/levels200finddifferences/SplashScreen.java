package com.olbigames.levels200finddifferences;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

 public class SplashScreen extends Activity {
    
    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;
    
   
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SplashScreen sPlashScreen = SplashScreen.this;
        
        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(1000);
                    }
                }
                catch(InterruptedException ex){
                    // Run next activity
                    Intent intent = new Intent();
                    intent.setClass(sPlashScreen, MainActivity.class);
                    sPlashScreen.startActivity(intent);
                    sPlashScreen.finish();
                }
                finally
                {
                
                // Run next activity
                Intent intent = new Intent();
                intent.setClass(sPlashScreen, MainActivity.class);
                sPlashScreen.startActivity(intent);
                sPlashScreen.finish();
                }
            }
        };
        
        
		this.StartSplash();
    }
    
    public void StartSplash()
    {
    	

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
    	int displayW = metrics.widthPixels;
    	int displayH = metrics.heightPixels;

	    RelativeLayout layout_splash = new RelativeLayout(this);
	    layout_splash.setLayoutParams(new LayoutParams(displayW,displayH));
	    layout_splash.setBackgroundColor(Color.argb(255,0,0,0));
        layout_splash.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        
        ImageView splash = new ImageView(this); //получаем индентификатор ImageView с Splash картинкой
        /*
        int id = this.getResources().getIdentifier("raw/krypton", null, this.getPackageName());
		Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), id);
        splash.setImageBitmap(bmp);
        */
        //-------Async Bitmap loading
        BitmapWorkerTask3 task = new BitmapWorkerTask3(splash, this);
        task.execute(1);
        //---------------------------
        
        float ww = 557f;
        float hh = 205f;
        int w;
    	int h;
        if ((int)ww > displayW/2){
            float prop = hh/ww;
        	w = (int) displayW / 3;
        	h = (int)(w * prop);
        }else{
        	w = (int) ww;
        	h = (int) hh;
        }
        
        LayoutParams params = new RelativeLayout.LayoutParams(
        		w,
        		h);
        /*
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.leftMargin = displayW/2 - (int)(banner_height*2f);
		params.topMargin = displayH/2 + (int)(banner_height*0.2f);
		*/
        layout_splash.addView(splash,params);
        this.setContentView(layout_splash);
    	
        mSplashThread.start();
    }
    
    
    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
    	
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        
        return true;
    }    
    
    
} 