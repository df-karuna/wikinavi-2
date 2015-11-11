package khc.fragm.wikinavi.frags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import khc.fragm.wikinavi.R;
import khc.fragm.wikinavi.asynctasks.VertexInfoAsyncTask;

public class SearchDestFrag extends Fragment implements OnClickListener {
	final String TAG = "SearchDestFrag";
	private View mView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mView = inflater.inflate(R.layout.search_dest, container, false);

		Button departuerSearchBtn = (Button) mView.findViewById(R.id.departureSearchBtn);
		Button destSearchBtn = (Button) mView.findViewById(R.id.destSearchBtn);
		Button routheSearchBtn = (Button) mView.findViewById(R.id.routeSearchBtn);

		departuerSearchBtn.setOnClickListener(this);
		destSearchBtn.setOnClickListener(this);
		routheSearchBtn.setOnClickListener(this);

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
			EditText t =(EditText) mView.findViewById(R.id.departureSearch);
			String departureName = t.getText().toString();
			runAsync("1", departureName);
			break;
		case R.id.destSearchBtn:
			String destName = ((EditText) mView.findViewById(R.id.destSearch)).getText().toString();
			runAsync("1", destName);
			break;
		case R.id.routeSearchBtn:
			Fragment newFragment = null;
			newFragment = new ViewMapFrag();

			// replace fragment
			final FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.ll_fragment, newFragment);

			// Commit the transaction
			transaction.commit();
			break;
		}

		if (!error)
			;
	}

	private void runAsync(String ... text) {
		ListView listView = (ListView) mView.findViewById(R.id.vertexListView);
		VertexInfoAsyncTask task = new VertexInfoAsyncTask(mView.getContext(), listView);

		task.execute(text);
	}

}
