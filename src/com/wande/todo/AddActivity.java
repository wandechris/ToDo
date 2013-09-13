package com.wande.todo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity implements
ConnectionCallbacks, OnConnectionFailedListener{
	
	JSONObject message = new JSONObject();
	String taskName;
	String taskDesc;
	String id;
	String url = "http://devfest-todo-app.appspot.com/saveToDo";
	private PlusClient mPlusClient;
	String responseString;
	EditText name;
	EditText desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_task);
		
		  name = (EditText)findViewById(R.id.txt_task_name);
		  desc = (EditText)findViewById(R.id.txt_task_description);
		
		

		mPlusClient = new PlusClient.Builder(this, this, this)
         .setVisibleActivities("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
         .build();
		 
		 if (mPlusClient.isConnected()) {
             id = mPlusClient.getCurrentPerson().getId();

             if (!mPlusClient.isConnecting()) {
                 mPlusClient.connect();
             }
         } else {
        	 	mPlusClient.connect();
         }
		
		/*
		JSONArray markArray = new JSONArray(); 
		JSONObject markObj = new JSONObject();
		markObj.put("mark1", "50");
		markObj.put("mark2", "70");
		markArray.put(markObj);

		mParams.put("Marks", markArray);
		*/
		
		Button add = (Button)findViewById(R.id.btn_add);
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new SendData().execute(url);
				finish();
			}
		});
	}
	
	
	
	class SendData extends AsyncTask<String, Void, String> {

		JSONObject res;
		HashMap<String, String> map = new HashMap<String, String>();
		
	    protected String doInBackground(String... urls) {
	    	
	    	taskName = name.getText().toString();
			taskDesc = desc.getText().toString();
			
			HttpPost httppost = new HttpPost(urls[0]);
			 DefaultHttpClient httpClient = new DefaultHttpClient();
			
			StringBuilder sb =new StringBuilder();
			
			 httppost.setHeader("Content-type", "application/json");
		    try {
		    	message.put("title", taskName);
				message.put("text", taskDesc);
				message.put("user_id", id);
				
				StringEntity strentity = new StringEntity(message.toString());
				strentity.setContentType("application/json");
                strentity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
                httppost.setHeader("Accept", "application/json");
                httppost.setEntity(strentity);
                HttpResponse response = httpClient.execute(httppost);
                
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in,"iso-8859-1"),8);
                //StringBuilder sb2 = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                     sb.append(line + "\n");
                }
                in.close();
                responseString = sb.toString();
                res = new JSONObject(responseString);
			    
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HttpResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    }

	    protected void onPostExecute(String feed) {
           // for (int i=0; i < res.length(); i++) {
             //   result.add(convertContact(res.getJSONObject(i)));
            	try {
					//map.put("user_id", res.getString("user_id"));
					map.put("title", res.getString("title"));
	            	map.put("text", res.getString("text"));
	            	//map.put("to_do_id", res.getString("to_do_id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	MainActivity.tasks.add(map);
            	MainActivity.taskAdapter.notifyDataSetChanged();
           // }
            return;
	    }
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_menu, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {
        case R.id.cancel:
	      finish();
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
		id = mPlusClient.getCurrentPerson().getId();
		
	}


	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}
