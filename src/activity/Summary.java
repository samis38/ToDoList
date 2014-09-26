package activity;

import java.util.ArrayList;
import java.util.Iterator;

import model.DiskList;
import model.Event;

import com.example.assignment01.R;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Summary extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		TextView tc=(TextView)findViewById(R.id.total_checked);
		TextView tu=(TextView)findViewById(R.id.total_uncheck);
		TextView ac=(TextView)findViewById(R.id.arch_checked);
		TextView au=(TextView)findViewById(R.id.arch_uncheck);
		
		SharedPreferences dataBase=getSharedPreferences("DB",0);
	    DiskList tmpList=(new Gson()).fromJson(dataBase.getString("TODO_LIST",(new Gson()).toJson(new DiskList(new ArrayList<Event>()))),DiskList.class);
	    ArrayList<Event> todo=tmpList.getList();
	    tmpList=(new Gson()).fromJson(dataBase.getString("ARCH_LIST",(new Gson()).toJson(new DiskList(new ArrayList<Event>()))),DiskList.class);
	    ArrayList<Event> arch=tmpList.getList();
	    
	    
	    //calculate the summary
	    Iterator<Event> i=todo.iterator();
	    int todoUncheck=0;int todoCheck=0;
		while (i.hasNext()){
			Event e=i.next();
			if(e.getStatus()){
				todoCheck++;
			}
			else{
				todoUncheck++;
			}
		}
		Iterator<Event> i2=arch.iterator();
		
	    int archUncheck=0;int archCheck=0;
		while (i2.hasNext()){
			Event e2=i2.next();
			if(e2.getStatus()){
				archCheck++;
			}
			else{
				archUncheck++;
			}
		}
		
		//print the summary on the summary layout
		tu.setText("Total number of unchecked event: "+(todoUncheck));
		tc.setText("Total number of checked event: "+(todoCheck));
		ac.setText("Total number of unchecked archived event: "+(archCheck));
		au.setText("Total number of unchecked archived event: "+(archUncheck));
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
