package activity;

import java.util.ArrayList;

import model.DiskList;
import model.Event;
import model.EventList;
import com.example.assignment01.R;
import com.google.gson.Gson;

import adapter.Adapter;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class ArchivedEventActivity extends Activity {
	private ListView archList=null;
	private Button remove=null;
	private Button unarchive=null;
	private EventList eventList=null;
	private Adapter adapter=null;
	private SharedPreferences dataBase=null;
	private Gson gson;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.archive_list);
		
		archList=(ListView)findViewById(R.id.arch_list);
        remove=(Button)findViewById(R.id.remove_button2);
        unarchive=(Button)findViewById(R.id.unarchive_button);
        gson=new Gson();
	}
	
	
    /*This is for resuming data and reset the action listeners in the to do list and the archived event list
     *when user shut down the app, or when a suddenly interruption happens.
     * */
	@Override
	protected void onResume() {
		super.onResume();
		//getDiskData
	    dataBase=getSharedPreferences("DB",0);
	    DiskList tmpList=gson.fromJson(dataBase.getString("ARCH_LIST",gson.toJson(new DiskList(new ArrayList<Event>()))),DiskList.class);
	    eventList=new EventList(tmpList.getList());
		//init adapter
	    adapter=new Adapter(this,R.layout.single_layout,eventList.getCurrentList());
		archList.setAdapter(adapter);
		archList.setOnItemClickListener(new ClickListener());
		eventList.setAdpater(adapter);
		//
		remove.setOnClickListener(new RemoveListener());
		unarchive.setOnClickListener(new UnArchiveClick());
	}
	
	
	
	 //listener on remove button in the layout, if the button is clicked, remove the
	 //selected events from the list
	 class RemoveListener implements OnClickListener{
	
			@Override
			public void onClick(View v) {
				eventList.removeEvent();
				
				//Update Disk Data
				
				DiskList tmpList=new DiskList(eventList.getCurrentArrayList());
				dataBase.edit().putString("ARCH_LIST",gson.toJson(tmpList)).commit();
			}
	    	
	  }
	 
	 //listener on unarchive button in the layout, if the button is clicked, move the
	 //selected events from the archived list to the to do list
	 class UnArchiveClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			DiskList tmpList=gson.fromJson(dataBase.getString("TODO_LIST",gson.toJson(new DiskList(new ArrayList<Event>()))),DiskList.class);
			ArrayList<Event> tmp=tmpList.getList();
			tmp.addAll(eventList.getTransferItems());
			tmpList=new DiskList(tmp);
			dataBase.edit().putString("TODO_LIST",gson.toJson(tmpList)).commit();
			Toast.makeText(getApplicationContext(), "selected items has been added back to the to do list",Toast.LENGTH_SHORT).show();
			
			//Update Disk Data
			
			DiskList tmpList2=new DiskList(eventList.getCurrentArrayList());
			dataBase.edit().putString("ARCH_LIST",gson.toJson(tmpList2)).commit();
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
			dataBase.edit().putString("ARCH_LIST",gson.toJson(tmpList)).commit();
		}
			
	}

}
