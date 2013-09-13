package com.wande.todo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements
		ConnectionCallbacks, OnConnectionFailedListener {
	
	
	public static String KEY_TODO_S = "to_dos"; 
	public static String KEY_ID = "user_id"; // id of the task
	public static String KEY_NAME = "title"; // name of the task
	public static String KEY_DESC = "text"; // description of the task
	public static String KEY_TODO_ID = "todo_id"; 
	
	
	JSONArray todo_s = null;
	//String url = "http://devfest-todo-app.appspot.com/getToDosForUser?user_id=110696408351640467234";//"http://devfest-todo-app.appspot.com/getToDos";
	
	public static ArrayList<HashMap<String, String>> tasks = new ArrayList<HashMap<String,String>>();
	
	
	public static TaskAdapter taskAdapter;

	private ProgressDialog mConnectionProgressDialog;
	private PlusClient mPlusClient;
	//private ConnectionResult mConnectionResult;

	

	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	private static final String TAG = "SignIn";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPlusClient = new PlusClient.Builder(this, this, this)
				.setVisibleActivities("AddActivity").build();
		// Progress bar to be displayed if the connection failure is not
		// resolved.
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");
		
		//new LoadPlaces().execute(url);
		
	        }
	
	
	class LoadPlaces extends AsyncTask<String, String, String> {
		
		private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
		

	    @Override
	    protected void onPostExecute(String result) {            
	        super.onPostExecute(result);
	        taskAdapter = new TaskAdapter(MainActivity.this, tasks);
 	        setListAdapter(taskAdapter);
	        dialog.dismiss();
	    }

	    @Override
	    protected void onPreExecute() {        
	        super.onPreExecute();
	        dialog.setMessage("Downloading tasks...");
	        dialog.show();            
	    }

		protected String doInBackground(String... params) {
//			 List<Task> result = new ArrayList<Task>();
			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.getJSONFromUrl(params[0]);
			try {
				// Getting Array of Tasks
				todo_s = json.getJSONArray(KEY_TODO_S);
				
				// looping through All Contacts
				for(int i = 0; i < todo_s.length(); i++){
					JSONObject c = todo_s.getJSONObject(i);
					
					// Storing each json item in variable
					String name = c.getString(KEY_NAME);
					String desc = c.getString(KEY_DESC);
					//String id = c.getString(KEY_ID);
					//String todo_id = c.getString(KEY_TODO_ID);
					
					HashMap<String, String> map = new HashMap<String, String>();
					//map.put("id", id);
					map.put(KEY_NAME, name);
		            map.put(KEY_DESC, desc);
		       //   map.put(KEY_TODO_ID, todo_id);
		            tasks.add(map);
				
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		

		switch (item.getItemId()) {
		case R.id.add:
			Intent i = new Intent(this, AddActivity.class);
			startActivity(i);
			return true;

		case R.id.sign_in:
			if (mPlusClient.isConnected() != true) {
				mConnectionProgressDialog.show();
				mPlusClient.connect();
			} else {
				Toast.makeText(this,"you are connected.", Toast.LENGTH_LONG).show();
				item.setTitle(mPlusClient.getCurrentPerson().getDisplayName());
			}
			
			
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		if (mConnectionProgressDialog.isShowing()) {
			// The user clicked the sign-in button already. Start to resolve
			// connection errors. Wait until onConnected() to dismiss the
			// connection dialog.
			if (result.hasResolution()) {
				try {
					result.startResolutionForResult(this,
							REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					mPlusClient.connect();
				}
			}
		}
		// Save the result and resolve the connection failure upon a user click.
		//mConnectionResult = result;

	}
	
	String url = "http://devfest-todo-app.appspot.com/getToDosForUser?user_id=";//110696408351640467234";
	Menu menu;
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		mConnectionProgressDialog.dismiss();
		String id = mPlusClient.getCurrentPerson().getId();
		new LoadPlaces().execute(url+id);
	/*	
		MenuItem item = menu.findItem(R.id.sign_in);
		item.setTitle(mPlusClient.getCurrentPerson().getDisplayName());
		*/
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		Log.d(TAG, "disconnected");
	}

	@Override
	protected void onStart() {
		super.onStart();
		mPlusClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mPlusClient.disconnect();
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == RESULT_OK) {
			//mConnectionResult = null;
			mPlusClient.connect();
		}
		
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent;
		intent = new Intent(this, TaskViewActivity.class); 
		intent.putExtra(MainActivity.KEY_NAME, tasks.get(position).get(KEY_NAME));
		intent.putExtra(MainActivity.KEY_DESC, tasks.get(position).get(KEY_DESC));
		startActivity(intent); 
	}


}
