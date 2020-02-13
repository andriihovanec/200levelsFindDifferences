package com.olbigames.levels200finddifferences;
/*
package com.kryptongames.findthedifference150levels;

import java.util.Random;

public class TracesHint {

	public static final int COL = 200;
	
    int x;
    int y;
    float X[] = new float[COL];
    float Y[] = new float[COL];
    float speedX[] = new float[COL];
    float speedY[] = new float[COL];
    int difId;
    
    float timeLeft;
    
    float vector[] = new float[COL*2];

    float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    
    float uvs[] = {
    		0.0f, 0.0f,
    	    0.0f, 1.0f,
    	    1.0f, 1.0f,
    	    1.0f, 0.0f };

    
    public TracesHint(int trId, int xx, int yy) {

	    timeLeft = 3000.0f;
	    
	    x = xx;
	    y = yy;
	    difId = trId;

		Random r = new Random();
	    int i = 0;
	    do {
	    	
	        float scsc = r.nextFloat()*360.0f;
	        
	        float sc = r.nextFloat()*40.0f;
	        
	        speedX[i] = (float) (Math.sin(scsc) * (sc));
	        speedY[i] = (float) (Math.sin(scsc) * (10.0f + sc) - 35.0f);
	        
	        vector[2*i] = speedX[i] + x;
	        vector[2*i+1] = speedY[i] + y;
	        
	        X[i] = x ;
	        Y[i] = y ;
	        i++;
	    } while (i < COL);
	    
    }

    public int getId() {
        return difId;
    }
    
    public int col() {
        return COL;
    }
    
    public float gettime() {
        return 3000.0f - timeLeft;
    }
    

    public void  getVector2(float time) {
    
    int i = 0;
    do {
        
        speedY[i] += 30.0f * time;
        
        if( speedY[i] > 0.0f){
            speedY[i] -= 10.0f * time;
        }else{
            speedY[i] += 10.0f * time;
        }
        
        vector[2*i] = X[i] = X[i] + speedX[i] * time;
        vector[2*i+1] = Y[i] = Y[i] + speedY[i] * time;
        
        i++;
    } while (i < COL);
    
    
}

    public float[] getvector() {
        return vector;
    }
    
    public float[] getuvs() {
        return uvs;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }



    public boolean timeAdd(float time) {
	    	
	    timeLeft -= time;
	    if(timeLeft < 0.0f){
	        return true;
	    }

	    this.getVector2(time/1000.0f);
	    
	    return false;
	}

}
*/