package com.alandk.xosomienbac.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alandk.xosomienbac.R;
import com.alandk.xosomienbac.common.LotteryUtils;
import com.alandk.xosomienbac.thongke.CountLoGan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class ThongkeLoganActivity extends Activity {
	private TableLayout tableLogan;

	private TextView textTitleLogan;
	private ProgressBar spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thongke_logan);
		tableLogan = (TableLayout) findViewById(R.id.tableThongkeLogan);
		textTitleLogan = (TextView) findViewById(R.id.displayLoganInfo);
		spinner = (ProgressBar) findViewById(R.id.progressBarLogan);
		if (LotteryUtils.isConnectInternet(this)) {
			textTitleLogan.setText("");
			spinner.setVisibility(View.VISIBLE);
			tableLogan.removeViewAt(1);
			new DownloadWebpageTask(this).execute("http://floating-ravine-3291.herokuapp.com/ThongkeLogan");
		} else {
			textTitleLogan.setText(getResources().getString(R.string.internetConnectionWaring));
			spinner.setVisibility(View.GONE);
			tableLogan.removeViewAt(0);
			tableLogan.removeViewAt(1);
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
		// int len = 5000;

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
			String contentAsString = LotteryUtils.convertStreamToString(is);
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
				spinner.setVisibility(View.GONE);

				Gson gson = new Gson();
				JsonReader reader = new JsonReader(new StringReader(result));
				reader.setLenient(true);
				List<CountLoGan> listLoGan = gson.fromJson(reader, new TypeToken<List<CountLoGan>>() {
				}.getType());
				for (int i = 0; i < 15; i++) {
					CountLoGan countLogan = listLoGan.get(i);
					TableRow row = new TableRow(mContext);
					// TableRow.LayoutParams lpRow = new
					// TableRow.LayoutParams(0,
					// TableRow.LayoutParams.WRAP_CONTENT, 2);
					TableRow.LayoutParams lpRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
					row.setLayoutParams(lpRow);
					TextView loto = new TextView(mContext);
					TableRow.LayoutParams lpCol1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2);
					loto.setLayoutParams(lpCol1);
					loto.setText(countLogan.getResult());
					loto.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					loto.setGravity(Gravity.CENTER);
					loto.setTextSize(20);
					TextView solanchuave = new TextView(mContext);
					TableRow.LayoutParams lpCol2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3);
					solanchuave.setLayoutParams(lpCol2);
					solanchuave.setText(String.valueOf(countLogan.getCount()));
					solanchuave.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					solanchuave.setGravity(Gravity.CENTER);
					solanchuave.setTextSize(20);
					row.addView(loto);
					row.addView(solanchuave);
					tableLogan.addView(row, i + 2);
				}
			} catch (Exception e) {
				Log.e("E", e.getMessage());
				// throw e;
			}

		}

	}

}
