package com.codepath.imagesearch;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8662688882869237116L;
	String url;
	String tbUrl;
	
	public ImageResult(JSONObject json) {
		try {
			this.url = json.getString("url");
			this.tbUrl = json.getString("tbUrl");
		} catch (JSONException e) {
			this.url=this.tbUrl = null;
		}
		
	}
	
	public String getUrl() {
		return url;
	}
	public String getTbUrl() {
		return tbUrl;
	}
	
	public String toString() {
		return this.tbUrl;
	}

	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray array) {
			ArrayList<ImageResult> results = new ArrayList<ImageResult>();
			for(int i=0 ; i<array.length() ; i++) {
				try {
					results.add(new ImageResult(array.getJSONObject(i)));
				}
				catch(Exception e) {
					
				}
			}
		return results;
	}
	
}
