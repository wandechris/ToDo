package com.wande.todo;

public class Task {
	private String name;
	private String desc;
	private boolean complete;
	
	public Task(String taskName, Boolean done){
		name = taskName;
		complete = done;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public void toggleComplete() {
		complete = !isComplete();
	}
}
