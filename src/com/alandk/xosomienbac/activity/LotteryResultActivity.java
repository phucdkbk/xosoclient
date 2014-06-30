package com.alandk.xosomienbac.activity;

import com.alandk.xosomienbac.activity.ScreenSlidePageFragment;
import com.alandk.xosomienbac.database.LotteryDBResult;
import com.alandk.xosomienbac.database.LotteryDataSource;
import com.alandk.xosomienbac.sync.AlarmReceiver;
//import com.alandk.xosomienbac.ScreenSlideActivity.ScreenSlidePagerAdapter;
import com.example.xosomienbac.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class LotteryResultActivity extends Activity {

	private static LotteryDataSource lotteryDataSource;

	public LotteryResultActivity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * The number of pages (wizard steps) to show in this demo.
	 */
	public static final int NUM_PAGES = 100;

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;
	
	private void testAlamManager(Context context){
		Intent alarmIntent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				alarmIntent, 0);

		AlarmManager manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		int interval = 2000; // 10 seconds

		manager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), interval, pendingIntent);
		Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_slide);
		lotteryDataSource = new LotteryDataSource(this);
		lotteryDataSource.open();

		
		testAlamManager(this);		
		
		// test download result
		// textView = (TextView) findViewById(R.id.myText);
		// new

		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
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
		mPager.setCurrentItem(NUM_PAGES/2, true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

		menu.findItem(R.id.action_previous).setEnabled(
				mPager.getCurrentItem() > 0);

		// Add either a "next" or "finish" button to the action bar, depending
		// on which page
		// is currently selected.
		MenuItem item = menu
				.add(Menu.NONE,
						R.id.action_next,
						Menu.NONE,
						(mPager.getCurrentItem() == mPagerAdapter.getCount() - 1) ? R.string.action_finish
								: R.string.action_next);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	public static LotteryDBResult getLotteryDBResultByDate(int date) {
		return lotteryDataSource.getAllLotteryResultByDate(date);
	}

	public static void createLotteryDBResult(int date, String result) {
		lotteryDataSource.createLotteryResult(date, result);
	}

	public boolean isConnectInternet() {
		ConnectivityManager connMgr = (ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_previous:
			// Go to the previous step in the wizard. If there is no previous
			// step,
			// setCurrentItem will do nothing.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			return true;

		case R.id.action_next:
			// Advance to the next step in the wizard. If there is no next step,
			// setCurrentItem
			// will do nothing.
			mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment}
	 * objects, in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ScreenSlidePageFragment.create(position, getApplicationContext());
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

}
