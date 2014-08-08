package com.alandk.xosomienbac.activity;

import com.alandk.xosomienbac.R;
import com.alandk.xosomienbac.common.Constants;
import com.alandk.xosomienbac.common.LotteryUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatisticActivity extends Activity {

	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistic_main);
		insertAds();

		TextView previousDate = (TextView) findViewById(R.id.thongkelogan);
		previousDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				showThongkeLogan();
				// finishActivity(0);
				// finish();
			}
		});

		TextView nextDate = (TextView) findViewById(R.id.thongkeitnhieu);
		nextDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				showThongkeItnhieu();
			}
		});
	}

	private void showThongkeItnhieu() {
		Intent intent = new Intent(this, ThongkeItnhieuActivity.class);
		startActivityForResult(intent, 0);
	}

	private void showThongkeLogan() {
		Intent intent = new Intent(this, ThongkeLoganActivity.class);
		startActivityForResult(intent, 0);
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

	private void insertAds() {
		if (LotteryUtils.isConnectInternet(this)) {
			// Create an ad.
			adView = new AdView(this);
			adView.setAdSize(AdSize.SMART_BANNER);
			adView.setAdUnitId(Constants.AD_UNIT_ID);
			LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutThongke);
			layout.addView(adView, 0);
			// AdRequest adRequest = new
			// AdRequest.Builder().setGender(AdRequest.GENDER_MALE)
			// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
			// .addTestDevice("EF46L01111101079272").build();
			AdRequest adRequest = LotteryUtils.getAdRequest();
			// Start loading the ad in the background.
			adView.loadAd(adRequest);
		}
	}

	

}
