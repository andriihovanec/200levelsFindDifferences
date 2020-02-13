package com.olbigames.levels200finddifferences;

import java.util.ArrayList;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.appbrain.AppBrain;
/*
import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.widget.LoginButton;
*/
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends FragmentActivity implements RewardedVideoAdListener {

	private final int LEVEL_COUNT = AAAsettings.levelCount;
	private static final String STATE_LEVEL = "Level";
	private static final String SHOW_END_LEVEL = "showEndLevel";
	private static final String VOLUME_LEVEL = "volumeLevel";
	private static final String LAST_INTERST_SHOW = "lastInterstShow";
	
	private static final String SCROLL_Y = "scrollY";
	//private static final String SCROLL_YV = "scrollYV";
	//private static final String SCROLL_YH = "scrollYH";
	
	
	/*
	private static final String SURFACE_STATUS = "surfaceStatus";
	 */
	private boolean availablePlayServices = true;

	private Context mContext;
	
	private long touchLastTime;
	private int newTouch = 1;

	private RelativeLayout layout_menu;
	private RelativeLayout layout;
	
	private GLSurfaceView surface;
	private Menu menu;
	
	private int displayW;
	private int displayH;
	
	private int banner_height;
	
	private GameRenderer GameRend;

	private long lastInterstShow;
	private boolean showInterst = false;
	private InterstitialAd interstitial;
	private int useAdSize = 0;
	private AdView adView;
	private AdView adView2;
	private Button btnNext;
	private Button btnMenu;
	private Button gestures_tip;
	Handler gestures_tip_handler;
	
	private boolean showAd;
	
	private SQLiteHelper db;
	
	private TextView textDifs;
	private TextView btnHint;

	private int level = 0;
	
	private int showEndLevel = 0;
	private int surfaceStatus = 0;
	
	private TextWatcher tw, tw2;
	
	private ArrayList<Finger> fingers = new ArrayList<Finger>();		// Все пальцы, находящиеся на экране
	
	private float volumeLevel = 1f;

	
	//facebook
	//private Button fbButton;
	
//	private static final int SPLASH = 0;
//	private static final int SELECTION = 1;
//	private static final int FRAGMENT_COUNT = SELECTION +1;

//	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	//private MainFragment mainFragment;

	/*
	private SplashFragment splashFragment;

	LoginButton authButton;
	
	Session FBsession;

	
	private String gender;
	*/

//	private String locale;
//	private String birth;
	
	//--------
	ScrollView sv;
	   
	private Banner banner;
	
	private int scrollYV;
	private int scrollYH;
	private float scrollY;

	private RewardedVideoAd rewardedAd;


	Button btnrewardedAD;
	boolean btnrewardedADIsVisible = false;


	private FirebaseAnalytics mFirebaseAnalytics;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // Obtain the FirebaseAnalytics instance.

		/*FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);*/
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


		String admob_app_id = this.getResources().getString(R.string.admob_app_id);

		MobileAds.initialize(this, admob_app_id);

        AppBrain.init(this);

        lastInterstShow = System.currentTimeMillis() - 1200000;


		rewardedAd = MobileAds.getRewardedVideoAdInstance(this);
		rewardedAd.setRewardedVideoAdListener(this);

		btnrewardedAD = new Button(this);

		loadRewardedVideoAd();
		
		mContext = this;
		
		banner = new Banner(this);
		
	    textDifs = new TextView(this);
	    btnHint = new TextView(this);
		db = new SQLiteHelper(this, textDifs, btnHint);
		
        // если хотим, чтобы приложение постоянно имело портретную ориентацию
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // если хотим, чтобы приложение было полноэкранным
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // и без заголовка
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
    	displayW = metrics.widthPixels;
    	displayH = metrics.heightPixels;
    	
    	
    	
    	this.setVolumeControlStream(AudioManager.STREAM_MUSIC); 
    	
	    banner_height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
	    
	    int banner_width_468 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 468, getResources().getDisplayMetrics());
	    int banner_height_60 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
	    
	    if ((banner_height_60 * 4 + banner_width_468) <= displayW){
	    	banner_height = banner_height_60;
	    	useAdSize = 1;
	    }


	    tw = new TextWatcher(){
            @Override
	        public void afterTextChanged(Editable s) {
	        	if(( strint(s.toString())) >= 10){

			    	showEndLevel = 1;
	        		
	        	    Handler handler = new Handler();
	        	    handler.postDelayed(new Runnable() { 
	        	         public void run() {
	        	     	    	db.addHint(1);
	        			    	finishLevel();
	        	         } 
	        	    }, 1500); 
	        		
	        	}
	        }
            @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
	    };


	    tw2 = new TextWatcher(){
            @Override
	        public void afterTextChanged(Editable s) {
	        	if(true){
	        	
	        	}
	        }
            @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
	    };
	    

	    //this.startLevel(lvl);
		
	}

	private void loadRewardedVideoAd() {
		String rewarded_id = this.getResources().getString(R.string.rewarded_ad_unit_id);
		rewardedAd.loadAd(rewarded_id, new AdRequest.Builder().build());
	}


	@Override
	public void onRewarded(RewardItem reward) {
		//Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
		//		reward.getAmount(), Toast.LENGTH_SHORT).show();

		db.addHint(reward.getAmount());

		btnrewardedADIsVisible = false;
		btnrewardedAD.setVisibility(View.INVISIBLE);
	}


	@Override
	public void onRewardedVideoAdLeftApplication() {
		///Toast.makeText(this, "onRewardedVideoAdLeftApplication",
			//	Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdClosed() {
		//Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int errorCode) {
		//Toast.makeText(this, "onRewardedVideoAdFailedToLoad" + errorCode, Toast.LENGTH_SHORT).show();
		btnrewardedADIsVisible = false;
		btnrewardedAD.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onRewardedVideoCompleted() {

	}

	@Override
	public void onRewardedVideoAdLoaded() {
		//Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
		btnrewardedADIsVisible = true;
		btnrewardedAD.setVisibility(View.VISIBLE);
	}

	@Override
	public void onRewardedVideoAdOpened() {
		//Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoStarted() {
		//Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
	}


	private void openRateDialog() {
		
		AlertDialog.Builder rateDialog = new AlertDialog.Builder(
				this);
		rateDialog.setTitle(this.getResources().getString(R.string.rate));
		rateDialog.setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface hi, int dd){
				// TODO Auto-generated method stub
				db.dontShowRate();
				rateMyApp();
			}
		});
		rateDialog.setNeutralButton(this.getResources().getString(R.string.later), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface hi, int dd){
				// TODO Auto-generated method stub
				db.showRateLater();
			}
		});
		rateDialog.setNegativeButton(this.getResources().getString(R.string.never), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface hi, int dd){
				// TODO Auto-generated method stub
				db.dontShowRate();
			}
		});

		rateDialog.show();
	}
	
	/*private void siteKry(){
		  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://")));
	}*/
	
	private void rateMyApp(){
		Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
		  startActivity(goToMarket);
		} catch (ActivityNotFoundException e) {
		  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
		}
	}

	private void allMyApp(){

		Uri uri = Uri.parse("market://search?q=pub:Olbi Games");
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
		  startActivity(goToMarket);
		} catch (ActivityNotFoundException e) {
		  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=pub:Olbi Games")));
		}
		/*
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://search?q=pub:Your Publisher Name"));
		startActivity(intent);
		*/
	}
	
	private void twitterApp(){
		
		/*String tweetUrl = "https://twitter.com/intent/tweet?text="+this.getResources().getString(R.string.app_name)+"&url="
                + "http://";*/
		String tweetUrl = "https://twitter.com/intent/tweet?text="+this.getResources().getString(R.string.app_name)+" Olbi Games";
		Uri uri = Uri.parse(tweetUrl);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
		/*
	    Intent share = new Intent(Intent.ACTION_SEND);
	    share.putExtra(Intent.EXTRA_TEXT, "Here's some text for Twitter.");
	    startActivity(Intent.createChooser(share, "Share this via"));
	    */
		/*
		Intent goToFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/36KrGames"));
		try {
		  startActivity(goToFacebook);
		} catch (ActivityNotFoundException e) {
		  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/36KrGames")));
		}
		*/
	}
	
	

	// Invoke displayInterstitial() when you are ready to display an interstitial.
	private void displayInterstitial() {
		if (interstitial.isLoaded()) {
			
			interstitial.show();
			
			lastInterstShow = System.currentTimeMillis();
			showInterst = false;
			db.resetInterstitialLastDiffCount();
			
		}
	}


	
	private void getAd(){
/*
		int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if(isAvailable == ConnectionResult.SUCCESS){
*/



			if(isNetworkConnected()){
				showAd = true;
			}else{
				showAd = false;
			}

/*
	    	int gend = AdRequest.GENDER_UNKNOWN;
		    if(gender!= null){
		    	if(gender == "male"){
		    		gend = AdRequest.GENDER_MALE;
		    	}else if(gender == "female"){
		    		gend = AdRequest.GENDER_FEMALE;
		    	}
		    }
*/
		    

		    /*
			int showRate = db.showRate();
			if(showRate==0){
			*/
				int fica = db.getAllFindedCount();
				int last_fica = db.getInterstitialLastDiffCount();
				
				long now = System.currentTimeMillis();
				long elapsed = now - lastInterstShow;
				
				if(fica - last_fica > AAAsettings.showInterstitialAfterFindingDifferences 
						|| ((fica > AAAsettings.showInterstitialAfterFindingDifferences) && elapsed > AAAsettings.showInterstitialAfterMinElapsedTime)){
				
						lastInterstShow = now;
					
					    // Create the interstitial.
						String interst_ad_unit_id = this.getResources().getString(R.string.interst_ad_unit_id);
					    interstitial = new InterstitialAd(this);
					    interstitial.setAdUnitId(interst_ad_unit_id);
					    // Create ad request.
					    AdRequest adRequestI = new AdRequest.Builder().build();
					    // Begin loading your interstitial.
					    interstitial.loadAd(adRequestI);
					    
					    showInterst = true;
				}
			
			//}
			
			String banner_ad_unit_id_1 = this.getResources().getString(R.string.banner_ad_unit_id_1);
			String banner_ad_unit_id_2 = this.getResources().getString(R.string.banner_ad_unit_id_2);
		    // Создание экземпляра adView.
			adView = new AdView(this);
		    adView.setAdUnitId(banner_ad_unit_id_1);//Идентификатор рекламного блока
		    if(useAdSize == 1){
		    	adView.setAdSize(AdSize.FULL_BANNER);
		    }else{
		    	adView.setAdSize(AdSize.BANNER);
		    }

		    // Создание экземпляра adView.
			adView2 = new AdView(this);
		    adView2.setAdUnitId(banner_ad_unit_id_2);//Идентификатор рекламного блока
		    adView2.setAdSize(AdSize.BANNER);
		    
		    /*
		    .setGender(AdRequest.GENDER_FEMALE)// Демографический таргетинг
		    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		    .addTestDevice("6faaec928950ac96e28f3f6c27073357")	//359092055600484
		    		//MD5    6faaec928950ac96e28f3f6c27073357
		    */
		    /*
		    AdRequest adRequest = new AdRequest();
		    String[] keywords = getResources().getStringArray(R.array.key_words);
		    Set<String> set = new HashSet<String>();
		    int count = 0;
		    while (count < keywords.length) {
		        set.add(keywords[count]);
		        count++;
		    }
		    adRequest.addKeywords(set);
		    adView.loadAd(adRequest);
		    */

		    AdRequest adRequest = new AdRequest.Builder()
		    //.setGender(gend)
		    //.setBirthday(new GregorianCalendar(1985, 1, 1).getTime())
		    //.setLocation(location)
		    .build();
		    
			// Загрузка adView с объявлением.
		    adView.loadAd(adRequest);

		    adView.setAdListener(new AdListener() {
		    	  @Override
		    	  public void onAdOpened() {
		    	    // Сохраняет состояние приложения перед переходом к оверлею объявления.
		    	  }
		    	});
		//-------------------
		    /*
		    .setGender(AdRequest.GENDER_FEMALE)// Демографический таргетинг
		    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		    .addTestDevice("6faaec928950ac96e28f3f6c27073357")	//359092055600484
		    		//MD5    6faaec928950ac96e28f3f6c27073357
		    */
		    AdRequest adRequest2 = new AdRequest.Builder()
		    //.setGender(gend)
		    .build();
		    
		    // Загрузка adView с объявлением.
		    adView2.loadAd(adRequest2);
		    
		    adView2.setAdListener(new AdListener() {
		    	  @Override
		    	  public void onAdOpened() {
		    	    // Сохраняет состояние приложения перед переходом к оверлею объявления.
		    	  }
		    	});
		//-------------------
/*
		}else{
			showAd = false;
		}
*/
	}


	private void startMenu()
	{
		level = 0;
		showEndLevel = 0;
		

	    if (surface != null) {
			surface.clearAnimation();//?????
			surface.destroyDrawingCache();//?????
			surfaceStatus = 0;
			layout.removeAllViewsInLayout();
	    }

	    
        sv = new ScrollView(this);
        sv.setLayoutParams(new LayoutParams(displayW,displayH));
        RelativeLayout ll = new RelativeLayout(this);
		ll.setLayoutParams(new LayoutParams(displayW,displayH));
		//ll.setOrientation(1);
		sv.addView(ll);

		
	    layout_menu = new RelativeLayout(this);
	    layout_menu.setLayoutParams(new LayoutParams(displayW,displayH));
    	layout_menu.setBackgroundColor(Color.argb(255,0,0,0));
	    
		View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	//Log.e("FFFFF", "getId :" + v.getId());
            	if(v.getId() == 1000){
            		// mute
            		if(volumeLevel == 0f){volumeLevel = 1f;}else{volumeLevel = 0f;}
            		menu.setMute(volumeLevel);
            		
            	}else if(v.getId() == 1008){

            		getFTDgame();
            	}else if(v.getId() == 1007){

            		allMyApp();
            	}/*else if(v.getId() == 1004){
            		siteKry();
            	}*/else if(v.getId() == 1005){
            		allMyApp();
            	}else if(v.getId() == 1001){
            		openRateDialog();
            	}else if(v.getId() == 1002){
            		rateMyApp();
            	}else if(v.getId() == 1003){
            		twitterApp();
            	}else if(v.getId() > 2000){
            		
            		db.refreshLVL(v.getId() - 2000);
            		
	            	//int lvl = menu.getLevel(v.getId());
	            	//if(lvl!=0){
		            	level = v.getId() - 2000;
		            	startLevel(level);
	            	//}
	            	
            	}else{
	            	int lvl = menu.getLevel(v.getId());
	            	if(lvl!=0){
		            	level = lvl;
		            	startLevel(lvl);
	            	}
            	}
                
            }
        };
        
		layout_menu.addView(sv);
		

		/*
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
				displayW, 
	    		displayH);
		params2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		RelativeLayout shadeView = new RelativeLayout(this);
        shadeView.setBackgroundColor(Color.argb(170,0,0,0));
	    ObjectAnimator anim = ObjectAnimator.ofFloat(shadeView, "alpha", 0f, 1f);
	    anim.setDuration(1000);
	    
        ProgressBar progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
        
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300,50);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        shadeView.addView(progressBar,params);
        layout_menu.addView(shadeView, params2);
        // starting new Async Task
	    anim.start();
        new LevelDownloader( mContext, 2, progressBar, shadeView, layout_menu).execute(AAAsettings.zip_links[1]);
		*/
        
        
        setContentView(layout_menu);



        //Facebook
