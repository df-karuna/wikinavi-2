package khc.fragm.wikinavi.asynctasks;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// import khc.fragm.wikinavi.PeripheralListAdatper;
import khc.fragm.wikinavi.R;
import khc.fragm.wikinavi.asynctasks.common.HttpJsonParser;
import khc.fragm.wikinavi.components.PeripheralListAdapter;
import khc.fragm.wikinavi.config.ApplicationConfig;
import khc.fragm.wikinavi.dataSet.Map;
import khc.fragm.wikinavi.dataSet.Vertex;

import com.google.gson.reflect.TypeToken;
import com.wizturn.sdk.central.Central;
import com.wizturn.sdk.central.CentralManager;
import com.wizturn.sdk.peripheral.Peripheral;
import com.wizturn.sdk.peripheral.PeripheralScanListener;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class VertexInfoAsyncTask extends AsyncTask<String, Void, List<Vertex>> {

	private PeripheralListAdapter listAdapter;

	private CentralManager centralManager;
	private List<Peripheral> mPeripherals = null;
	
	
	
	private WeakReference<Context> mApplicationContext;

	public VertexInfoAsyncTask(Context mContext) {
		this.mApplicationContext = new WeakReference<Context>(mContext);
	}

	@Override
	protected List<Vertex> doInBackground(String... params) {
		// TODO Auto-generated method stub

		HttpJsonParser parser = new HttpJsonParser("http://106.249.235.42:8123");
		StringBuilder uri = new StringBuilder();
		
		uri.append("/api/maps/");
		
		if(params != null){
			uri.append(params[0]).append("/vertexes?type=").append(params[2]);
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
	protected void onPostExecute(List<Vertex> result) {
		// TODO Auto-generated method stub
		Log.d("TAG", "OnPostExecute log");
		if(result != null)
			for(Vertex v : result){
				Log.d("mainActivity", v.getMacAddr());
			}
		init();
	}

	private void terminateIfNotBLE() {
		if (!centralManager.isBLESupported()) {
			Toast.makeText(mApplicationContext.get(), khc.fragm.wikinavi.R.string.error_ble_not_support,
					Toast.LENGTH_LONG).show();
		}
	}

	private void turnOnBluetooth() {
		if (!centralManager.isBluetoothEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		}

		if (centralManager.isBluetoothEnabled()) {
			Log.i("mainActivity", "bluetooth enabled, start scanning...");
			scan();
		}
	}

	private void init() {

		setCentralManager();
		terminateIfNotBLE();
		turnOnBluetooth();
		//scan();
	}

	private void setCentralManager() {
		mPeripherals = new ArrayList<Peripheral>();
		centralManager = CentralManager.getInstance();

		
		Context c = mApplicationContext.get();
		centralManager.init(c);

		centralManager.setPeripheralScanListener(new PeripheralScanListener() {
			@Override
			public void onPeripheralScan(Central central, final Peripheral peripheral) {

				ApplicationConfig.mainActivityContext.runOnUiThread(new Runnable() {
					public void run() {

						if (mPeripherals.size() > 3) {
							stop();
						} else if (peripheral.getBDName().equals("pebBLE")) {
							Log.d("test", peripheral.getBDAddress());
							mPeripherals.add(peripheral);
						}

						Log.i("mainActivity", mPeripherals.size() + "");
						
						try {
							Thread.sleep(3000);
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		});
	}

	private void scan() {
		if (!centralManager.isBluetoothEnabled()) {
			Log.i("mainActivity", "before scanA");
			turnOnBluetooth();
		}
		Log.i("mainActivity", "before scanB");
		centralManager.startScanning();
	}

	private void stop() {
		centralManager.stopScanning();
	}

	
	private void clear() {
		listAdapter.clear();
	}
	
	// private void showPeripherials(){
	// // String BDAddressToFind = "D0:39:72:A4:96:FD";
	// String BDAddressToFind = "D0:39:72:A4:B2:64";
	// Peripheral firstPeripherial = null;
	// for(Peripheral p : mPeripherals){
	// Log.i("mainActivity", p.getDistance() + "");
	// Log.i("mainActivity", p.getBDAddress() + "");
	// if(p.getBDAddress().equals(BDAddressToFind))
	// firstPeripherial = p;
	//
	// }
	//
	// TextView distanceA = (TextView)
	// findViewById(khc.fragm.wikinavi.R.id.distanceA);
	// distanceA.setText(mPeripherals.get(0).getDistance() + "m");
	// }
	//
}