package cgncjr.com.cgncjr.model;


import cgncjr.com.cgncjr.data.ServerResponeEntity;

/**
 * Created by Administrator on 2016/4/13.
 * 服务器交互接口
 */

public interface IServerRespone {
    void OnRespone(ServerResponeEntity entity);
    void OnErrorRespone(ServerResponeEntity entity);
    void OnAntherOperation();
}

