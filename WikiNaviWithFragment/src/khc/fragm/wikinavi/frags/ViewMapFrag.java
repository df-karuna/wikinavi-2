package khc.fragm.wikinavi.frags;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import khc.fragm.wikinavi.R;
import khc.fragm.wikinavi.asynctasks.PathFindAsyncTask;
import khc.fragm.wikinavi.asynctasks.VertexInfoAsyncTask;
import khc.fragm.wikinavi.components.PathImageView;
import khc.fragm.wikinavi.dataSet.Vertex;
import khc.fragm.wikinavi.lib.ImageDownloader;
import khc.fragm.wikinavi.lib.ImageDownloader.Mode;

public class ViewMapFrag extends Fragment {
	View mView = null;
	final String TAG = "ViewMapFrag";
	private Vertex mDepartureVertex, mDestVertex;

	public ViewMapFrag(Vertex departureVertex, Vertex destVertex) {
		this.mDepartureVertex = departureVertex;
		this.mDestVertex = destVertex;
	}

	public ViewMapFrag() {
		// TODO Auto-generated constructor stub
		this.mDepartureVertex = new Vertex();
		this.mDestVertex = new Vertex();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mView = inflater.inflate(R.layout.view_map, container, false);
		Log.d(TAG, "View");
		ImageDownloader downloader = new ImageDownloader();
		downloader.setMode(Mode.CORRECT);
		
		TextView inform = (TextView) mView.findViewById(R.id.text_inform);
		
		if(mDepartureVertex.getVertexId() == 0 || mDepartureVertex.getVertexId() == 0)
			inform.setVisibility(View.INVISIBLE);
		inform.setText(mDepartureVertex.getName()+"부터 " + mDestVertex.getName()+"까지의 최단경로입니다.");

		ImageView imageView = (ImageView) mView.findViewById(R.id.map_view);
//		ImageView imageView = (ImageView) mView.findViewById(R.id.map_view);
		ImageView tmpImageView = new ImageView(mView.getContext());
		downloader.download("http://106.249.235.42:8123/api/maps/2/image", imageView);
		PathFindAsyncTask pathFindAsyncTask = new PathFindAsyncTask(mView.getContext(), imageView, mDepartureVertex, mDestVertex );
		
		pathFindAsyncTask.execute("2");
		return mView;
	}
	
}
