package khc.fragm.wikinavi.asynctasks;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import khc.fragm.wikinavi.R;
import khc.fragm.wikinavi.asynctasks.common.HttpJsonParser;
import khc.fragm.wikinavi.components.MapListAdapter;
import khc.fragm.wikinavi.components.MapListViewItem;
import khc.fragm.wikinavi.dataSet.Map;

public class MapInfoAsyncTask extends AsyncTask<String, Void, List<Map>> {
	private WeakReference<ListView> mListView = null;
	private WeakReference<Context> mContext = null;

	final String TAG = "wikinavi";
	
	public MapInfoAsyncTask(Context context, ListView listView){
		mContext = new WeakReference<Context>(context);
		mListView = new WeakReference<ListView>(listView);
	}
	
	@Override
	protected List<Map> doInBackground(String... params) {
		// TODO Auto-generated method stub
		HttpJsonParser parser = new HttpJsonParser("http://106.249.235.42:8123");
		StringBuilder uri = new StringBuilder();
		
		uri.append("/api/maps");
		
		if(params != null){
			uri.append("?title=")
				.append(params[0]);
		}
		
		try {
			Type mapCollections = new TypeToken<List<Map>>(){}.getType();
			
			Log.v("wikinavi", "begin");
			@SuppressWarnings("unchecked")
			
			List<Map> maps = (ArrayList<Map>)parser.parse(uri.toString(), mapCollections);
			
			return maps;
			
		} catch(Exception ex){
			Log.d("wikinavi", ex.getMessage());
		} finally {
			Log.v("wikinavi", "end");
		}
		return null;
	}
	
	@Override
	protected  void onPostExecute(List<Map> maps){

		if(maps != null){

//			ArrayList<Map> data = new ArrayList();
//			for(Map m : maps){
//				data.add(new MapListViewItem(m));
//			}
				
			MapListAdapter mla = new MapListAdapter(mContext.get(), R.layout.map_list_item, maps);
			mListView.get().setAdapter(mla);
		}
	}
	
}
