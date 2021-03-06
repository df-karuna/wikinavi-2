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
import khc.fragm.wikinavi.components.VertexListAdapter;
import khc.fragm.wikinavi.dataSet.Map;
import khc.fragm.wikinavi.dataSet.Vertex;

public class VertexInfoAsyncTask extends AsyncTask<String, Void, List<Vertex>> {
	private WeakReference<ListView> mListView = null;
	private WeakReference<Context> mContext = null;

	final String TAG = "wikinavi";
	
	public VertexInfoAsyncTask(Context context, ListView listView){
		mContext = new WeakReference<Context>(context);
		mListView = new WeakReference<ListView>(listView);
	}
	
	@Override
	protected List<Vertex> doInBackground(String... params) {
		// TODO Auto-generated method stub
		HttpJsonParser parser = new HttpJsonParser("http://106.249.235.42:8123");
		StringBuilder uri = new StringBuilder();
		
		uri.append("/api/maps");
		
		if(params != null){
			uri.append("/")
				.append(params[0]).append("/vertexes").append("?name=").append(params[1]);
		}
		
		try {
			Type vertexCollections = new TypeToken<List<Vertex>>(){}.getType();
			
			Log.v("wikinavi", "begin");
			@SuppressWarnings("unchecked")
			
			List<Vertex> vertexes = (ArrayList<Vertex>)parser.parse(uri.toString(), vertexCollections);
			
			return vertexes;
			
		} catch(Exception ex){
			Log.d("wikinavi", ex.getMessage());
		} finally {
			Log.v("wikinavi", "end");
		}
		return null;
	}
	
	@Override
	protected  void onPostExecute(List<Vertex> vertexes){

		if(vertexes != null){

//			ArrayList<Map> data = new ArrayList();
//			for(Map m : maps){
//				data.add(new MapListViewItem(m));
//			}
				
			VertexListAdapter vla = new VertexListAdapter(mContext.get(), R.layout.map_list_item, vertexes);
			mListView.get().setAdapter(vla);
		}
	}
	
}
