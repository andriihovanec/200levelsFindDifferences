/*package com.olbigames.finddifferences200levels;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class SplashFragment extends Fragment {
	
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");//publish_actions
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;

	private UiLifecycleHelper uiHelper;
	
	private Session.StatusCallback callback = 
		    new Session.StatusCallback() {
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		        onSessionStateChange(session, state, exception);
		    }
		};

	private Button shareButton;

	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.main, container, false);

		//Log.e("FRAGMENT","onCreateView");
		
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setFragment(this);
	    
	    shareButton = (Button) view.findViewById(R.id.shareButton);
	    

		shareButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

				if (FacebookDialog.canPresentShareDialog(getActivity().getApplicationContext(), 
						FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
					// Publish the post using the Share Dialog
					shareDialog();
				} else {
					// Fallback. For example, publish the post using the Feed Dialog
					publishFeedDialog();
				}
		    	
		    }
		});
	    
		ImageButton backButton = (ImageButton) view.findViewById(R.id.back);


		backButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	removeFR();
		    }
		});

		if (savedInstanceState != null) {
		    pendingPublishReauthorization = 
		        savedInstanceState.getBoolean(PENDING_PUBLISH_KEY, false);
		}
		
	    return view;
	}
	

	public void removeFR(){
		getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
	}
	
	public void shareDialog(){
		
	    final String app_name = getResources().getString(R.string.app_name);

		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
		.setApplicationName(app_name)
		.setName(app_name)
		.setCaption("by Krypton Games")
		.setDescription("Android puzzle game")
		.setLink(AAAsettings.googlePlayUrl)
		.setPicture(AAAsettings.logoUrl)
		.build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
		
	}
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
		
		//Log.e("FRAGMENT","onCreate");
	}
	
	@Override
	public void onResume() {
	super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null &&
		(session.isOpened() || session.isClosed()) ) {
		onSessionStateChange(session, session.getState(), null);
		}
		//Log.e("FRAGMENT","onResume");
		uiHelper.onResume();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		
		//shareDialog();
		if (FacebookDialog.canPresentShareDialog(getActivity().getApplicationContext(), 
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			shareDialog();
		} else {
			// Fallback. For example, publish the post using the Feed Dialog
			publishFeedDialog();
		}
    	
	}
	
	@Override
	public void onPause() {
	super.onPause();
		//Log.e("FRAGMENT","onPause");
		uiHelper.onPause();
	}
	
	@Override
	public void onDestroy() {
	super.onDestroy();
		//Log.e("FRAGMENT","onDestroy");
		uiHelper.onDestroy();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	    outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	public void publishFeedDialog() {
	    Bundle params = new Bundle();

	    final String app_name = getResources().getString(R.string.app_name);
	    
	    params.putString("name", app_name);
	    params.putString("caption", "by Krypton Games");
	    params.putString("description", "Android puzzle game");
	    params.putString("link", AAAsettings.googlePlayUrl);
	    params.putString("picture", AAAsettings.logoUrl);


	    WebDialog feedDialog = (
	            new WebDialog.FeedDialogBuilder(getActivity(),
	                Session.getActiveSession(),
	                params))
	            .setOnCompleteListener(new OnCompleteListener() {
	            	
	                @Override
	                public void onComplete(Bundle values,
	                    FacebookException error) {
	                    if (error == null) {
	                        // When the story is posted, echo the success
	                        // and the post Id.
	                        final String postId = values.getString("post_id");
	                        if (postId != null) {
	                            Toast.makeText(getActivity(),
	                                "Posted story, id: "+postId,
	                                Toast.LENGTH_SHORT).show();
	                        } else {
	                            // User clicked the Cancel button
	                            Toast.makeText(getActivity().getApplicationContext(), 
	                                "Publish cancelled", 
	                                Toast.LENGTH_SHORT).show();
	                        }
	                    } else if (error instanceof FacebookOperationCanceledException) {
	                        // User clicked the "x" button
	                        Toast.makeText(getActivity().getApplicationContext(), 
	                            "Publish cancelled", 
	                            Toast.LENGTH_SHORT).show();
	                    } else {
	                        // Generic, ex: network error
	                        Toast.makeText(getActivity().getApplicationContext(), 
	                            "Error posting story", 
	                            Toast.LENGTH_SHORT).show();
	                    }
	                }

	            })
	            .build();
	    feedDialog.show();
	}

	public void publishStory() {

	    final String app_name = getResources().getString(R.string.app_name);
	    final String link = AAAsettings.googlePlayUrl;
		
	    Session session = Session.getActiveSession();

	    if (session != null){

	        // Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session
	                    .NewPermissionsRequest(this, PERMISSIONS);
	            session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }

	        Bundle postParams = new Bundle();
	        postParams.putString("name", app_name);
	        postParams.putString("caption", "by Krypton Games");
	        postParams.putString("description", "Android puzzle game");
	        postParams.putString("link", link);
	        postParams.putString("picture", AAAsettings.logoUrl);


	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	            	
	                JSONObject graphResponse = response
	                                           .getGraphObject()
	                                           .getInnerJSONObject();
	                
	                String postId = null;
	                try {
	                    postId = graphResponse.getString("id");
	                } catch (JSONException e) {
	                    //Log.i("FB","JSON error "+ e.getMessage());
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(getActivity()
	                         .getApplicationContext(),
	                         error.getErrorMessage(),
	                         Toast.LENGTH_SHORT).show();
	                    } else {
	                    	
	                        Toast.makeText(getActivity()
	                             .getApplicationContext(), 
	                             postId,
	                             Toast.LENGTH_LONG).show();
	                        
	                }
	                
	                
	            }
	        };

	        Request request = new Request(session, "me/feed", postParams, 
	                              HttpMethod.POST, callback);

	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	    }

	}
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
	

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        //Log.i("FB", "Logged in...");
	        shareButton.setVisibility(View.VISIBLE);
	        if (pendingPublishReauthorization && 
	                state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
	            pendingPublishReauthorization = false;
	            publishStory();
	        }

		    //this.startMenu();
		    
	    } else if (state.isClosed()) {
	        //Log.i("FB", "Logged out...");
	        shareButton.setVisibility(View.INVISIBLE);
	        
		    //this.startMenu();
	    }
	}
	
	
}
*/