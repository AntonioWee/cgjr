package cgncjr.com.cgncjr.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cgncjr.com.cgncjr.R;
import cgncjr.com.cgncjr.constants.Constants;
import cgncjr.com.cgncjr.fragment.MainFragment;
import cgncjr.com.cgncjr.utils.LogUtils;
import cgncjr.com.cgncjr.utils.ScreenUtils;


public class MainActivity extends FragmentActivity {

    private String TAG = "MainActivity";
    public static boolean IS_SHOW = false;

    private String urlLoanIndex= Constants.WALLET_LOAN_INDEX;
    private String urlLoanTest= Constants.WALLET_LOAN_TEST;
    MainFragment mainFragment;
    @Bind(R.id.webview)
    WebView  webView;
    @Bind(R.id.progress_bar_2)
    TextView pb;
    private ViewGroup.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initMyWebView();
    }

    private void initMyWebView() {
        //设置可以访问文件
        webView.getSettings().setAllowFileAccess(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        String mUserAgent =webView.getSettings().getUserAgentString();
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
            if (0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE))
            { WebView.setWebContentsDebuggingEnabled(true);}
        }
        setClient();
        webView.loadUrl(urlLoanIndex);

    }

    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private int Progress;
    private int width;

    private void setClient() {
        lp = pb.getLayoutParams();
        width = ScreenUtils.getScreenWidth(this);
        webView.setWebChromeClient(new WebChromeClient() {
            //用于修改进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Progress = newProgress;
                if (newProgress < 100) {
                    lp.width = width * Progress;
                    pb.setLayoutParams(lp);
                } else {
                    pb.setVisibility(View.GONE);
                }
            }


        });
        webView.setWebViewClient(new WebViewClient() {
            //重写点击动作,用webview载入
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                try {
                    Uri uri = Uri.parse(url);
                    if ("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme())) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("Referer", view.getUrl());
                        LogUtils.i(TAG, "访问了服务器");
                        view.loadUrl(url, map); //载入网页
                        return true;
                    }
                } catch (Exception e) {

                }
                return true;
            }

            @Override
            public void onReceivedSslError(final WebView view, SslErrorHandler handler,
                                           SslError error) {
                handler.proceed();
            }


        });
        webView.setWebChromeClient(new WebChromeClient() {


            // For Android 3.0-
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooserImpl(uploadMsg);
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooserImpl(uploadMsg);

            }

            // For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooserImpl(uploadMsg);
            }

            // For Android > 5.0
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
                openFileChooserImplForAndroid5(uploadMsg);
                return true;
            }


        });


    }
    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "请选择图片");
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode !=RESULT_OK ? null : intent.getData();
            if (result!=null){

                String filePath;

                if ("content".equals(result.getScheme())) {

                    Cursor cursor = getContentResolver().query(result, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
                    cursor.moveToFirst();
                    filePath = cursor.getString(0);
                    cursor.close();
                    System.out.println(filePath);

                } else {
                    filePath = result.getPath();
                    System.out.println(filePath);

                }

                Uri myUri = Uri.parse(filePath);
                System.out.println(myUri);
                mUploadMessage.onReceiveValue(myUri);

            } else {
                mUploadMessage.onReceiveValue(result);

            }
            mUploadMessage = null;
        }else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5){
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null: intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }


    long waitTime = 2000;
    long touchTime = 0;

    @Override
    public void onBackPressed() {

        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            finish();
        }
    }


}
