package khc.fragm.wikinavi;

import android.app.Activity;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import khc.fragm.wikinavi.frags.MoreViewFrag;
import khc.fragm.wikinavi.frags.SearchDestFrag;
import khc.fragm.wikinavi.frags.SelectMapFrag;
import khc.fragm.wikinavi.frags.ViewMapFrag;
import khc.fragm.wikinavi.asynctasks.VertexInfoAsyncTask;
import android.os.Build;



public class MainActivity extends FragmentActivity implements OnClickListener {

	final String TAG = "wikinavi";
	
	private enum FRAGMENT_INDEX {SELECT_MAP, VIEW_MAP, SEARCH_DEST, MORE_VIEW}
	private FRAGMENT_INDEX mCurrentFragmentIndex ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button bt_selectMap = (Button) findViewById(R.id.bt_selectMap);
		bt_selectMap.setOnClickListener(this);
		Button bt_moreView = (Button) findViewById(R.id.bt_moreView);
		bt_moreView.setOnClickListener(this);
		Button bt_searchDest = (Button) findViewById(R.id.bt_searchDest);
		bt_searchDest.setOnClickListener(this);
		Button bt_viewMap = (Button) findViewById(R.id.bt_viewMap);
		bt_viewMap.setOnClickListener(this);
		
		// beacon vertex 정보 수신 
		// bluetooth scan start
		VertexInfoAsyncTask ble = new VertexInfoAsyncTask();
		ble.init();
		
		
		mCurrentFragmentIndex = FRAGMENT_INDEX.SELECT_MAP;

		fragmentReplace(mCurrentFragmentIndex);
	}
	
	public void fragmentReplace(FRAGMENT_INDEX reqNewFragmentIndex) {

		Fragment newFragment = null;

		Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

		newFragment = getFragment(reqNewFragmentIndex);

		// replace fragment
		final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		transaction.replace(R.id.ll_fragment, newFragment);

		// Commit the transaction
		transaction.commit();

	}
	
	private Fragment getFragment(FRAGMENT_INDEX idx) {
		Fragment newFragment = null;

		Log.d(TAG, "idx = " + idx);
		switch (idx) {
		case SELECT_MAP:
			newFragment = new SelectMapFrag();
			break;
		case VIEW_MAP : 
			newFragment = new ViewMapFrag();
			break;
		case SEARCH_DEST : 
			newFragment = new SearchDestFrag();
		break;
		case MORE_VIEW:
			newFragment = new MoreViewFrag();
			break;
		default:
			Log.d(TAG, "Unhandle case");
			break;
		}

		return newFragment;
	}
	
	@Override
	public void onClick(View v){
		boolean error = false;
		Log.d(TAG, "v.getId = " + v.getId());
		switch(v.getId()){
		case R.id.bt_selectMap :
			mCurrentFragmentIndex = FRAGMENT_INDEX.SELECT_MAP;
			break;
		case R.id.bt_viewMap :
			mCurrentFragmentIndex = FRAGMENT_INDEX.VIEW_MAP;
			break;
		case R.id.bt_searchDest :
			mCurrentFragmentIndex = FRAGMENT_INDEX.SEARCH_DEST;
			break;
		case R.id.bt_moreView :
			mCurrentFragmentIndex = FRAGMENT_INDEX.MORE_VIEW;
			break;	
		}
		
		if(!error)
			fragmentReplace(mCurrentFragmentIndex);
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//			return rootView;
//		}
//	}
}
