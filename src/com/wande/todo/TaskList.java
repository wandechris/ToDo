package com.wande.todo;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class TaskList extends ListFragment{
	
	public static String KEY_TODO_S = "to_dos"; 
	public static String KEY_ID = "user_id"; // id of the task
	public static String KEY_NAME = "title"; // name of the task
	public static String KEY_DESC = "text"; // description of the task
	public static String KEY_TODO_ID = "todo_id"; 
	
	
	JSONArray todo_s = null;
	String url = "http://devfest-todo-app.appspot.com/getToDosForUser?user_id=110696408351640467234";//"http://devfest-todo-app.appspot.com/getToDos";
	
	public static ArrayList<HashMap<String, String>> tasks = new ArrayList<HashMap<String,String>>();
	
	
	public static TaskAdapter taskAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new LoadPlaces().execute(url);
		
		
	}
	
	
	
	class LoadPlaces extends AsyncTask<String, String, String> {
		
		private final ProgressDialog dialog = new ProgressDialog(getActivity());
		

	    @Override
	    protected void onPostExecute(String result) {            
	        super.onPostExecute(result);
	        taskAdapter = new TaskAdapter(getActivity(), tasks);
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
	public void onListItemClick(ListView list, View v, int position, long id) {
		Intent intent;
		intent = new Intent(getActivity(), TaskViewActivity.class); 
		intent.putExtra(TaskList.KEY_NAME, tasks.get(position).get(KEY_NAME));
		intent.putExtra(TaskList.KEY_DESC, tasks.get(position).get(KEY_DESC));
		startActivity(intent); 
		getActivity().finish();
	}

}
