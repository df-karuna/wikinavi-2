package khc.fragm.wikinavi.frags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import khc.fragm.wikinavi.R;

public class MoreViewFrag extends Fragment {
	final String TAG = "MoreViewFrag";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "View!");
		
		View v = inflater.inflate(R.layout.more_view, container, false);
		
		return v;
	}
}
