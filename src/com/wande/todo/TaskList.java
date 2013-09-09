package com.wande.todo;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class TaskList extends ListFragment {
	
	TaskAdapter taskAdapter;
	ArrayList<Task> tasks = new ArrayList<Task>();
	Task task;
	Task task2;
	
	public TaskList(){
		task = new Task("Sleep", false);
		task2 = new Task("eat", true);
		tasks.add(task);
		tasks.add(task2);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		taskAdapter = new TaskAdapter(getActivity(), tasks);
		setListAdapter(taskAdapter);
	}
	
	
	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
		/**
		 * Toast message will be shown when you click any list element
		 */
		Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
	}

}
