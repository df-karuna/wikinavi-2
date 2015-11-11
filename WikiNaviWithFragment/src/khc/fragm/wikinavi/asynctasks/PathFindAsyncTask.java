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

public class PathFindAsyncTask extends AsyncTask<String, Void, List<Vertex>> {
	private WeakReference<ListView> mListView = null;
	private WeakReference<Context> mContext = null;
	private Vertex mStart, mEnd;
	final String TAG = "wikinavi";
	
	public PathFindAsyncTask(Context context, Vertex start, Vertex end){
		mContext = new WeakReference<Context>(context);
		mStart = start;
		mEnd = end;
	}
	
	@Override
	protected List<Vertex> doInBackground(String... params) {
		// TODO Auto-generated method stub
		HttpJsonParser parser = new HttpJsonParser("http://106.249.235.42:8123");
		StringBuilder uri = new StringBuilder();
		
		uri.append("/api/maps");
		
		if(params != null){
			uri.append("/")
				.append(params[0]).append("/vertexes/end/").append(mEnd.getVertexId()).append("/start");
			
			if(mStart.getVertexId()!=0){
				uri.append("/").append(mStart.getVertexId());
			}
			else{
				uri.append("?x=").append(mStart.getX()).append("&y=").append(mEnd.getY());
			}
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
			for(Vertex v: vertexes){
				Log.d("vertexes test", v.getName());
			}
		}
	}
	
}