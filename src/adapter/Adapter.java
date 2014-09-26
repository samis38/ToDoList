package adapter;

import java.util.List;

import model.Event;

import com.example.assignment01.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/*This is a customer adapter for initialing the format and color of each row*/
public class Adapter extends ArrayAdapter<Event>{
	
	public Adapter(Context context,int textViewResourceId,List<Event> objects) {
		super(context,textViewResourceId,objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			convertView = inflater.inflate(R.layout.single_layout,null);
		}
		Event event=this.getItem(position);
		if(event!=null){
			TextView eventText=(TextView)convertView.findViewById(R.id.event_text);
			eventText.setText(event.getEvent());
			
			if(event.getStatus()){
				convertView.setBackgroundColor(Color.CYAN);
			}
			else{
				convertView.setBackgroundColor(Color.WHITE);
			}
			
		}
		return convertView;
	}
	
}