/*
	        fbButton = new Button(this);

			// TODO 
	        fbButton.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {

	            	if( splashFragment != null){
	            		getSupportFragmentManager().beginTransaction().remove(splashFragment).commit();
	            	}
		        	splashFragment = new SplashFragment();
		        	
		        	getSupportFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, splashFragment)
					.commit();
	            }
	        });

	    */
		//menu = new Menu(this, ll, ocl, displayW, displayH, db, fbButton, banner);
		menu = new Menu(this, ll, ocl, displayW, displayH, db, banner);



		menu.setMute(volumeLevel);
		
		sv.post(new Runnable() {
		    @Override
		    public void run() {
				if (displayH > displayW){
					//sv.scrollTo( 0, scrollYV);
					sv.scrollTo( 0, (int) (scrollY * sv.getChildAt(0).getHeight()));
				}else{
					//sv.scrollTo( 0, scrollYH);
					sv.scrollTo( 0, (int) (scrollY * sv.getChildAt(0).getHeight()));
				}
		    } 
		});

        
	}
	
	
	  
	protected void getFTDgame() {
		String src = banner.getSrc();
		Uri uri = Uri.parse("market://details?id=" + src);
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		if (isOnline()){
			try {
				startActivity(goToMarket);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + src)));
			}
		}else{
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + src)));
		}
	}
	
	protected boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	protected static void AlertError(Context mContext){
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    	// Add the buttons
    	builder.setMessage(R.string.some_error)
        .setTitle(R.string.error);

    	builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	               // User clicked OK button
    	           }
    	       });
    	builder.create();
	}

	
	private void startLevel(int lvl)
	{
		level = lvl;

		fingers.clear();
		
		if(availablePlayServices){
			getAd();
		}

		if(layout_menu != null){
			if (displayH > displayW){
				scrollYV = sv.getScrollY();
				scrollY = (float) scrollYV / (float) sv.getChildAt(0).getHeight();
			}else{
				scrollYH = sv.getScrollY();
				scrollY = (float) scrollYH / (float) sv.getChildAt(0).getHeight();
			}
			layout_menu.removeAllViewsInLayout();
		}

	    if (surface != null) {
			surface.clearAnimation();//?????
			surface.destroyDrawingCache();//?????
			surfaceStatus = 0;
			layout.removeAllViewsInLayout();
	    }
	    
		if(layout != null){
			layout.removeAllViewsInLayout();
		}
		
		surface = new GLSurfaceView(this);

	    
	    // Check if the system supports OpenGL ES 2.0.
	    final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
	    final boolean supportsEs2 = ( configurationInfo.reqGlEsVersion >= 0x20000 || Build.FINGERPRINT.startsWith( "generic" ) );
	    
	    
	    if (supportsEs2)
	    {
	        // Request an OpenGL ES 2.0 compatible context.
	    	surface.setEGLContextClientVersion(2);
			surface.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
			//surface.setEGLConfigChooser(8, 8, 8, 8, 16, 8);

	    	//surface.getHolder().setFormat(PixelFormat.RGBA_8888);

	    	GameRend = new GameRenderer(this, displayW, displayH, banner_height, db, level, volumeLevel);
	        surface.setRenderer(GameRend);
	        //surfaceStatus = 1;
	    	/*
	    	GameRend = new GameRenderer(this, displayW, displayH, banner_height, db, lvl);
	        surface.setRenderer(GameRend);
	        */
	    }
	    else
	    {
	        // This is where you could create an OpenGL ES 1.x compatible
	        // renderer if you wanted to support both ES 1 and ES 2.
	    	AlertError(this);
	        return;
	    }

	    layout = new RelativeLayout(this);
	    layout.setBackgroundColor(Color.argb(255,0,0,0));
	    
	    int banner_height2 = banner_height;
	    //if (displayH > displayW & showAd){
		if (displayH > displayW){
	    	banner_height2 = banner_height*2;
	    }

	    RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
	    		RelativeLayout.LayoutParams.WRAP_CONTENT, 
	    		displayH - banner_height2);
	    
	    layout.addView(surface, adParams);


		//------------


	    if (showAd & availablePlayServices)
	    {
		    adParams = new RelativeLayout.LayoutParams(
		    		RelativeLayout.LayoutParams.WRAP_CONTENT, 
		    		RelativeLayout.LayoutParams.WRAP_CONTENT);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
	        
	        //layout_menu.addView(adView, adParams);
		    layout.addView(adView, adParams);

	    }else{
			String banner_src = banner.getImgSrc();

			if(banner_src != "dontshowabanner"){

				int id6 = this.getResources().getIdentifier(banner_src, null, mContext.getPackageName());

				if ( id6 != 0 ) {  // the resouce exists...

				    adParams = new RelativeLayout.LayoutParams(
				    		((banner_height * 32) / 5), 
				    		banner_height);
			        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			        
			    	Button ourBanner = new Button(this);
				    ourBanner.setWidth((banner_height * 32) / 5);
				    ourBanner.setHeight(banner_height);
				    ourBanner.setBackgroundResource(id6);
				    layout.addView(ourBanner, adParams);
				    
				    ourBanner.setOnClickListener(new OnClickListener() {
				    	@Override
				    	public void onClick(View v) {
				    		// TODO Auto-generated method stub
				    		getFTDgame();
				    	}
				    });
				}
		    
			}
			
	    }
	    
	    //------------
		    adParams = new RelativeLayout.LayoutParams(
		    		banner_height, 
		    		banner_height);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        if (displayH > displayW)adParams.bottomMargin = banner_height;
	        
		    btnMenu = new Button(this);
		    btnMenu.setWidth(banner_height);
		    btnMenu.setHeight(banner_height);
		    btnMenu.setBackgroundResource(R.raw.button_menu);
		    
		    layout.addView(btnMenu, adParams);
		    
		    
		//------------
		    adParams = new RelativeLayout.LayoutParams(
		    		banner_height, 
		    		banner_height);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	        if (displayH > displayW)adParams.bottomMargin = banner_height;
		    
		    btnNext = new Button(this);
		    btnNext.setHeight(banner_height);
		    btnNext.setWidth(banner_height);
		    btnNext.setBackgroundResource(R.raw.button_next);
		    
		    layout.addView(btnNext, adParams);

		    
		//------------
		    btnMenu.setOnClickListener(new OnClickListener() {
		    	@Override
		    	public void onClick(View v) {
		    		// TODO Auto-generated method stub
		    		if(showEndLevel != 1){
		    			startMenu();
		    		}
		    	}
		    });
		    
		    
		//------------
		    btnNext.setOnClickListener(new OnClickListener() {
		    	@Override
		    	public void onClick(View v) {
		    		// TODO Auto-generated method stub
		    		if(showEndLevel != 1){
		    			nextLevel();
		    		}
		    	}
		    });
		    

		//------------

            //Log.e("FFFFF", "lvl :" + level);
		    String difCount = db.getDifCount(level);
		    adParams = new RelativeLayout.LayoutParams(
		    		banner_height, 
		    		banner_height);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

	    	float textDifs_size = banner_height/3.5f;
	    	
	        if (displayH > displayW)adParams.bottomMargin = banner_height;
	        adParams.leftMargin = banner_height;

		    textDifs.removeTextChangedListener(tw);
		    
		    textDifs.setWidth(banner_height);
		    textDifs.setHeight(banner_height);
		    textDifs.setBackgroundResource(R.raw.dif_counter);
		    textDifs.setText(difCount+"/10");
		    textDifs.setTextSize(TypedValue.COMPLEX_UNIT_PX, textDifs_size);
		    textDifs.setTextColor(Color.argb(255,255,255,255));//Color.parseColor("#ffffff")
		    textDifs.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		    textDifs.setTypeface(null,Typeface.BOLD);
		    layout.addView(textDifs, adParams);
		    
		    //if ( Integer.valueOf(difCount) < 10){
		    
		    textDifs.addTextChangedListener(tw);
		    //}
		    
		    adParams = new RelativeLayout.LayoutParams(
		    		banner_height, 
		    		banner_height);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	    	
	        if (displayH > displayW)adParams.bottomMargin = banner_height;
	        adParams.rightMargin = banner_height;
	        
	        btnHint.removeTextChangedListener(tw2);
		    
	        btnHint.setWidth(banner_height);
	        btnHint.setHeight(banner_height);
	        btnHint.setBackgroundResource(R.raw.hint);
	        btnHint.setText(String.valueOf(db.getHintCount()));
	        btnHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, textDifs_size);
	        btnHint.setTextColor(Color.argb(255,255,255,255));//Color.parseColor("#ffffff")
	        btnHint.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
	        btnHint.setTypeface(null,Typeface.BOLD);
		    layout.addView(btnHint, adParams);

		    btnHint.addTextChangedListener(tw2);

			//------------
		    btnHint.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	// TODO Auto-generated method stub
			    	useHint();
			    }
			});

		    if(db.showGesturesTip()){
			    
			    gestures_tip = new Button(this);
			    
			    int gtsize = 0;
			    if (displayW > displayH){
			    	gtsize = displayH - banner_height*3;
			    }else{
			    	gtsize = displayW - banner_height*2;
			    }
			    int gtspaceW = (displayH - gtsize)/2;
			    int gtspaceH = (displayW - gtsize)/2;
		    	
			    RelativeLayout.LayoutParams gtParams = new RelativeLayout.LayoutParams(
			    		gtsize, 
			    		gtsize);
			    gtParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			    gtParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			    
			    gtParams.bottomMargin = gtspaceW;
			    gtParams.rightMargin = gtspaceH;
		        //if (displayH > displayW & showAd)gtParams.bottomMargin = banner_height;
		        //gtParams.rightMargin = banner_height;
		        gestures_tip.setWidth(gtsize);
		        gestures_tip.setHeight(gtsize);
		        gestures_tip.setBackgroundResource(R.raw.gestures_tip);
			    layout.addView(gestures_tip, gtParams);
			    
			    
			    gestures_tip_handler = new Handler(); 
			    gestures_tip_handler.postDelayed(new Runnable() { 
	    	         public void run() {
	    	        	try {
	    	        		 layout.removeView(gestures_tip);
	    	        		 db.addCountGesturesTip();
						} catch (Exception e) {
							// TODO: handle exception
						}
	    	         }
	    	    }, 5000);

			    gestures_tip.setOnClickListener(new OnClickListener() {
				    @Override
				    public void onClick(View v) {
				    	// TODO Auto-generated method stub
				    	layout.removeView(gestures_tip);
				    	gestures_tip_handler.removeCallbacksAndMessages(null);
		        		db.addCountGesturesTip();
				    	gestures_tip.setOnClickListener(null);
				    }
				});
			    
		    }
		    
		    /*
		    Thread waitThread =  new Thread(){
	            @Override
	            public void run(){
	                try {
	                    synchronized(this){
	                        // Wait given period of time or exit on touch
	                        wait(2000);
	                    }
	                }
	                catch(InterruptedException ex){
	                    // Run next activity
        	        	try {
        	        		layout.removeView(gestures_tip);
    					} catch (Exception e) {
    						// TODO: handle exception
    					}
	                }
	                finally
	                {
	                
	                // Run next activity
        	        	try {
        	        		layout.removeView(gestures_tip);
    					} catch (Exception e) {
    						// TODO: handle exception
    					}
	                }
	            }
	        };
	        
	        waitThread.start();
	        */
	        
		    
		    

        setContentView(layout);

        surfaceStatus = 1;
        
        if(showEndLevel == 1){
        	finishLevel();
        };
	}

	protected void useHint()
	{
		int difCount = Integer.valueOf(db.getDifCount(level));
		int count = db.getHintCount();
		if(count>0 && difCount<10){
			GameRend.useHint();
        	btnHint.setText(String.valueOf(count - 1));
		}
	}
	
	
	protected void nextLevel()
	{
		/*
		if (e1 == 0){
			e1 = 1;
		}else{
			surface.clearAnimation();//?????
			surface.destroyDrawingCache();//?????
			layout.removeAllViewsInLayout();
			surfaceStatus = 0;
		}
		*/
		if(getLevel()<LEVEL_COUNT){
			
			if (surface != null){
				surface.clearAnimation();//?????
				surface.destroyDrawingCache();//?????
				layout.removeAllViewsInLayout();
				surfaceStatus = 0;
			}
			fingers.clear();
			startLevel(getLevel()+1);
		}else{
			startMenu();
		}
		
	}

	protected int strint (String str)
	{
		int intres = 0;
		char ch0 = str.charAt(0);
		char ch1 = str.charAt(1);
		
		if (ch1 == '/'){
			intres = Integer.valueOf(String.valueOf(ch0));
		}else{
			intres = 10 * Integer.valueOf(String.valueOf(ch0)) + Integer.valueOf(String.valueOf(ch1));
		}


        //Log.e("FFFFF", "intres :" + intres);
		return intres;
	}
	
	protected void finishLevel()
	{
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				displayW, 
	    		displayH);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    	
		RelativeLayout shadeView = new RelativeLayout(this);
		
		if(showAd){
		    if (adView != null) {
		    	layout.removeView(adView);
		    }
		}
		
        shadeView.setBackgroundColor(Color.argb(170,0,0,0));
	    ObjectAnimator anim = ObjectAnimator.ofFloat(shadeView, "alpha", 0f, 1f);
	    anim.setDuration(1000);

	    layout.addView(shadeView, params);
	    
	    anim.start();

	    /*
	    if(showAd){
	    	*/
			params = new RelativeLayout.LayoutParams(
					banner_height*7,
					(int) (banner_height*4.2f));
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.leftMargin = displayW/2 - (int)(banner_height*3.5f);
			params.topMargin = displayH/2 - (int)(banner_height*2.5f);
