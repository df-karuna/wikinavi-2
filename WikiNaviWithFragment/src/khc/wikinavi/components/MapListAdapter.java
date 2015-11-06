package khc.wikinavi.components;

import java.util.ArrayList;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import khc.wikinavi.dataSet.Map;

public class MapListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<Map> data;
	private int layout;

	public MapListAdapter(Context context, int layout, ArrayList<Map> data) {
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = data;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public String getItem(int position) {
		return data.get(position).getName();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(layout, parent, false);
		}

		Map listviewitem = data.get(position);
		
//		ImageView icon = (ImageView) convertView.findViewById(R.id.imageview);
//		icon.setImageResource(listviewitem.getIcon());
//		TextView mapName = (TextView) convertView.findViewById(R.id.);
//		mapName.setText(listviewitem.getName());
//		TextView mapAddr = (TextView) convertView.findViewById(R.id.mapName);

		return convertView;
	}
}