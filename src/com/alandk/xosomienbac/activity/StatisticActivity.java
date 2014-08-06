package com.alandk.xosomienbac.activity;

import com.alandk.xosomienbac.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class StatisticActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistic_main);

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

}
