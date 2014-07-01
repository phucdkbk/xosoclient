/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alandk.xosomienbac.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alandk.xosomienbac.common.DisplayResult;
import com.alandk.xosomienbac.common.Result;
import com.alandk.xosomienbac.database.LotteryDBResult;
import com.example.xosomienbac.R;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy
 * title indicating the page number, along with some dummy text.
 * 
 * <p>
 * This class is used by the {@link CardFlipActivity} and
 * {@link ScreenSlideActivity} samples.
 * </p>
 */
public class ScreenSlidePageFragment extends Fragment {
	private static Context mContext;
	/**
	 * The argument key for the page number this fragment represents.
	 */
	public static final String ARG_PAGE = "page";
	private DisplayResult displayResult;

	/**
	 * The fragment's page number, which is set to the argument value for
	 * {@link #ARG_PAGE}.
	 */
	private int mPageNumber;

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given page number.
	 */
	public static ScreenSlidePageFragment create(int pageNumber, Context context) {
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		mContext = context;
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ScreenSlidePageFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout containing a title and body text.
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
		initDiplayResult(rootView);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2009);
		cal.add(Calendar.DATE, mPageNumber - LotteryResultActivity.NUM_PAGES / 2);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		int dateInt = Integer.valueOf(df.format(cal.getTime()));

		LotteryDBResult lotteryDBResult = LotteryResultActivity.getLotteryDBResultByDate(dateInt);
		if (lotteryDBResult != null) {
			Result lotteryResult = convertFromJsonToResultObject(lotteryDBResult.getResult());
			convertToDisplayResult(lotteryResult);
		} else {
			ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				new DownloadWebpageTask(dateInt).execute("http://floating-ravine-3291.herokuapp.com/LotteryResult?date=" + dateInt);
			} else {
				// display error
			}
		}
		//
		// Set the title view to show the page number.
		TextView textView = (TextView) rootView.findViewById(R.id.currentDate);
		textView.setText(getDisplayDateFromDateInt(dateInt));
		return rootView;
	}

	private String getDisplayDateFromDateInt(int dateInt) {
		String displayDate = "";
		int date = dateInt % 100;
		int month = dateInt / 100 % 100;
		int year = dateInt / 10000;
		if (date < 10) {
			displayDate += ("0" + date + "/");
		} else {
			displayDate += (date + "/");
		}
		if (month < 10) {
			displayDate += ("0" + month + "/");
		} else {
			displayDate += (month + "/");
		}
		displayDate += year;
		return displayDate;
	}

	/**
	 * initialize display view on activity
	 * 
	 * @param rootView
	 */
	private void initDiplayResult(ViewGroup rootView) {
		displayResult = new DisplayResult();
		displayResult.setGiaiDB((TextView) rootView.findViewById(R.id.giaiDBValue));
		displayResult.setGiaiNhat((TextView) rootView.findViewById(R.id.giaiNhatValue));
		displayResult.setGiaiNhi((TextView) rootView.findViewById(R.id.giaiNhiValue));
		displayResult.setGiaiBa((TextView) rootView.findViewById(R.id.giaiBaValue));
		displayResult.setGiaiTu((TextView) rootView.findViewById(R.id.giaiTuValue));
		displayResult.setGiaiNam((TextView) rootView.findViewById(R.id.giaiNamValue));
		displayResult.setGiaiSau((TextView) rootView.findViewById(R.id.giaiSauValue));
		displayResult.setGiaiBay((TextView) rootView.findViewById(R.id.giaiBayValue));

		displayResult.setDau0((TextView) rootView.findViewById(R.id.dau0));
		displayResult.setDau1((TextView) rootView.findViewById(R.id.dau1));
		displayResult.setDau2((TextView) rootView.findViewById(R.id.dau2));
		displayResult.setDau3((TextView) rootView.findViewById(R.id.dau3));
		displayResult.setDau4((TextView) rootView.findViewById(R.id.dau4));
		displayResult.setDau5((TextView) rootView.findViewById(R.id.dau5));
		displayResult.setDau6((TextView) rootView.findViewById(R.id.dau6));
		displayResult.setDau7((TextView) rootView.findViewById(R.id.dau7));
		displayResult.setDau8((TextView) rootView.findViewById(R.id.dau8));
		displayResult.setDau9((TextView) rootView.findViewById(R.id.dau9));
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}

	/**
	 * download given url, return string content
	 * 
	 * @param myurl
	 * @return
	 * @throws IOException
	 */
	public static String downloadUrl(String myurl) throws IOException {
		InputStream is = null;
		// Only display the first 500 characters of the retrieved
		// web page content.
		int len = 500;

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
			String contentAsString = readIt(is, len);
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
		private int date;

		public DownloadWebpageTask(int date) {
			super();
			this.date = date;
		}

		@Override
		protected String doInBackground(String... urls) {
			// params comes from the execute() call: params[0] is the url.
			try {
				return downloadUrl(urls[0]);
			} catch (IOException e) {
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			try {
				result = result.trim();
				if (result != null && !result.isEmpty() && result.length() > 2) {
					Result lotteryResult = convertFromJsonToResultObject(result);
					Gson gson = new Gson();
					if (lotteryResult.isHasFullValue()) {
						LotteryResultActivity.createLotteryDBResult(date, gson.toJson(lotteryResult));
					}
					convertToDisplayResult(lotteryResult);
				}
			} catch (Exception e) {
				Log.e("E", e.getMessage());
				throw e;
			}

		}
	}

	/**
	 * Convert from json to lottery result logic object
	 * 
	 * @param result
	 * @return
	 */
	public static Result convertFromJsonToResultObject(String result) {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(result));
		reader.setLenient(true);
		Result lotteryResult = gson.fromJson(reader, Result.class);
		return lotteryResult;
	}

	/**
	 * Convert from lottery result logic object to display object
	 * 
	 * @param lotteryResult
	 */
	private void convertToDisplayResult(Result lotteryResult) {
		displayResult.getGiaiDB().setText(lotteryResult.getGiaiDB());
		displayResult.getGiaiNhat().setText(lotteryResult.getGiaiNhat());
		displayResult.getGiaiNhi().setText(lotteryResult.getGiaiNhi());
		displayResult.getGiaiBa().setText(lotteryResult.getGiaiBa());
		displayResult.getGiaiTu().setText(lotteryResult.getGiaiTu());
		displayResult.getGiaiNam().setText(lotteryResult.getGiaiNam());
		displayResult.getGiaiSau().setText(lotteryResult.getGiaiSau());
		displayResult.getGiaiBay().setText(lotteryResult.getGiaiBay());

		lotteryResult.caculateDauso();
		displayResult.getDau0().setText(lotteryResult.getDau0());
		displayResult.getDau1().setText(lotteryResult.getDau1());
		displayResult.getDau2().setText(lotteryResult.getDau2());
		displayResult.getDau3().setText(lotteryResult.getDau3());
		displayResult.getDau4().setText(lotteryResult.getDau4());
		displayResult.getDau5().setText(lotteryResult.getDau5());
		displayResult.getDau6().setText(lotteryResult.getDau6());
		displayResult.getDau7().setText(lotteryResult.getDau7());
		displayResult.getDau8().setText(lotteryResult.getDau8());
		displayResult.getDau9().setText(lotteryResult.getDau9());
	}
}