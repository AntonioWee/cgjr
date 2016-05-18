package cgncjr.com.cgncjr.model.impl;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cgncjr.com.cgncjr.model.IsNetConnect;
import cgncjr.com.cgncjr.utils.UtilityUtils;



/**
 * Created by Administrator on 2016/4/13.
 */
public class NetConnect implements IsNetConnect {

    @Override
    public boolean isNetConnect(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    @Override
    public boolean unNetConnect(Activity activity) {
        boolean netConnect = this.isNetConnect(activity);
        if (!netConnect) {
            UtilityUtils.makeText_short(activity, "网络未开启");
            activity.finish();
            return true;
        }
        return false;
    }

}
