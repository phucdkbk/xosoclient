package com.alandk.xosomienbac.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class LotteryUtils {
	
	public static boolean isConnectInternet(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

}
