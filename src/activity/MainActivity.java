/*
To Do List: events management

Copyright (C) 2014 Xiaocong Zhou xiaocong@ualberta.ca

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.

*/

package activity;


import java.util.ArrayList;

import model.DiskList;
import model.Event;
import model.EventList;
import com.example.assignment01.R;

import com.google.gson.Gson;

import adapter.Adapter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
	//Views
	private ListView todoList=null;
	private EditText inputText=null;
	private Button add=null;
	private Button remove=null;
	private Button archive=null;
	//Data Storage
	private EventList eventList=null;
	private Adapter adapter=null;
	//Disk for resuming data
	private SharedPreferences dataBase=null;
	//gson
	private Gson gson=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //initView
        todoList=(ListView)findViewById(R.id.todo_list);
        inputText=(EditText)findViewById(R.id.input_text);
        add=(Button)findViewById(R.id.add_button);
        remove=(Button)findViewById(R.id.remove_button);
        archive=(Button)findViewById(R.id.archive_button);
        gson=new Gson();
    }
    
    
    

    
    /*This is for resuming data and reset the action listeners in the to do list and the archived event list
     *when user shut down the app, or when a suddenly interruption happens.
     * */
    @Override
	protected void onResume() {
		super.onResume();
		//get data from disk to resume the information in the layout.
	    dataBase=getSharedPreferences("DB",0);
	    DiskList tmpList=gson.fromJson(dataBase.getString("TODO_LIST",gson.toJson(new DiskList(new ArrayList<Event>()))),DiskList.class);
	    eventList=new EventList(tmpList.getList());
		//initialize adapter
	    adapter=new Adapter(this,R.layout.single_layout,eventList.getCurrentList());
		todoList.setAdapter(adapter);
		todoList.setOnItemClickListener(new ClickListener());
		eventList.setAdpater(adapter);
		//set listener again
	    add.setOnClickListener(new AddListener());
		remove.setOnClickListener(new RemoveListener());
		archive.setOnClickListener(new ArchiveClick());
		
		Button gotoSummary=(Button)findViewById(R.id.summary);
		gotoSummary.setOnClickListener(new SummaryClick());
		
		
	}
    
    /*This template class will be called when a user click the "Archive selected events".
     *Then the layout will switch to a new layout of the archived event list.
     * */
    class SummaryClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, Summary.class);
	    	startActivity(intent);
			
		}
    	
    }
    
    /*This template class will be called when a user click the "Add an event"
     *to get the text from the user input and add it to event list.
     * */
    class AddListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String input=inputText.getText().toString();
			Event newEvent=new Event(input,false);
			eventList.addEvent(newEvent);
			
			//Update Disk Data
			DiskList tmpList=new DiskList(eventList.getCurrentArrayList());
			dataBase.edit().putString("TODO_LIST",gson.toJson(tmpList)).commit();
		}
    	
    }
    
    /*This template class will be called when a user click the "Remove selected events"
     *to remove it from event list.
     * */
    
    class RemoveListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			eventList.removeEvent();
			
			//Update Disk Data
			
			DiskList tmpList=new DiskList(eventList.getCurrentArrayList());
			dataBase.edit().putString("TODO_LIST",gson.toJson(tmpList)).commit();
		}
    	
    }
    
    /*This template class will be called when a user click the "Archive Selected Events"
     *to add selected items in the current to do list to the archived event list.
     * */
    class ArchiveClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			//get the old archived event list and append new events.
			DiskList tmpList=gson.fromJson(dataBase.getString("ARCH_LIST",gson.toJson(new DiskList(new ArrayList<Event>()))),DiskList.class);
			ArrayList<Event> tmp=tmpList.getList();
			tmp.addAll(eventList.getTransferItems());
			tmpList=new DiskList(tmp);
			dataBase.edit().putString("ARCH_LIST",gson.toJson(tmpList)).commit();
			Toast.makeText(getApplicationContext(), "selected items has been added to the archive",Toast.LENGTH_SHORT).show();
			
			//Update Disk Data
			DiskList tmpList2=new DiskList(eventList.getCurrentArrayList());
			dataBase.edit().putString("TODO_LIST",gson.toJson(tmpList2)).commit();
			
			gotoArchive();
		}
    	
    }

    /*This template class will be called when a  one of the items is selected
     * to catch the selection signal, and change the 'selected' boolean in Event class
     * */
    class ClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?>arg0,View arg1,int pos,long arg3){
			Event event=(Event)arg0.getItemAtPosition(pos);
			eventList.clickAnEvent(event);
			
			//Update Disk Data
			DiskList tmpList=new DiskList(eventList.getCurrentArrayList());
			dataBase.edit().putString("TODO_LIST",gson.toJson(tmpList)).commit();
		}
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /*This template class will be called when a user click the "Archive Selected Events"
     *to jump to a new layout.
     * */
    public void gotoArchive(){
    	Toast.makeText(this, "View archived events", Toast.LENGTH_SHORT).show();
    	Intent intent = new Intent(MainActivity.this, ArchivedEventActivity.class);
    	startActivity(intent);
    }

}
