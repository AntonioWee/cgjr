package cgncjr.com.cgncjr.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cgncjr.com.cgncjr.CGApplication;
import cgncjr.com.cgncjr.constants.Constants;
import cgncjr.com.cgncjr.data.ServerResponeEntity;
import cgncjr.com.cgncjr.model.impl.InterfaceImp;
import cgncjr.com.cgncjr.model.impl.ServerRespone;
import cgncjr.com.cgncjr.utils.CustomTask;
import cgncjr.com.cgncjr.utils.DownloadingTask;
import cgncjr.com.cgncjr.utils.LogUtils;
import cgncjr.com.cgncjr.utils.UtilitySharedpreferences;
import cgncjr.com.cgncjr.utils.UtilityUtils;


/**
 * Created by Administrator on 2016/4/13.
 */
public class DownloadPicService extends Service {
    private static final String TAG = "DownloadPicService";
    private String downLoadTime;
    private String lastUpdateTime;
    private File file = new File(Constants.IMG_FILE_PATH);
    private boolean startImg = false;//判断启动图片是否下载成功
    private boolean startImgEnd = true;//下载启动图片线程是否结束
    private static final int THREAD_END = 10010;//两个线程是否全部结束
    private static final int THREAD_NUM = 10086;//用来判断开启的所有线程动作是否完成
    private int num = 0;//用来计算开启了几个线程动作
    private int count;
    private UtilitySharedpreferences usp;
    private String img_url;




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i(TAG, "进入onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        usp = new UtilitySharedpreferences(this, Constants.REFRE_START_IMAGE);
        lastUpdateTime = (String) usp.getMsg(Constants.STRING, Constants.LASTUPDATE, "");
        getStartUp();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 新接口，获取是否更新启动图
     */
    private void getStartUp() {
        if (mHandler.hasMessages(0)) {
            mHandler.removeMessages(0);
        }
        mHandler.sendEmptyMessage(0);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){

            InterfaceImp.getStatUp(DownloadPicService.this, new ServerRespone() {

                @Override
                public void OnRespone(ServerResponeEntity entity) {
                    super.OnRespone(entity);
                    try {
                        JSONObject json = UtilityUtils.parseObj(DownloadPicService.this, entity.getRespone());
                        if (json != null) {
                            downLoadTime = json.optString("downLoadTime");
                            if (!lastUpdateTime.equals(downLoadTime)) {
                                getStartImage();//获取启动图
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnErrorRespone(ServerResponeEntity entity) {
                    super.OnErrorRespone(entity);
                    LogUtils.d(TAG, "接口信息获取失败");
                    stopSelf(count);
                    CGApplication.getApp().removePicService();
                }
            });


        }


    };

    /**
     * 获取启动图
     */
    private void getStartImage() {
        num += 1;//线程动作加 1
        CustomTask task = new CustomTask(handler, Constants.HANDLER_API_STARTIMAGE,
                Constants.URL_API_STARTIMAGE, true, null, Constants.UTF8);
        task.execute();
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.HANDLER_API_STARTIMAGE://访问api/image结果判断
                    try {
                        if (TextUtils.isEmpty(msg.obj.toString())) {
                            return;
                        }
                        LogUtils.i(TAG, "访问api/image结果判断: " + msg.obj.toString());
                        JSONObject json = new JSONObject(msg.obj.toString());
                        if (json != null && json.optInt("success") == 1) {
                            img_url = json.optString("url");//启动图片的路径
                            downloadingAndSave(img_url);//下载启动图
                        }
                        handler.sendEmptyMessage(THREAD_NUM);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        UtilityUtils.saveExceptionInfo(e);
                        handler.sendEmptyMessage(THREAD_NUM);
                    }

                    break;
                case Constants.HANDLER_RL_START://下载开机图片到本地结果判断
                    boolean downFlag = (boolean) msg.obj;
                    if (downFlag) {
                        if (file.exists()) {
                            startImg = true;
                            LogUtils.i(TAG, "下载开机图片到本地结果判断: " + msg.obj.toString());
                        }
                    }else {
                        startImg = false;
                    }
                    startImgEnd = true;//线程结束
                    handler.sendEmptyMessage(THREAD_END);
                    break;
                case THREAD_END://判断下载图片的线程是否全部结束
                    if (startImg){
                        usp.putMsg(Constants.STRING, Constants.LASTUPDATE, downLoadTime);
                        handler.sendEmptyMessage(THREAD_NUM);

                    }else if(startImgEnd){
                        handler.sendEmptyMessage(THREAD_NUM);


                    }


                    break;

                case THREAD_NUM:
                    if (num >= 1) {
                        num--;
                        if (num < 1) {
                            stopSelf(count);
                            CGApplication.getApp().removePicService();
                        }
                    } else {
                        stopSelf(count);
                        CGApplication.getApp().removePicService();
                    }

                    break;
            }
        }
    };

    /**
     * 下载图片并保存
     */
    private void downloadingAndSave(String url) {
        num += 1;//线程动作增加  1
        //判断内存卡是否存在
        if (UtilityUtils.isHaveSD()) {
            LogUtils.i(TAG, "内存卡存在,更新图片");
            startImgEnd = false;
            DownloadingTask downTask = new DownloadingTask(
                    Constants.COMPANY_FILEDIR + "/download", Constants.IMG_FILE_NAME, url, handler);
            downTask.execute();
        }
    }




}
