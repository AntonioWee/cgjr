package cgncjr.com.cgncjr;

import android.app.Application;
import android.app.Service;

import cgncjr.com.cgncjr.presenter.ServerPresenter;
import cgncjr.com.cgncjr.utils.LogUtils;


/**
 * Created by Administrator on 2016/4/13.
 */
public class CGApplication extends Application{

    public static final String TAG="CGApplication";
    private Service picService;//存储打开的图片下载服务
    private static CGApplication instance;
    private ServerPresenter presenter = new ServerPresenter();
    private int netWorkStatus = 1;
    private String userId;//登录后的userId

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i(TAG, "create app");
        instance = this;

    }

    public void setPicService(Service picService) {
        this.picService = picService;
    }

    public void removePicService(){
        this.picService = null;
    }

    public static CGApplication getApp() {
        return instance;
    }

    public ServerPresenter getPresenter(){
        return presenter;
    }

    public int getNetWorkStatus() {
        return netWorkStatus;
    }

    public void setNetWorkStatus(int netWorkStatus) {
        this.netWorkStatus = netWorkStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
