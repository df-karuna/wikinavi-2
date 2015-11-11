package khc.fragm.wikinavi.components;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import khc.fragm.wikinavi.R;
import khc.fragm.wikinavi.dataSet.Map;
import khc.fragm.wikinavi.dataSet.Vertex;

public class VertexListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Vertex> data;
	private int layout;

	public VertexListAdapter(Context context, int layout, List<Vertex> vertexes) {
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = vertexes;
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

		Vertex listviewitem = data.get(position);
		
		
//		ImageView icon = (ImageView) convertView.findViewById(R.id.imageview);
//		icon.setImageResource(listviewitem.getIcon());
		TextView mapName = (TextView) convertView.findViewById(R.id.map_name);
		mapName.setText(listviewitem.getName());

		return convertView;
	}
}