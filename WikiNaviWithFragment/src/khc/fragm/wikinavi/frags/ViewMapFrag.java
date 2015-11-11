package khc.fragm.wikinavi.frags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import khc.fragm.wikinavi.R;

public class ViewMapFrag extends Fragment {
	final String TAG = "ViewMapFrag";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.view_map, container, false);
		Log.d(TAG, TAG + " View");
		
		return v;
	}

}
