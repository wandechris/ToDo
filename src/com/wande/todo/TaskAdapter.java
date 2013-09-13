package com.wande.todo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskAdapter extends BaseAdapter {

	private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null; 
    
    public TaskAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
        
       // Task task;
        if(convertView==null)
            vi = inflater.inflate(R.layout.task_item, null);

        TextView name = (TextView)vi.findViewById(R.id.task_item_name); // title
        TextView desc = (TextView)vi.findViewById(R.id.task_item_desc); // title
        
        
       // task = data.get(position);
        name.setText(data.get(position).get(TaskList.KEY_NAME));
        desc.setText(data.get(position).get(TaskList.KEY_DESC));
        
        // Setting all values in listview
      //  name.setText(task.getName());
        return vi;
    }

}
