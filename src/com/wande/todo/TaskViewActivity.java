package com.wande.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskViewActivity extends Activity {
	TextView name;
	TextView desc;
	
	String taskName;
	String taskDesc;
	
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_view);
		
		name = (TextView)findViewById(R.id.task_name_view);
		desc = (TextView)findViewById(R.id.task_desc_view);
		
		
		intent = this.getIntent();
		//initialise
		taskName = intent.getExtras().getString(TaskList.KEY_NAME);
		taskDesc = intent.getExtras().getString(TaskList.KEY_DESC);
		
		name.setText(taskName);
		desc.setText(taskDesc);
	}
	

}
