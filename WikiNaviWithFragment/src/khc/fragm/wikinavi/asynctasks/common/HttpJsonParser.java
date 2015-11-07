package khc.fragm.wikinavi.asynctasks.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import android.util.Log;

public class HttpJsonParser {
	
	private String httpDomain = null;
	
	/**
	 * 
	 * @param httpDomain 도메인 주소. http://(httpDomain with ports)
	 */
	public HttpJsonParser(String httpDomain){
		this.httpDomain = httpDomain;
	}
	
	/**
	 * 
	 * @param uri
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	public Object parse(String uri, Type dataType) throws Exception{
		URL url;
		Gson g = new Gson();
		HttpURLConnection urlConnection = null;
		InputStream in = null;
		
		try{
			url = new URL(httpDomain + uri);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream());
			String data = getStringFromInputStream(in); //new String(bos.toByteArray());
			
			Log.d("HttpJsonParser", data);
			
			JsonElement el = new JsonParser().parse(data);
			
			JsonObject jsonObj = el.getAsJsonObject();
			
			JsonObject result = jsonObj.getAsJsonObject("result");
			JsonArray dataSet = jsonObj.getAsJsonArray("dataSet");
			if(result.getAsJsonPrimitive("value").getAsInt() != 0){
				throw new Exception("API Failed");
			}
			return g.fromJson(dataSet, dataType);
		}
		catch(MalformedURLException ex){
			ex.printStackTrace();
			Log.e("HttpJsonParser", ex.getMessage());
		} catch(IOException ex) {
			ex.printStackTrace();
			Log.e("HttpJsonParser", ex.getMessage());
		} catch(JsonSyntaxException ex) {
			ex.printStackTrace();
			Log.e("HttpJsonParser", ex.getMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
			Log.e("HttpJsonParser", ex.getMessage());
		} finally {
			in.close();
			urlConnection.disconnect();
		}
		
		return null;
	}
	
	public Object parse(String uri, Class<?> dataType) throws Exception{
		URL url;
		HttpURLConnection urlConnection = null;
		Gson g = new Gson();
		
		
		try{
			url = new URL(httpDomain + uri);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			String data = getStringFromInputStream(in); //new String(bos.toByteArray());
			
			JsonElement el = new JsonParser().parse(data);
			
			JsonObject jsonObj = el.getAsJsonObject();
			
			JsonObject result = jsonObj.getAsJsonObject("result")
					, dataSet = jsonObj.getAsJsonObject("dataSet");
			if(result.getAsJsonObject("value").getAsInt() != 0){
				throw new Exception("API Failed");
			}
			
			return g.fromJson(dataSet, dataType);
		}
		catch(MalformedURLException ex){
			ex.printStackTrace();
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch(JsonSyntaxException ex) {
			ex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}
		
		return null;
	}
	
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

}
