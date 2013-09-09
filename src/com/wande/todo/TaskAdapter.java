package com.wande.todo;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

public class TaskAdapter extends BaseAdapter {

	private Activity activity;
    private ArrayList<Task> data;
    private static LayoutInflater inflater=null; 
    
    public TaskAdapter(Activity a, ArrayList<Task> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        
        Task task;
        if(convertView==null)
            vi = inflater.inflate(R.layout.task_item, null);

        CheckedTextView name = (CheckedTextView)vi.findViewById(R.id.task_item_name); // title
        
        
        task = data.get(position);
        
        // Setting all values in listview
        name.setText(task.getName());
        name.setChecked(task.isComplete());
        return vi;
    }

}
