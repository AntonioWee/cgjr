package cgncjr.com.cgncjr.model.impl;

import android.content.Context;

import cgncjr.com.cgncjr.CGApplication;
import cgncjr.com.cgncjr.constants.Constants;
import cgncjr.com.cgncjr.data.ServerContent;
import cgncjr.com.cgncjr.model.IServerRespone;
import cgncjr.com.cgncjr.presenter.ServerPresenter;


/**
 * Created by Administrator on 2016/4/13.
 */
public class InterfaceImp  {

    private static final String TAG = "InterfaceImp";

    /**
     * 获取启动图最新更新的时间
     *
     * @param context
     * @param serverRespone
     */
    public static void getStatUp(Context context, IServerRespone serverRespone) {
        ServerContent serverContent = getServerContent();

        //这样处理，ServerPresenter只在application启动的时候创建一次
        ServerPresenter presenter = CGApplication.getApp().getPresenter();
        presenter.setServer(serverRespone, serverContent);
        presenter.didGetServerData(context);
    }

    public static ServerContent getServerContent() {
        ServerContent serverContent = new ServerContent();
        serverContent.setIsPost(true);
        serverContent.setSplicing(true);
        serverContent.setUrl(Constants.URL_API_STARTUP);
        serverContent.setSplingStr(Constants.SERVER_URL);
        return serverContent;
    }
}
