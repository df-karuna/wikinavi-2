package khc.fragm.wikinavi.frags;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import khc.fragm.wikinavi.R;
import khc.fragm.wikinavi.asynctasks.MapInfoAsyncTask;

public class SelectMapFrag extends Fragment implements OnClickListener{
	private View mView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		mView = inflater.inflate(R.layout.select_map, container, false);
		
		Button bt_searchMap = (Button)mView.findViewById(R.id.bt_searchMap);
		
		bt_searchMap.setOnClickListener(this);
		runAsync(null);
		
		Log.d("wikinavi", "selectMapFrag");
		
		return mView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditText map_search = (EditText) mView.findViewById(R.id.map_search);
		String text = map_search.getText().toString();
		
		runAsync(text);
	
	}
	
	private void runAsync(String text){
		ListView listView = (ListView) mView.findViewById(R.id.mapListView);
		MapInfoAsyncTask task = new MapInfoAsyncTask(mView.getContext(), listView);
		
		task.execute(text);
		
	}

}
