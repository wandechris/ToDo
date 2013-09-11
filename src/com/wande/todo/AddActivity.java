package com.wande.todo;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddActivity extends Activity {
	
	JSONObject message = new JSONObject();
	String taskName;
	String taskDesc;
	String url = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_task);
		
		TextView name = (TextView)findViewById(R.id.txt_task_name);
		TextView desc = (TextView)findViewById(R.id.txt_task_description);
		
		taskName = name.getText().toString();
		taskDesc = desc.getText().toString();
		/*
		JSONArray markArray = new JSONArray(); 
		JSONObject markObj = new JSONObject();
		markObj.put("mark1", "50");
		markObj.put("mark2", "70");
		markArray.put(markObj);

		mParams.put("Marks", markArray);
		*/
		try {
			message.put("name", taskName);
			message.put("desc", taskDesc);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Button add = (Button)findViewById(R.id.btn_add);
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HttpPost httppost = new HttpPost(url);
			    try {
					httppost.setEntity(new StringEntity(message.toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
		});
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
}
