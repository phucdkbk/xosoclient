package com.alandk.xosomienbac.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.alandk.xosomienbac.common.LotteryUtils;
import com.alandk.xosomienbac.common.Result;
import com.alandk.xosomienbac.thongke.CountItNhieu;
import com.alandk.xosomienbac.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ThongkeItnhieuActivity extends Activity {
	private TableLayout tableThongkeItnhieu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thongke_itnhieu);
		tableThongkeItnhieu = (TableLayout) findViewById(R.id.tableThongkeItnhieu);
		if (LotteryUtils.isConnectInternet(this)) {
			new DownloadWebpageTask(this).execute("http://floating-ravine-3291.herokuapp.com/ThongkeItNhieu?songay=30");
		}
	}

	/**
	 * download given url, return string content
	 * 
	 * @param myurl
	 * @return
	 * @throws IOException
	 */
	public static String downloadUrl(String myurl) throws Exception {
		InputStream is = null;
		// Only display the first 500 characters of the retrieved
		// web page content.
		int len = 10000;

		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			Log.d("DownloadURL", "The response is: " + response);
			is = conn.getInputStream();

			// Convert the InputStream into a string
			// String contentAsString = readIt(is, len);
			String contentAsString = convertStreamToString(is);
			return contentAsString;
		} catch (Exception e) {
			Log.e("E", e.getMessage());
			throw e;
		}
		// Makes sure that the InputStream is closed after the app is
		// finished using it.
		finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

	public static String convertStreamToString(java.io.InputStream is) {
		Scanner s = new Scanner(is, "UTF-8").useDelimiter("//A");
		return s.hasNext() ? s.next() : "";
	}

	private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
		private Context mContext;

		public DownloadWebpageTask(Context mContext) {
			super();
			this.mContext = mContext;
		}

		@Override
		protected String doInBackground(String... urls) {
			// params comes from the execute() call: params[0] is the url.
			try {
				return downloadUrl(urls[0]);
			} catch (Exception e) {
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			try {
				// result = result + "]";
				// result = result.trim();
				// result = result.substring(0, result.lastIndexOf("]"));
				// List<CountLoGan> listLoGan = new ArrayList<CountLoGan>();

				Gson gson = new Gson();
				JsonReader reader = new JsonReader(new StringReader(result));
				reader.setLenient(true);
				List<CountItNhieu> listItNhieu = gson.fromJson(reader, new TypeToken<List<CountItNhieu>>() {
				}.getType());
				for (int i = 0; i < 15; i++) {
					CountItNhieu countItNhieu = listItNhieu.get(i);
					TableRow row = new TableRow(mContext);
					//TableRow.LayoutParams lpRow = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2);
					TableRow.LayoutParams lpRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
					row.setLayoutParams(lpRow);
					TextView loto = new TextView(mContext);
					TableRow.LayoutParams lpCol1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2);
					loto.setLayoutParams(lpCol1);
					loto.setText(countItNhieu.getResult());
					loto.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					loto.setGravity(Gravity.CENTER);
					loto.setTextSize(20);
					TextView solanchuave = new TextView(mContext);
					TableRow.LayoutParams lpCol2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3);
					solanchuave.setLayoutParams(lpCol2);
					solanchuave.setText(String.valueOf(countItNhieu.getCount()));
					solanchuave.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					solanchuave.setGravity(Gravity.CENTER);
					solanchuave.setTextSize(20);
					row.addView(loto);
					row.addView(solanchuave);
					tableThongkeItnhieu.addView(row, i + 1);
				}
			} catch (Exception e) {
				Log.e("E", e.getMessage());
				// throw e;
			}

		}

	}

}
