package cgncjr.com.cgncjr.fragment;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import cgncjr.com.cgncjr.R;
import cgncjr.com.cgncjr.constants.Constants;


/**
 * Created by Administrator on 2016/4/27.
 */
public class MainFragment extends BaseFragment{
    private String TAG = "MainFragment";

    public WebView webView;
    private TextView pb;
    private int Progress;
    private int width;
    private ViewGroup.LayoutParams lp;



    private String urlLoanIndex= Constants.WALLET_LOAN_INDEX;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_webview, container, false);
        webView = (WebView) view.findViewById(R.id.webview);
        String mUserAgent =webView.getSettings().getUserAgentString();
        //设置可以访问文件
        webView.getSettings().setAllowFileAccess(true);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        webView.getSettings().setUserAgentString(mUserAgent);
        webView.getSettings().setJavaScriptEnabled(true);//可用JS
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //添加手机缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);//出现缩放工具
        webView.getSettings().setUseWideViewPort(true);//扩大比例缩放
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
        webView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        pb = (TextView) view.findViewById(R.id.progress_bar_2);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {

            if (0 != (getActivity().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE))

            { WebView.setWebContentsDebuggingEnabled(true);}

        }
        webView.loadUrl(urlLoanIndex);
        return view;

    }



}
