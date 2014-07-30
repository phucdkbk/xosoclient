package com.alandk.xosomienbac.activity;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alandk.xosomienbac.R;
import com.alandk.xosomienbac.common.Constants;
import com.alandk.xosomienbac.common.LotteryUtils;
import com.alandk.xosomienbac.database.LotteryDBResult;
import com.alandk.xosomienbac.database.LotteryDataSource;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class LotteryResultActivity extends FragmentActivity {

	private static LotteryDataSource lotteryDataSource;

	/** The view to show the ad. */
	private AdView adView;

	/* Your ad unit id. Replace with your actual ad unit id. */
	private static final String AD_UNIT_ID = "ca-app-pub-1954954439370083/7603841453";

	public LotteryResultActivity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	public void gotoPreviousDate() {
		mPager.setCurrentItem(mPager.getCurrentItem() - 1);
	}

	public void gotoNextDate() {
		mPager.setCurrentItem(mPager.getCurrentItem() + 1);
	}

	public void gotoDate(Date date) {
		int selectItem = Constants.NUM_PAGES / 2 + get_days_between_dates(new Date(), date);
		mPager.setCurrentItem(selectItem);
	}

	public int get_days_between_dates(Date date1, Date date2) {
		// if date2 is more in the future than date1 then the result will be
		// negative
		// if date1 is more in the future than date2 then the result will be
		// positive.
		return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24l));
	}

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		removeNotification();
		setContentView(R.layout.activity_screen_slide);
		insertAds();
		lotteryDataSource = new LotteryDataSource(this);
		lotteryDataSource.open();
		LotteryUtils.setResultNotification(this); 
		setSlilePagerAdapter();
		mPager.setCurrentItem(com.alandk.xosomienbac.common.Constants.NUM_PAGES / 2, true);
	}

	private void setSlilePagerAdapter() {
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(), this);
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When changing pages, reset the action bar actions since they
				// are dependent
				// on which page is currently active. An alternative approach is
				// to have each
				// fragment expose actions itself (rather than the activity
				// exposing actions),
				// but for simplicity, the activity provides the actions in this
				// sample.
				invalidateOptionsMenu();
			}
		});
	}

	private void removeNotification() {
		Bundle extras = getIntent().getExtras();
		try {
			int id = extras.getInt("notificationId");
			if (id > 0) {
				NotificationManager myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				// remove the notification with the specific id
				myNotificationManager.cancel(id);
			}
		} catch (Exception ex) {
		}
	}

	private void insertAds() {
		if (LotteryUtils.isConnectInternet(this)) {
			// Create an ad.
			adView = new AdView(this);
			adView.setAdSize(AdSize.SMART_BANNER);
			adView.setAdUnitId(AD_UNIT_ID);
			LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
			layout.addView(adView, 0);
//			AdRequest adRequest = new AdRequest.Builder().setGender(AdRequest.GENDER_MALE)
//					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//					.addTestDevice("EF46L01111101079272").build();
			
			AdRequest adRequest = new AdRequest.Builder().build();
			// Start loading the ad in the background.
			adView.loadAd(adRequest);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lottery_menu, menu);
		// menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem()
		// > 0);
		// Add either a "next" or "finish" button to the action bar, depending
		// on which page
		// is currently selected.
		// MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
		// (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1) ?
		// R.string.action_finish
		// : R.string.action_next);
		// item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
		// MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	public static LotteryDBResult getLotteryDBResultByDate(int date) {
		return lotteryDataSource.getLotteryResultByDate(date);
	}

	public static void createLotteryDBResult(int date, String result) {
		lotteryDataSource.createLotteryResult(date, result);
	}
	
	public static void createOrUpdateLotteryDBResult(int date, String result) {
		lotteryDataSource.createOrUpdateLotteryDBResult(date, result);
	}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.action_previous:
		// // Go to the previous step in the wizard. If there is no previous
		// // step,
		// // setCurrentItem will do nothing.
		// mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		// return true;
		case R.id.action_next:
			// Advance to the next step in the wizard. If there is no next step,
			// setCurrentItem
			// will do nothing.
			mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			return true;
		case R.id.action_refresh:
			// mPager.setCurrentItem(mPager.getCurrentItem());
			int currentPosition = mPager.getCurrentItem();
			setSlilePagerAdapter();
			mPager.setCurrentItem(currentPosition);
			// mPagerAdapter.notifyDataSetChanged();
			return true;
		case R.id.action_search:
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getFragmentManager(), "datePicker");
			return true;
		case R.id.action_statistic:
			Intent intent = new Intent(this, StatisticActivity.class);		    
		    String message = "Hello";
		    intent.putExtra("extraMessage", message);
		    startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void clickPreviosDate(View arg0) {
		Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
		mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		invalidateOptionsMenu();
	}

	/**
	 * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment}
	 * objects, in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		LotteryResultActivity lrActivity;

		public ScreenSlidePagerAdapter(FragmentManager fm, LotteryResultActivity activity) {
			super(fm);
			lrActivity = activity;
		}

		@Override
		public Fragment getItem(int position) {
			return ScreenSlidePageFragment.create(position, getApplicationContext(), lrActivity);
		}

		@Override
		public int getCount() {
			return Constants.NUM_PAGES;
		}

		// @Override
		// public int getItemPosition(Object object) {
		// // TODO Auto-generated method stub
		// return POSITION_NONE;
		// //return super.getItemPosition(object);
		// }
	}

	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, day);
			gotoDate(cal.getTime());
			// Do something with the date chosen by the user
		}
	}
}
