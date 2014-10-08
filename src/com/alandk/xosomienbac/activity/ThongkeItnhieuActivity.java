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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alandk.xosomienbac.R;
import com.alandk.xosomienbac.common.LotteryUtils;
import com.alandk.xosomienbac.thongke.CountItNhieu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class ThongkeItnhieuActivity extends Activity {
	private TableLayout tableThongkeItnhieu;

	private Spinner spinnerDate;
	
	private TextView textTitleLoItnhieu;
	private ProgressBar spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thongke_itnhieu);
		tableThongkeItnhieu = (TableLayout) findViewById(R.id.tableThongkeItnhieu);
		addListenerOnSpinnerItemSelection();
		textTitleLoItnhieu = (TextView) findViewById(R.id.displayLoItnhieuInfo);
		spinner = (ProgressBar) findViewById(R.id.progressBarLogan);				
		// showResultThongkeItnhieuByDate(30);
	}

	public void showResultThongkeItnhieuByDate(int songay) {
		if (LotteryUtils.isConnectInternet(this)) {
			textTitleLoItnhieu.setText("");
			spinner.setVisibility(View.VISIBLE);
			new DownloadWebpageTask(this).execute("http://floating-ravine-3291.herokuapp.com/ThongkeItNhieu?songay=" + songay);			
		} else {
			textTitleLoItnhieu.setText(getResources().getString(R.string.internetConnectionWaring));
			spinner.setVisibility(View.GONE);
			tableThongkeItnhieu.removeViews(1, 8);
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
		// int len = 10000;
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
				Gson gson = new Gson();
				JsonReader reader = new JsonReader(new StringReader(result));
				reader.setLenient(true);
				List<CountItNhieu> listItNhieu = gson.fromJson(reader, new TypeToken<List<CountItNhieu>>() {
				}.getType());

				spinner.setVisibility(View.GONE);
				//tableThongkeItnhieu.removeViewAt(0);
				//tableThongkeItnhieu.removeViewAt(0);
				
				removeCurrentResult();
				if(mContext==null){
					mContext = getApplicationContext();
				}

				for (int i = 0; i < 10; i++) {
					CountItNhieu countItNhieuLoto = listItNhieu.get(i);
					CountItNhieu countItNhieuDacbiet = listItNhieu.get(i + 100);
					TableRow row = new TableRow(mContext);
					TableRow.LayoutParams lpRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
					row.setLayoutParams(lpRow);

					TextView dacbiet = new TextView(mContext);
					TableRow.LayoutParams lpCol1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2);
					dacbiet.setLayoutParams(lpCol1);
					dacbiet.setText(countItNhieuDacbiet.getResult());
					dacbiet.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					dacbiet.setGravity(Gravity.CENTER);
					dacbiet.setTextSize(16);
					TextView solanchuaveDacbiet = new TextView(mContext);
					TableRow.LayoutParams lpCol2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3);
					solanchuaveDacbiet.setLayoutParams(lpCol2);
					solanchuaveDacbiet.setText(String.valueOf(countItNhieuDacbiet.getCount()));
					solanchuaveDacbiet.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					solanchuaveDacbiet.setGravity(Gravity.CENTER);
					solanchuaveDacbiet.setTextSize(16);
					row.addView(dacbiet);
					row.addView(solanchuaveDacbiet);

					TextView loto = new TextView(mContext);
					TableRow.LayoutParams lpCol3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2);
					loto.setLayoutParams(lpCol3);
					loto.setText(countItNhieuLoto.getResult());
					loto.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					loto.setGravity(Gravity.CENTER);
					loto.setTextSize(16);
					TextView solanchuaveLoto = new TextView(mContext);
					TableRow.LayoutParams lpCol4 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3);
					solanchuaveLoto.setLayoutParams(lpCol4);
					solanchuaveLoto.setText(String.valueOf(countItNhieuLoto.getCount()));
					solanchuaveLoto.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					solanchuaveLoto.setGravity(Gravity.CENTER);
					solanchuaveLoto.setTextSize(16);
					row.addView(loto);
					row.addView(solanchuaveLoto);
					tableThongkeItnhieu.addView(row, i + 5);
				}
				int startResultDe = 0;
				int startResultLo = 0;
				for (int i = 0; i < 100; i++) {
					CountItNhieu countItNhieuLoto = listItNhieu.get(99 - i);
					if (countItNhieuLoto.getCount() != 0) {
						startResultLo = 99 - i - 9;
						break;
					}
				}

				for (int i = 0; i < 100; i++) {
					CountItNhieu countItNhieuDe = listItNhieu.get(199 - i);
					if (countItNhieuDe.getCount() != 0) {
						startResultDe = 199 - i - 9;
						break;
					}
				}

				for (int i = 0; i < 10; i++) {
					CountItNhieu countItNhieuLoto = listItNhieu.get(startResultLo + i);
					CountItNhieu countItNhieuDacbiet = listItNhieu.get(i + startResultDe);
					TableRow row = new TableRow(mContext);
					TableRow.LayoutParams lpRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
					row.setLayoutParams(lpRow);

					TextView dacbiet = new TextView(mContext);
					TableRow.LayoutParams lpCol1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2);
					dacbiet.setLayoutParams(lpCol1);
					dacbiet.setText(countItNhieuDacbiet.getResult());
					dacbiet.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					dacbiet.setGravity(Gravity.CENTER);
					dacbiet.setTextSize(16);
					TextView solanchuaveDacbiet = new TextView(mContext);
					TableRow.LayoutParams lpCol2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3);
					solanchuaveDacbiet.setLayoutParams(lpCol2);
					solanchuaveDacbiet.setText(String.valueOf(countItNhieuDacbiet.getCount()));
					solanchuaveDacbiet.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					solanchuaveDacbiet.setGravity(Gravity.CENTER);
					solanchuaveDacbiet.setTextSize(16);
					row.addView(dacbiet);
					row.addView(solanchuaveDacbiet);

					TextView loto = new TextView(mContext);
					TableRow.LayoutParams lpCol3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2);
					loto.setLayoutParams(lpCol3);
					loto.setText(countItNhieuLoto.getResult());
					loto.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					loto.setGravity(Gravity.CENTER);
					loto.setTextSize(16);
					TextView solanchuaveLoto = new TextView(mContext);
					TableRow.LayoutParams lpCol4 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3);
					solanchuaveLoto.setLayoutParams(lpCol4);
					solanchuaveLoto.setText(String.valueOf(countItNhieuLoto.getCount()));
					solanchuaveLoto.setBackground(mContext.getResources().getDrawable(R.drawable.border));
					solanchuaveLoto.setGravity(Gravity.CENTER);
					solanchuaveLoto.setTextSize(16);
					row.addView(loto);
					row.addView(solanchuaveLoto);
					tableThongkeItnhieu.addView(row, i + 17);
				}
				String chuaveLo = "";
				String chuaveDe = "";
				for (int i = startResultLo + 10; i < 100; i++) {
					CountItNhieu countItNhieuLoto = listItNhieu.get(i);
					chuaveLo += countItNhieuLoto.getResult() + ";";
				}

				for (int i = startResultDe + 10; i < 200; i++) {
					CountItNhieu countItNhieuDe = listItNhieu.get(i);
					chuaveDe += countItNhieuDe.getResult() + ";";
				}
				if (chuaveLo.length() > 1) {
					chuaveLo = chuaveLo.substring(0, chuaveLo.length() - 1);
				}
				if (chuaveDe.length() > 1) {
					chuaveDe = chuaveDe.substring(0, chuaveDe.length() - 1);
				}

				TableRow row = new TableRow(mContext);
				TableRow.LayoutParams lpRow = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
				row.setLayoutParams(lpRow);
				TextView chuaveDeTextview = new TextView(mContext);
				TableRow.LayoutParams lpCol3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2);
				chuaveDeTextview.setLayoutParams(lpCol3);
				chuaveDeTextview.setText(chuaveDe);
				chuaveDeTextview.setBackground(mContext.getResources().getDrawable(R.drawable.border));
				chuaveDeTextview.setGravity(Gravity.CENTER);
				chuaveDeTextview.setTextSize(16);
				TextView chuaveLoTextview = new TextView(mContext);
				TableRow.LayoutParams lpCol4 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2);
				chuaveLoTextview.setLayoutParams(lpCol4);
				chuaveLoTextview.setText(String.valueOf(chuaveLo));
				chuaveLoTextview.setBackground(mContext.getResources().getDrawable(R.drawable.border));
				chuaveLoTextview.setGravity(Gravity.CENTER);
				chuaveLoTextview.setTextSize(16);
				row.addView(chuaveDeTextview);
				row.addView(chuaveLoTextview);
				tableThongkeItnhieu.addView(row, 29);

			} catch (Exception e) {
				Log.e("E", e.getMessage());
				// throw e;
			}
		}

		private void removeCurrentResult() {
			if (tableThongkeItnhieu.getChildCount() > 10) {
				tableThongkeItnhieu.removeViews(5, 10);
				tableThongkeItnhieu.removeViews(7, 10);
				tableThongkeItnhieu.removeViews(9, 1);
			}
		}

	}

	public void addListenerOnSpinnerItemSelection() {
		spinnerDate = (Spinner) findViewById(R.id.spinnerDateSearch);
		spinnerDate.setOnItemSelectedListener(new CustomOnItemSelectedListener(this));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lottery_menu_statistic, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_undo:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