/*
	    }else{

			params = new RelativeLayout.LayoutParams(
					banner_height*5,
					(int) (banner_height*4.2f));
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.leftMargin = displayW/2 - (int)(banner_height*2.5f);
			params.topMargin = displayH/2 - (int)(banner_height*2.5f);

	    }
		*/
		TextView windowView = new TextView(this);
	    windowView.setBackgroundColor(Color.argb(255,30,30,30));
	    
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
	    	setViewShape(windowView);
	    }
	    
		shadeView.addView(windowView, params);
		
		
		if(showAd & availablePlayServices){


			//------------
			RelativeLayout.LayoutParams adParams2 = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			adParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			adParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			adParams2.leftMargin = displayW/2 - (int)(banner_height/5f*16f);
			adParams2.topMargin = displayH/2 - (int)(banner_height*2.1f);
			
		    shadeView.addView(adView2, adParams2);
			//------------

	    	
	    	/*
			params = new RelativeLayout.LayoutParams(
		    		banner_height*7,
		    		banner_height*3);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.leftMargin = displayW/2 - (int)(banner_height*3.5f);
			params.topMargin = displayH/2 - (int)(banner_height*1.5f);
			*/
	    }else{
			String banner_src = banner.getImgSrc();

			if(banner_src != "dontshowabanner"){

				int id6 = this.getResources().getIdentifier(banner_src, null, mContext.getPackageName());

				if ( id6 != 0 ) {  // the resouce exists...

					RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
							((banner_height * 32) / 5),
							banner_height);
					adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
					adParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
					adParams.leftMargin = displayW/2 - (int)(banner_height/5f*16f);
					adParams.topMargin = displayH/2 - (int)(banner_height*2.1f);

					Button ourBanner = new Button(this);
					ourBanner.setWidth((banner_height * 32) / 5);
					ourBanner.setHeight(banner_height);
					ourBanner.setBackgroundResource(id6);
					layout.addView(ourBanner, adParams);

					ourBanner.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							getFTDgame();
						}
					});
				}

			}

		}
		

		params = new RelativeLayout.LayoutParams(
	    		banner_height, 
	    		banner_height);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.leftMargin = displayW/2 - (int)(banner_height*2.75f);
		//if(showAd){
			params.topMargin = displayH/2 - (int)(banner_height*0.8f);
		/*}
		else{
			params.topMargin = displayH/2 - (int)(banner_height*1.5f);
		}*/
		
		Button btnmenu = new Button(this);
		btnmenu.setHeight(banner_height);
		btnmenu.setWidth(banner_height);
		btnmenu.setBackgroundResource(R.raw.button_menu);
	    
		shadeView.addView(btnmenu, params);
		

		params = new RelativeLayout.LayoutParams(
	    		banner_height, 
	    		banner_height);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.leftMargin = displayW/2 - (int)(banner_height*0.5f);
		//if(showAd){
			params.topMargin = displayH/2 - (int)(banner_height*0.8f);
		/*}
		else{
			params.topMargin = displayH/2 - (int)(banner_height*1.5f);
		}*/
		
		Button btncheck = new Button(this);
		btncheck.setHeight(banner_height);
		btncheck.setWidth(banner_height);
		btncheck.setBackgroundResource(R.raw.winnercup);
	    
		shadeView.addView(btncheck, params);




		params = new RelativeLayout.LayoutParams(
				(int)(banner_height*3.2f),
				banner_height);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.leftMargin = displayW/2 - (int)(banner_height*1.6f);
		//if(showAd){
			params.topMargin = displayH/2 + (int)(banner_height*0.4f);
		/*}
		else{
			params.topMargin = displayH/2 - (int)(banner_height*0.3f);
		}*/

		Button btnrewarded = new Button(this);
		btnrewardedAD = btnrewarded;
		btnrewarded.setHeight(banner_height);
		btnrewarded.setWidth((int)(banner_height*3.2f));
		btnrewarded.setBackgroundResource(R.raw.free_hints);
		if(btnrewardedADIsVisible){
			btnrewarded.setVisibility(View.VISIBLE);
		}else{
			btnrewarded.setVisibility(View.INVISIBLE);
		}

		shadeView.addView(btnrewarded, params);

        //btnrewarded.setVisibility(View.INVISIBLE);

		
		params = new RelativeLayout.LayoutParams(
	    		banner_height, 
	    		banner_height);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.leftMargin = displayW/2 + (int)(banner_height*1.75f);
		//if(showAd){
			params.topMargin = displayH/2 - (int)(banner_height*0.8f);
		/*}
		else{
			params.topMargin = displayH/2 - (int)(banner_height*1.5f);
		}*/
		
		Button btnnext = new Button(this);
		btnnext.setHeight(banner_height);
		btnnext.setWidth(banner_height);
		btnnext.setBackgroundResource(R.raw.button_next);
	    
		shadeView.addView(btnnext, params);
	    

		//------------
			btnmenu.setOnClickListener(new OnClickListener() {
		    	@Override
		    	public void onClick(View v) {
		    		// TODO Auto-generated method stub
		    		showEndLevel = 0;
		    		startMenu();
		    	}
		    });

			btnrewarded.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (rewardedAd.isLoaded()) {
						rewardedAd.show();
					}
				}
			});
		    
		    btnnext.setOnClickListener(new OnClickListener() {
		    	@Override
		    	public void onClick(View v) {
		    		// TODO Auto-generated method stub
		    		showEndLevel = 0;
		    		nextLevel();
		    	}
		    });
		
		int showRate = db.showRate();
		if(showRate!=0 & ((db.getAllFindedCount() - showRate) > 100)){
			openRateDialog();
		}
		
		if(showInterst == true){
		    displayInterstitial();
		}
		
	}




	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	protected void setViewShape(TextView view)
	{
		GradientDrawable shape =  new GradientDrawable();
		shape.setCornerRadius( 40 );
		shape.setColor(Color.argb(255,30,30,30));
		shape.setStroke(1, Color.argb(255,100,100,100));
		view.setBackground(shape);
	}
	
	protected int getLevel()
	{
		return level;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_MENU) {
	    	if (level != 0){
	    		startMenu();
	    	}
	        return true;
	    }
	    else if(keyCode == KeyEvent.KEYCODE_HOME)
	    {
			finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
/*
    	if(splashFragment != null && splashFragment.isVisible()){
        	getSupportFragmentManager().beginTransaction().remove(splashFragment).commit();
    	}else{
		*/
		if (level == 0){
			openQuitDialog();
		}else{
			startMenu();
		}
		/*
    	}
    	*/
		//super.onBackPressed();
	}



	private void openQuitDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				this);
		quitDialog.setTitle(this.getResources().getString(R.string.quit));
		quitDialog.setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface hi, int dd){
				// TODO Auto-generated method stub

			    AppBrain.getAds().maybeShowInterstitial(mContext);
			    
				finish();
			}
		});

		quitDialog.setNegativeButton(this.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface hi, int dd){
				// TODO Auto-generated method stub
				
			}
		});

		quitDialog.show();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
	    // Save the user's current game state
	    savedInstanceState.putInt(STATE_LEVEL, level);
	    savedInstanceState.putInt(SHOW_END_LEVEL, showEndLevel);
	    savedInstanceState.putFloat(VOLUME_LEVEL, volumeLevel);
	    savedInstanceState.putFloat(LAST_INTERST_SHOW, lastInterstShow);

		if(layout_menu != null){
			if (displayH > displayW){
				scrollYV = sv.getScrollY();
				scrollY = (float) scrollYV / (float) sv.getChildAt(0).getHeight();
			}else{
				scrollYH = sv.getScrollY();
				scrollY = (float) scrollYH / (float) sv.getChildAt(0).getHeight();
			}
		}
		
		//Log.e("scrollYH = ", String.valueOf(scrollYH));
		//Log.e("sv.getChildAt(0).getHeight() = ", String.valueOf(sv.getChildAt(0).getHeight()));
		//Log.e("scrollY = ", String.valueOf(scrollY));
		
		savedInstanceState.putFloat(SCROLL_Y, scrollY);
		//savedInstanceState.putInt(SCROLL_YV, scrollYV);
		//savedInstanceState.putInt(SCROLL_YH, scrollYH);

	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    // Always call the superclass so it can restore the view hierarchy
	    super.onRestoreInstanceState(savedInstanceState);
	    
	    level = savedInstanceState.getInt(STATE_LEVEL);
	    showEndLevel = savedInstanceState.getInt(SHOW_END_LEVEL);
	    volumeLevel = savedInstanceState.getFloat(VOLUME_LEVEL);
	    lastInterstShow = savedInstanceState.getLong(LAST_INTERST_SHOW);

	    scrollY = savedInstanceState.getFloat(SCROLL_Y);
	    //scrollYV = savedInstanceState.getInt(SCROLL_YV);
		//scrollYH = savedInstanceState.getInt(SCROLL_YH);
		
	}

	private boolean isNetworkConnected() {
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) {
		   // There are no active networks.
			  return false;
		  } else{
			  return true;
		  }
	}
	
	 public boolean isPackageExisted(String targetPackage){
		   PackageManager pm=getPackageManager();
		   try {
		    pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
		       } catch (NameNotFoundException e) {
		    return false;
		    }  
		    return true;
	 }
	 
    @Override
    protected void onStart() {
		super.onStart();
    }

    @Override
    protected void onStop() {
		super.onStop();
    }
	
	@Override
	protected void onResume()
	{
		rewardedAd.resume(this);
	    // The activity must call the GL surface view's onResume() on activity onResume().
	    super.onResume();
	    
	    if(surfaceStatus == 2){
		    surface.onResume();
		    surfaceStatus = 1;
	    }
	    if (adView != null) {
	    	adView.resume();
		}
		if (adView2 != null) {
		    adView2.resume();
		}
		/*
	    if (showAd){ 
	    	adView.resume();
	    	adView2.resume();
	    }
	    */

			getAd();


	    if(level == 0){
		    this.startMenu();
	    }else{
	    	this.startLevel(level);
	    }

	    //if(isPackageExisted("com.facebook.katana")){
	    //if(isClass("com.facebook.AppEventsLogger")&& isClass("com.facebook.katana.provider.AttributionIdProvider")){
	    //	AppEventsLogger.activateApp(this);
	    //}
	    //}
	}
	
	@Override
	protected void onPause()
	{
	    // The activity must call the GL surface view's onPause() on activity onPause().

	    if (adView != null) {
	    	adView.pause();
		}
		if (adView2 != null) {
		    adView2.pause();
		}
		
		
		/*
	    if (showAd){ 
	    	adView.pause();
	    	adView2.pause();
	    }
	    */
	    if(surfaceStatus == 1){
	    	surface.onPause();
	    	surfaceStatus = 2;
	    }
		rewardedAd.pause(this);
	    super.onPause();
	    
	    //com.facebook.katana.provider.AttributionIdProvider
	    //if(isPackageExisted("com.facebook.katana")){
	    //if(isClass("com.facebook.AppEventsLogger")&& isClass("com.facebook.katana.provider.AttributionIdProvider")){
	    //	AppEventsLogger.deactivateApp(this);
	    //}
	    //}
	}
	
	@Override
	public void onDestroy() {

	    if (adView != null) {
	    	adView.destroy();
		}
		if (adView2 != null) {
		    adView2.destroy();
		}
		/*
		if (showAd){
	    	adView.destroy();
	    	adView2.destroy();
	    }
	    */
	    if(surfaceStatus != 0){
			surface.clearAnimation();//?????
			surface.destroyDrawingCache();//?????
			//GameRend.Finish();
	    	surfaceStatus = 0;
	    }
		rewardedAd.destroy(this);
		super.onDestroy();
	} 
	

	@Override
	public boolean onTouchEvent(MotionEvent e) {
	    // MotionEvent reports input details from the touch screen
	    // and other input controls. In this case, you are only
	    // interested in events where the touch position changed.

		//----------------------------------------
		//MotionEvent eCopy = MotionEvent.obtain(e);
	    int id = e.getPointerId(e.getActionIndex());	// Идентификатор пальца
	    int action = e.getActionMasked(); // Действие
	    if(action  == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN){
	      fingers.add(e.getActionIndex(), new Finger(id, (int)e.getX(), (int)e.getY()));
		}else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP){
	    	try {
	    		if(fingers.get(e.getActionIndex()) != null){
	    			fingers.remove(fingers.get(e.getActionIndex()));// Удаляем палец, который был отпущен
	    		}
			} catch (Exception e2) {
			}
		}else if(action == MotionEvent.ACTION_MOVE){
		  int finsize = 0;
		  if (fingers.size() == 1){
			  finsize = 1;
		  }else if(fingers.size() > 1){
			  finsize = 2;
		  }
	      for(int n = 0; n < finsize; n++){			// Обновляем положение всех пальцев

	    	  if(e.getPointerCount() > n){
	    		fingers.get(n).setNow((int)e.getX(n), (int)e.getY(n));
	    	  }
	  	
	      }
	    }
	    //----------------------------------------
	    
	    
	    
		if(surfaceStatus == 1 & showEndLevel != 1 & level > 0){
		
	    
		if(fingers.size() > 1){
			
		    double now = checkDistance(fingers.get(0).Now, fingers.get(1).Now);
		    double before = checkDistance(fingers.get(0).Before, fingers.get(1).Before);
		    
		    
		    if(GameRend!=null){ GameRend.doScale((float) (before - now));}
			
		}else if(fingers.size() == 1){
	    
			
			//if(fingers.size() == 1){
				float xd = 0f;
				float yd = 0f;
				xd = (float)(fingers.get(0).Before.x - fingers.get(0).Now.x);
				yd = (float)(fingers.get(0).Before.y - fingers.get(0).Now.y);
				if(GameRend!=null){ GameRend.doMove( xd, yd); }
			//}
			
			
		//----------------------------------------
    	long now = System.currentTimeMillis();
    	long elapsed = now - touchLastTime;
    	
    	if (elapsed > 100 && newTouch!=1){
    		newTouch = 1;
		}else{
    		newTouch = 0;
        	touchLastTime = now;
		}
		
    	if(newTouch == 1){
    	
		    float x = e.getX();
		    float y = e.getY();
		    GameRend.Touched( x, y);
    	}
    	//----------------------------------------
    	
    	
		}
		
		}
		
	    return true;
	}
	
	static double checkDistance(Point p1, Point p2){	// Функция вычисления расстояния между двумя точками
	    return Math.sqrt((p1.x - p2.x)*(p1.x - p2.x)+(p1.y - p2.y)*(p1.y - p2.y));
	}


}