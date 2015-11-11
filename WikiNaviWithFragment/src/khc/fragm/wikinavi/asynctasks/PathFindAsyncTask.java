package khc.fragm.wikinavi.asynctasks;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import khc.fragm.wikinavi.R;
import khc.fragm.wikinavi.asynctasks.common.HttpJsonParser;
import khc.fragm.wikinavi.components.MapListAdapter;
import khc.fragm.wikinavi.components.MapListViewItem;
import khc.fragm.wikinavi.components.VertexListAdapter;
import khc.fragm.wikinavi.dataSet.Map;
import khc.fragm.wikinavi.dataSet.Vertex;

public class PathFindAsyncTask extends AsyncTask<String, Void, List<Vertex>> {
	private WeakReference<Context> mContext = null;
	private WeakReference<ImageView> mMapView = null;
	private Vertex mStart, mEnd;
	private Bitmap mMapBitmap= null;
	final String TAG = "wikinavi";
	
	public PathFindAsyncTask(Context context, ImageView mapView, Vertex start, Vertex end){
		mContext = new WeakReference<Context>(context);
		mMapView = new WeakReference<ImageView>(mapView);
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
//			for(Vertex v: vertexes){
//				Log.d("vertexes test", v.getName());
//			}
			
			ImageView mapView = mMapView.get();
			while(((BitmapDrawable)mMapView.get().getDrawable()).getBitmap() == null);
			
			
			Bitmap tempBitmap = Bitmap.createBitmap(((BitmapDrawable)mMapView.get().getDrawable()).getBitmap().copy(Config.ARGB_8888, true));
			Canvas tempCanvas = new Canvas(tempBitmap);
			
			int scaleWidth = tempBitmap.getWidth();
			int scaleHeight = tempBitmap.getHeight();

			float ratioW = scaleWidth / 52
					, ratioH = scaleHeight / 61;
			
			//Draw the image bitmap into the cavas
			tempCanvas.drawBitmap(tempBitmap, 0, 0, null);

			//Draw everything else you want into the canvas, in this example a rectangle with rounded edges
			float [] pts = new float[vertexes.size()*4];
	     	
	     	int index = 0;
	     	Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

			
	     	p.setColor(0xffff6600);
	     	p.setStrokeWidth(8);
	     	
	     	for(int i = 0; i < vertexes.size()-1 ; i++){
	     		pts[index++] = vertexes.get(i).getX()*ratioW;
	     		pts[index++] = vertexes.get(i).getY()*ratioH;
	     		pts[index++] = vertexes.get(i+1).getX()*ratioW;
	     		pts[index++] = vertexes.get(i+1).getY()*ratioH;
	     	}
//	     	for(Vertex v : vertexes){
//	     		pts[index++] = v.getX();
//	     		pts[index++] = v.getY();
//	     	}
			tempCanvas.drawLines(pts, p);

			p.setColor(0xff00ff66);
			tempCanvas.drawCircle(pts[0], pts[1], 20, p);
			
			p.setColor(0xffff3300);
			tempCanvas.drawCircle(pts[(index-2)], pts[(index-1)], 20, p);
			
//			p.setColor(Color.MAGENTA);
//			p.setTextSize(100);
//			tempCanvas.drawText("Start", pts[0]*ratioW, pts[1]*ratioH, p);//drawCircle(pts[0]*ratioW, pts[1]*ratioH, 15, p);
//			p.setColor(Color.CYAN);
//			p.setTextSize(70);
//			tempCanvas.drawText("목적지", pts[pts.length-2]*ratioW, pts[pts.length-1]*ratioH, p);//drawCircle(pts[0]*ratioW, pts[1]*ratioH, 15, p);

			//Attach the canvas to the ImageView
			mapView.setImageDrawable(new BitmapDrawable(mContext.get().getResources(), tempBitmap));
			
		}
	}
	
}