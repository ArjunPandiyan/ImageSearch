package com.codepath.imagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultsArrayAdapter imageAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultsArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position, long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
//				i.putExtra("url", imageResult.getUrl());
				i.putExtra("result", imageResult);
				startActivity(i);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	private void setupViews() {
		btnSearch = (Button) findViewById(R.id.btnSearch);
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
	}
	
	public void OnImageSearch(View v) {
		String query = etQuery.getText().toString();
		if(query.length()>0) {
			Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
			AsyncHttpClient client = new AsyncHttpClient();
			client.get("http://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + 0 + "&v=1.0&q=" + Uri.encode(query), 
					new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject response) {
					try {
						JSONArray imageJsonResults = null;
						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
						imageResults.clear();
						imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
		}
		else {
			
		}
	}

}
