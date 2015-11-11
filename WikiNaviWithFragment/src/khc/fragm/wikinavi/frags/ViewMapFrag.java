package khc.fragm.wikinavi.frags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import khc.fragm.wikinavi.R;
import khc.fragm.wikinavi.asynctasks.PathFindAsyncTask;
import khc.fragm.wikinavi.asynctasks.VertexInfoAsyncTask;
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

		ImageView imageView = (ImageView) mView.findViewById(R.id.map_view);
		downloader.download("http://106.249.235.42:8123/api/maps/1/image", imageView);

		PathFindAsyncTask pathFindAsyncTask = new PathFindAsyncTask(mView.getContext(), mDepartureVertex, mDestVertex);
		
		pathFindAsyncTask.execute("2");
		return mView;
	}
	
}
