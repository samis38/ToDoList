package model;

import java.util.Date;

public class Event {
	private String content=null;
	private String id=null;
	Boolean selected=false;
	
	public Event(String content, Boolean selected){
		this.content = content;
		this.selected = selected;
		this.id = (content+(new Date()).getTime()).replaceAll("\\s","");
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getEvent(){
		return this.content;
	}
	
	public boolean getStatus(){
		return this.selected;
	}
	
	public void select(){
		this.selected=true;
	}
	
	public void unSelect(){
		this.selected=false;
	}

}