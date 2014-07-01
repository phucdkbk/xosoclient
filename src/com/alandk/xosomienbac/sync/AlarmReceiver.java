package com.alandk.xosomienbac.sync;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.alandk.xosomienbac.activity.LotteryResultActivity;
import com.alandk.xosomienbac.activity.ScreenSlidePageFragment;
import com.alandk.xosomienbac.common.Result;
import com.alandk.xosomienbac.database.LotteryDBResult;
import com.example.xosomienbac.R;
import com.google.gson.Gson;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	private NotificationManager myNotificationManager;

	private int notificationIdOne = 111;
	private int notificationIdTwo = 112;
	private int numMessagesOne = 0;
	private int numMessagesTwo = 0;

	@Override
	public void onReceive(Context context, Intent arg1) {
		// For our recurring task, we'll just display a message
		// Toast.makeText(arg0, "I'm running", Toast.LENGTH_SHORT).show();
		displayNotificationOne(context, "");

	}

	protected void displayNotificationOne(Context context, String displayText) {

		// Invoking the default notification service
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

		mBuilder.setContentTitle("New Message " + displayText);
		mBuilder.setContentText("New message from javacodegeeks received");
		mBuilder.setTicker("Explicit: New Message Received!");
		mBuilder.setSmallIcon(R.drawable.ic_launcher);

		// Increase notification number every time a new notification arrives
		mBuilder.setNumber(++numMessagesOne);

		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(context, NotificationOne.class);
		resultIntent.putExtra("notificationId", notificationIdOne);

		// This ensures that navigating backward from the Activity leads out of
		// the app to Home page
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent
		stackBuilder.addParentStack(NotificationOne.class);

		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		// can
		// only
		// be
		// used
		// once
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
		// start the activity when the user clicks the notification text
		mBuilder.setContentIntent(resultPendingIntent);

		myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		// pass the Notification object to the system
		myNotificationManager.notify(notificationIdOne, mBuilder.getNotification());

		try {
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
			r.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String haveNewResult(Context context) {
		String newResult = "";
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		int dateInt = Integer.valueOf(df.format(cal.getTime()));

		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			new DownloadWebpageTask(dateInt, context).execute("http://floating-ravine-3291.herokuapp.com/LotteryResult?date=" + dateInt);
		} else {
			// display error
		}
		return newResult;
	}

	private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
		private int date;
		private Context context;

		public DownloadWebpageTask(int date, Context context) {
			super();
			this.date = date;
			this.context = context;
		}

		@Override
		protected String doInBackground(String... urls) {
			// params comes from the execute() call: params[0] is the url.
			try {
				return ScreenSlidePageFragment.downloadUrl(urls[0]);
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
					Result currentResult = ScreenSlidePageFragment.convertFromJsonToResultObject(result);
					// Gson gson = new Gson();
					// if (lotteryResult.isHasFullValue()) {
					// LotteryResultActivity.createLotteryDBResult(date,
					// gson.toJson(lotteryResult));
					// }
					String newResult = getNewResult(currentResult);
					if (newResult != null && !newResult.isEmpty()) {
						displayNotificationOne(context, newResult);
					}
				}
			} catch (Exception e) {
				Log.e("E", e.getMessage());
				throw e;
			}

		}

		private String getNewResult(Result currentResult) {
			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			int dateInt = Integer.valueOf(df.format(cal.getTime()));
			LotteryDBResult lotteryDBResult = LotteryResultActivity.getLotteryDBResultByDate(dateInt);
			Result lotteryResult = ScreenSlidePageFragment.convertFromJsonToResultObject(lotteryDBResult.getResult());
			// TODO Auto-generated method stub
			return getDifferentResult(currentResult, lotteryResult);
		}
	}

	public String getDifferentResult(Result fromResult, Result toResult) {
		String differentResult = "";
		if ((fromResult.getGiaiDB() != null && !fromResult.getGiaiDB().isEmpty()) && (toResult.getGiaiDB() == null || toResult.getGiaiDB().isEmpty())) {
			differentResult += R.string.giaiDBLabel + ": " + fromResult.getGiaiDB();
		}
		if ((fromResult.getGiaiNhat() != null && !fromResult.getGiaiNhat().isEmpty()) && (toResult.getGiaiNhat() == null || toResult.getGiaiNhat().isEmpty())) {
			differentResult += R.string.giaiNhatLabel + ": " + fromResult.getGiaiNhat();
		}

		String differentNumber;
		differentNumber = getDifferentNumber(fromResult.getArrGiaiNhi(), toResult.getArrGiaiNhi());
		if (differentNumber != null && !differentNumber.isEmpty()) {
			differentResult += R.string.giaiNhiLabel + ": " + differentNumber;
		}

		differentNumber = getDifferentNumber(fromResult.getArrGiaiBa(), toResult.getArrGiaiBa());
		if (differentNumber != null && !differentNumber.isEmpty()) {
			differentResult += R.string.giaiBaLabel + ": " + differentNumber;
		}

		differentNumber = getDifferentNumber(fromResult.getArrGiaiTu(), toResult.getArrGiaiTu());
		if (differentNumber != null && !differentNumber.isEmpty()) {
			differentResult += R.string.giaiTuLabel + ": " + differentNumber;
		}

		differentNumber = getDifferentNumber(fromResult.getArrGiaiNam(), toResult.getArrGiaiNam());
		if (differentNumber != null && !differentNumber.isEmpty()) {
			differentResult += R.string.giaiNamLabel + ": " + differentNumber;
		}

		differentNumber = getDifferentNumber(fromResult.getArrGiaiSau(), toResult.getArrGiaiSau());
		if (differentNumber != null && !differentNumber.isEmpty()) {
			differentResult += R.string.giaiSauLabel + ": " + differentNumber;
		}

		differentNumber = getDifferentNumber(fromResult.getArrGiaiBay(), toResult.getArrGiaiBay());
		if (differentNumber != null && !differentNumber.isEmpty()) {
			differentResult += R.string.giaiBayLabel + ": " + differentNumber;
		}

		return differentResult;
	}

	private String getDifferentNumber(String[] fromArrNums, String[] toArrNums) {
		String differentNumber = "";
		int oldLengh = 0;
		int newLength = 0;

		if (toArrNums != null) {
			oldLengh = toArrNums.length;
		}
		if (fromArrNums != null) {
			newLength = fromArrNums.length;
		}
		if (newLength > oldLengh) {
			if (oldLengh == 0) {
				for (int i = 0; i < newLength; i++) {
					differentNumber += toArrNums[i];
				}
			} else {
				for (int i = 0; i < newLength; i++) {
					String newNumber = fromArrNums[i].trim();
					boolean isNew = true;
					for (int j = 0; j < oldLengh; j++) {
						if (toArrNums[j].trim().equals(newNumber)) {
							isNew = false;
							break;
						}
					}
					if (isNew) {
						differentNumber += fromArrNums[i];
					}
				}
			}
		}
		return differentNumber;
	}

}
