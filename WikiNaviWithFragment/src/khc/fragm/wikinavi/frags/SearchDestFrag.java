package khc.fragm.wikinavi.frags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import khc.fragm.wikinavi.R;
import khc.fragm.wikinavi.asynctasks.VertexInfoAsyncTask;
import khc.fragm.wikinavi.dataSet.Vertex;

public class SearchDestFrag extends Fragment implements OnClickListener, OnItemClickListener {
	final String TAG = "SearchDestFrag";
	private View mView = null;
	private ListView mListView = null;
	private int mBtnClickState = 0;
	private Vertex mDepartureVertex, mDestVertex;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.search_dest, container, false);

		Button departuerSearchBtn = (Button) mView.findViewById(R.id.departureSearchBtn);
		Button destSearchBtn = (Button) mView.findViewById(R.id.destSearchBtn);
		Button routheSearchBtn = (Button) mView.findViewById(R.id.pathSearchBtn);

		departuerSearchBtn.setOnClickListener(this);
		destSearchBtn.setOnClickListener(this);
		routheSearchBtn.setOnClickListener(this);
		
		mListView = (ListView) mView.findViewById(R.id.vertexListView);
		mListView.setOnItemClickListener(this);

		Log.d(TAG, TAG + " View");

		return mView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		boolean error = false;

		Log.d(TAG, "onClick" + v.getId());

		switch (v.getId()) {
		case R.id.departureSearchBtn:
			String departureName = ((EditText) mView.findViewById(R.id.departureSearch)).getText().toString(); 
			runAsync("2", departureName);
			mBtnClickState = 1;
			break;
		case R.id.destSearchBtn:
			String destName = ((EditText) mView.findViewById(R.id.destSearch)).getText().toString();
			runAsync("2", destName);
			mBtnClickState = 2;
			break;
		case R.id.pathSearchBtn:

			// 전달인자 사용해서 departure, dest 정보 넘기기
			Fragment newFragment = null;
			newFragment = new ViewMapFrag(mDepartureVertex, mDestVertex);

			// replace fragment
			final FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.ll_fragment, newFragment);

			// Commit the transaction
			transaction.commit();
			
			mBtnClickState = 0;
			break;
		}

		if (!error)
			Log.d(TAG, TAG + " Error!");
	}

	private void runAsync(String... text) {
		VertexInfoAsyncTask task = new VertexInfoAsyncTask(mView.getContext(), mListView);

		task.execute(text);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		EditText t = null;
		Vertex item = null;
		
		switch (mBtnClickState) {
		case 1:
			t = (EditText) mView.findViewById(R.id.departureSearch);
			item = (Vertex)mListView.getAdapter().getItem(position);
			mDepartureVertex = item;
			t.setText(item.getName());
			break;
		case 2:
			t = (EditText) mView.findViewById(R.id.destSearch);
			item = (Vertex)mListView.getAdapter().getItem(position);
			mDestVertex = item;
			t.setText(item.getName());
			break;
		}
	}

}
