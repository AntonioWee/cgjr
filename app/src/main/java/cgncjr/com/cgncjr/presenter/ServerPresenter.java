package cgncjr.com.cgncjr.presenter;

import android.content.Context;

import cgncjr.com.cgncjr.data.ServerContent;
import cgncjr.com.cgncjr.data.ServerResponeEntity;
import cgncjr.com.cgncjr.model.IServerRespone;
import cgncjr.com.cgncjr.model.IsNetConnect;
import cgncjr.com.cgncjr.model.impl.CustomTask;
import cgncjr.com.cgncjr.model.impl.NetConnect;


/**
 * 服务器交互管理器
 * Created by Administrator on 2016/4/13.
 */
public class ServerPresenter {

    private IServerRespone serverRespone;
    private IsNetConnect netConnect;
    private ServerResponeEntity entity;
    private ServerContent serverContent;
    public ServerPresenter() {
        entity = new ServerResponeEntity();
        netConnect = new NetConnect();
    }

    public ServerPresenter(IServerRespone serverRespone, ServerContent serverContent) {
        entity = new ServerResponeEntity();
        this.serverRespone = serverRespone;
        this.serverContent = serverContent;
        netConnect = new NetConnect();
    }

    public void setServer(IServerRespone serverRespone, ServerContent serverContent) {
        this.serverRespone = serverRespone;
        this.serverContent = serverContent;
    }

    public void didGetServerData(Context context) {
        if (netConnect.isNetConnect(context)) {
            //网络可用，进行网络访问操作
            CustomTask task = new CustomTask(serverRespone, serverContent);
            task.execute();
        } else {
            //网络不可用
            entity.setUrl(serverContent.getUrl());
            entity.setCode(ServerResponeEntity.CODE_NO_NETWORK);//没有网络code判断值
            entity.setErrorMsg(ServerResponeEntity.ERROR_NO_NETWORK);//没有网络时的错误信息
            serverRespone.OnErrorRespone(entity);
        }
    }



}
