package com.alandk.xosomienbac.activity;

import com.example.xosomienbac.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	    startActivity(intent);

	}

	private void showThongkeLogan() {
		Intent intent = new Intent(this, ThongkeLoganActivity.class);		    
	    startActivity(intent);
	}

}
