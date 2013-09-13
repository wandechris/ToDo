package com.wande.todo;

public class Task {
	private String name;
	private String desc;
	
	public Task(String taskName,String descc){
		name = taskName;
		desc = descc;
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
	
}
