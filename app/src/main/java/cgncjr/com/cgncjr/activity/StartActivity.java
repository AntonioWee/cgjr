package cgncjr.com.cgncjr.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import cgncjr.com.cgncjr.R;
import cgncjr.com.cgncjr.constants.Constants;
import cgncjr.com.cgncjr.lib.ChangeLogHelper;
import cgncjr.com.cgncjr.service.DownloadPicService;
import cgncjr.com.cgncjr.utils.CustomTask;
import cgncjr.com.cgncjr.utils.DebugUtils;
import cgncjr.com.cgncjr.utils.HttpUtils;
import cgncjr.com.cgncjr.utils.UtilityUtils;
import cgncjr.com.cgncjr.view.ServerMainTainDialog;


/**
 * Created by Administrator on 2016/4/13.
 */
public class StartActivity extends Activity{

    public static final String TAG="StartActivity";
    private File file = new File(Constants.IMG_FILE_PATH);
    private long timerSpace = 1500;

    @Bind(R.id.ll_start)
    LinearLayout ll_start;
    @Bind(R.id.iv_start)
    ImageView iv_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        if (!UtilityUtils.isNewWorkConnected(this)) {
            Intent intent = new Intent(this, ChoiceEnterActivity.class);
            startActivity(intent);
        }else{
            //if (file.exists()) {
//                DebugUtils.i("获取开机图片", "文件存在");
//                Bitmap bitmap = ImageOperUtil.readFileAndOptions(Constants.IMG_FILE_PATH);
//                if (bitmap != null) {
//                    iv_start.setImageDrawable(new BitmapDrawable(bitmap));
//                    setAlpha(iv_start);
//                }
            //}else {
                iv_start.setImageResource(R.drawable.loading_cgjr);
                iv_start.setScaleType(ImageView.ScaleType.FIT_XY);
                setAlpha(iv_start);
            //}
        }
        VersionTask task = new VersionTask(this);
        task.execute();
       // getonLine();
       // SwitchPagerTimerTask  timerTask=new SwitchPagerTimerTask();
     //  Handler timerHandler = new Handler();
//        timerHandler.postDelayed(timerTask, timerSpace);
    }

    public class ViewHolder {
        @Bind(R.id.update_version)
        TextView update_version;
        @Bind(R.id.update_version_size)
        TextView update_version_size;
        @Bind(R.id.update_notice)
        TextView update_notice;
        @Bind(R.id.update_tip_button)
        TextView update_tip_button;
        @Bind(R.id.update_button_cancel)
        TextView update_button_cancel;
        @Bind(R.id.update_center_line)
        View update_center_line;

        public ViewHolder(View targetView) {
            ButterKnife.bind(this, targetView);
        }
    }

    private SwitchPagerTimerTask  timerTask;
    private Handler timerHandler;

    private void getonLine() {
        if (MainActivity.IS_SHOW) {
            finish();
            return;
        }
       // startService(new Intent(StartActivity.this, DownloadPicService.class));
        timerTask = new SwitchPagerTimerTask();
        timerHandler = new Handler();
        timerHandler.postDelayed(timerTask, timerSpace);
    }
    private Handler startHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.HANDLER_SERVER_MAINTAIN:
                    //服务器维护
                    try {
                        DebugUtils.i(TAG, "服务器维护：" + msg.obj.toString());
                        if (!TextUtils.isEmpty(msg.obj.toString())) {
                            JSONObject json = new JSONObject(msg.obj.toString());
                            int maintain = json.optInt("maintain");//服务器是否维护的判断值  1，维护中，0，没有维护
                            if (maintain == 1) {
                                //服务器维护中,显示服务器维护中图片
                                final ServerMainTainDialog maintainDialog =
                                        ServerMainTainDialog.getInstans(StartActivity.this);
                                maintainDialog.setCancelable(false);
                                maintainDialog.setCanceledOnTouchOutside(false);
                                maintainDialog.withMaintainIconClick(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        maintainDialog.dismiss();
                                        finish();
                                    }
                                });
                                maintainDialog.show();
                            } else if (maintain == 0) {
                                //服务器没有维护，正常显示访问服务器超时
                                UtilityUtils.makeText(StartActivity.this, getString(R.string.error_http_faild));
                                start();
                            } else {
                                getonLine();
                            }
                        } else {
                            //服务器没有维护，正常显示访问服务器超时
                            UtilityUtils.makeText(StartActivity.this, getString(R.string.error_http_faild));
                            start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        UtilityUtils.saveExceptionInfo(e);
                        DebugUtils.e(TAG, "返回数据错误了");
                        UtilityUtils.makeText(StartActivity.this, getString(R.string.error_page_hint));
                        start();
                    }
                    break;
            }
        }
    };

    /**
     * 用于延迟跳转到网络未连接提示页面
     */
    private void start() {
        TimerTask task = new TimerTask() {
            public void run() {
                startActivity(new Intent(StartActivity.this, ChoiceEnterActivity.class));
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }

    /**
     * 判断是否需要更新
     */
    class VersionTask extends AsyncTask<String, Void, String> {
        private Context context;

        public VersionTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String result;
            String content = "";
           // Map<String,String> maps=new HashMap<>();
           // maps.put("version",UtilityUtils.getVersion(context));
            //maps.put("type","android");
            //content = HttpUtils.getRequestData(maps,"UTF-8").toString();
            content = HttpUtils.getConstansData().toString();
            if (Constants.ISTEST) {
                //result = HttpUtils.HttpGet(Constants.version_url + "?version=" + UtilityUtils.getVersion(context)
                        //+ "&type=android", "UTF-8");
                result=HttpUtils.HttpPost(Constants.version_url_online,content,"UTF-8");

            } else {
               // result = HttpUtils.HttpsGet(Constants.version_url_online + "?version=" + UtilityUtils.getVersion(context)
                       // + "&type=android", "UTF-8");
                result=HttpUtils.HttpsPost(Constants.version_url_online, content, "UTF-8");
            }
            return result;
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result == null) {
                    getonLine();
                    return;
                }
                DebugUtils.i("startActivity", "版本号返回值：" + result);
                if (result.equals("")) {
                    UtilityUtils.makeText(StartActivity.this, getString(R.string.error_page_hint));
                    finish();
                } else if (result.equals("faild")) {

                    CustomTask task = new CustomTask(startHandler, Constants.HANDLER_SERVER_MAINTAIN,
                            Constants.URL_SERVER_MAINTAIN, true, null, Constants.UTF8);
                    task.execute();
                } else {
                    //返回值不为空和失败faild的情况下
                    JSONObject json = new JSONObject(result);
                    //在这里只是判断一下，就木有else了。
                    if (json != null) {
                        String success = json.optString("success");
                        if (success.equals("1")) {   //判断成功的标识符，包括：  1。如果不是 1，则改判断 action中存储的值，如果是maintain,则执行相应的动作。
                            String code = json.optString("code");
                             //code--->info-->{info.downurl,info.notice,info.version}--->这些信息用来填充dialog。
                            //code--->forceUpgrade--->用来判断是否有取消更新的按钮。
                            //以上都是来决定dialog的显示和样式的。
                            if (code.equals("200")) {
                                JSONObject obj = json.optJSONObject("info");
                                int forceUpgrade = json.optInt("forceUpgrade");//判断是否强制升级
                                if (obj == null) {
                                    return;
                                }
                                final String downurl = obj.optString("downurl");//获取下载路径
                                String notice = obj.optString("notice");//获取最新版本更新的内容
                                String version = obj.optString("version");//获取改变的版本号
                                if (TextUtils.isEmpty(downurl)
                                        || TextUtils.isEmpty(notice)
                                        || TextUtils.isEmpty(version)) {
                                    return;
                                }
                                LinearLayout dialogLayout =
                                        (LinearLayout) LayoutInflater.from(StartActivity.this)
                                                .inflate(R.layout.update_tip, null);
                                final Dialog mDialog =
                                        new Dialog(StartActivity.this, R.style.loading_dialog2);
                                mDialog.setContentView(dialogLayout);
                                mDialog.setCanceledOnTouchOutside(false);
                                mDialog.setCancelable(false);
                                ViewHolder vh = new ViewHolder(dialogLayout);
                                if (forceUpgrade == 1) {
                                    //强制升级
                                    DebugUtils.i(TAG, "强制升级");
                                    vh.update_button_cancel.setVisibility(View.GONE);
                                    vh.update_center_line.setVisibility(View.GONE);
                                } else if (forceUpgrade == 0) {
                                    //不强制升级
                                    DebugUtils.i(TAG, "不强制升级");
                                    vh.update_button_cancel.setVisibility(View.VISIBLE);
                                    vh.update_center_line.setVisibility(View.VISIBLE);
                                }
                                if (forceUpgrade != 1) {
                                    startService(new Intent(StartActivity.this, DownloadPicService.class));
                                }
                                vh.update_version.setText("最新版本：" + version);
                                vh.update_version_size.setText("当前版本：" + UtilityUtils.getVersion(StartActivity.this));
                                vh.update_notice.setText(Html.fromHtml(notice));
                                mDialog.show();
                                vh.update_tip_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Uri uri = Uri.parse(downurl);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);
                                        finish();
                                        mDialog.dismiss();
                                    }
                                });

                                vh.update_button_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getonLine();
                                        mDialog.dismiss();
                                    }
                                });
                            } else {
                                getonLine();
                            }
                        } else if (json.optString("action").equals("maintain")) {//前面判断正确，则这个不会执行，这就是优势
                            //系统维护中，显示出一个dialog出来
                            final ServerMainTainDialog maintainDialog =
                                    ServerMainTainDialog.getInstans(StartActivity.this);
                            maintainDialog.setCancelable(false);
                            maintainDialog.setCanceledOnTouchOutside(false);
                            maintainDialog.withMaintainIconClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    maintainDialog.dismiss();
                                    System.exit(0);
                                }
                            });
                            maintainDialog.show();
                        } else {
                            getonLine();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                UtilityUtils.saveExceptionInfo(e);
                DebugUtils.e(TAG, "返回数据错误了");
                UtilityUtils.makeText(StartActivity.this, getString(R.string.error_page_hint));
                finish();
            }
        }
    }


    private void setAlpha(View view) {
        AlphaAnimation mHideAnimation = new AlphaAnimation(0.0f, 1.0f);//设置从不见到见
        mHideAnimation.setDuration(2000);//设置渐现时间
        mHideAnimation.setFillAfter(true);//设置保留动画完成时的样子
        view.startAnimation(mHideAnimation);//启动动画
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    TurnHandler turnHandler=new TurnHandler();
    class SwitchPagerTimerTask implements Runnable {
        @Override
        public void run() {
            turnHandler.sendEmptyMessage(1);
        }
    }

    class TurnHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                //第一次的时候，里面没有储存判断的值，所有是不相等的，然后就执行判断的内容。
                if (!ChangeLogHelper.isTheSameVersion(StartActivity.this)) {
                    Intent intent = new Intent(StartActivity.this, ScreenSlideActivity.class);
                    intent.putExtra(ScreenSlideActivity.SCREEN_SLIDE_ACTIVITY_INTENT_PARAM_ISFIRSTOPEN, true);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(StartActivity.this, ChoiceEnterActivity.class));
                }
                finish();
            }
        }
    }


}
