package com.olbigames.levels200finddifferences;

import java.util.Locale;
import java.util.Random;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class Banner {

	int n = 0;
	Context cont;
	
	String[] href = {
			"com.olbigames.find10differences200levels",
			"com.kryptongames.finddifferences",
			"com.kryptongames.findthedifferencesrooms",
			"com.kryptongames.find10differences",
			"com.kryptongames.findthedifferencenewyear",
			"com.kryptongames.findthedifferences",
			"com.kryptongames.find6differences",
			"com.kryptongames.findthedifference150levels",
			"com.kryptongames.finddifferences150levels2",
			"com.kryptongames.findthedifference200levels",
			"com.olbigames.finddifferences200levels"
			};

	int[] indexes = {1,2,3,5,7,9,10,11,13,15,16};
	int[] packages = {0,0,0,0,0,0,0,0,0,0,0};
	int[] showpackages = null;

	
	public Banner(Context con){
		
		cont = con;
		
		int s = 0;
		int i = 0;
    	while( i < href.length ){

	    	if(!isPackageExisted(href[i])){
	    		packages[i] = 1;
	    		n = 1;
	    		s++;
	    	}
    		i++;
    	}
		if(s > 0){
	    	showpackages = new int[s];
	    	
	    	int j2 = 0;
	    	for (int j = 0; j < href.length && j2 < s; j++) {
				if( packages[j] == 1 ){
					showpackages[j2] = j;
					j2++;
				}
			}
		}
	}
	
	
	String getSrc(){
		
		return href[showpackages[0]];
	}
	

	String getImgSrc(){
		
		String lang = Locale.getDefault().getLanguage();
		
		String bann_src = "dontshowabanner";

		if(showpackages != null){

			shuffleArray(showpackages);

			//---------- 	50% findthedifference200lvls
			Random rand = new Random();
			if(!isPackageExisted(href[10])){
				if(rand.nextInt(2)==0){
					showpackages[0] = 10;
				}
			}
			//----------

			n = indexes[showpackages[0]];
			
			if(n == 1 || n == 2 || n == 3 || n == 5 || n == 7 || n == 9 || n == 10 || n == 11 || n == 13 || n == 15 || n == 16){
					

					if(lang.contentEquals("en")){
					    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "en";
					}else{
						if(lang.contentEquals("ja")){
						    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "ja";
						}else{
							if(lang.contentEquals("es")){
							    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "es";
							}else{
								if(lang.contentEquals("nl")){
								    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "nl";
								}else{
									if(lang.contentEquals("fr")){
									    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "fr";
									}else{
										if(lang.contentEquals("de")){
										    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "de";
										}else{
											if(lang.contentEquals("ru")){
											    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "ru";
											}else{
												
											    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "en";
											    
												if(n == 1 || n == 11 || n == 13 || n == 15 || n == 16){
													if(lang.contentEquals("cs")){
													    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "cs";
													}else{
														if(lang.contentEquals("pl")){
														    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "pl";
														}else{
															if(lang.contentEquals("it")){
															    bann_src = "raw/banner_" + String.valueOf(n) + "_" + "it";
															}
														}
													}
												}
												
											}
										}
									}
								}
							}
						}
					}
				
				}
        
		}

		//Log.e("FFFFFFFF", bann_src);
		
		return bann_src;
		
	}
	
	  // Implementing Fisher–Yates shuffle
	  static void shuffleArray(int[] ar)
	  {
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }
	
	 public boolean isPackageExisted(String targetPackage){
		   PackageManager pm = cont.getPackageManager();
		   try {
		    pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
		       } catch (NameNotFoundException e) {
		    return false;
		    }  
		    return true;
	 }
}
