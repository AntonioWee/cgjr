package cgncjr.com.cgncjr.model.impl;

import android.os.AsyncTask;
import android.text.TextUtils;

import cgncjr.com.cgncjr.constants.Constants;
import cgncjr.com.cgncjr.data.ServerContent;
import cgncjr.com.cgncjr.data.ServerResponeEntity;
import cgncjr.com.cgncjr.model.IServerRespone;
import cgncjr.com.cgncjr.utils.HttpUtils;
import cgncjr.com.cgncjr.utils.LogUtils;


/**
 * Created by Administrator on 2016/4/13.
 */
public class CustomTask extends AsyncTask<String, Void, String> {
    private String url_;//访问路径
    private boolean flag;//判断get或者post方法  get为false,post为true
    private String content;//post传递的参数
    private String encode = Constants.ENCONDING;//编码
    private IServerRespone serverRespone;
    private static final String TAG = "CustomTask";

    /**
     * @param serverRespone 回调接口
     * @param serverContent 所需数据
     */
    public CustomTask(IServerRespone serverRespone, ServerContent serverContent) {
        this.serverRespone = serverRespone;
        this.flag = serverContent.isPost();
        this.url_ = serverContent.getUrl();
        if (flag) {
            content = HttpUtils.getRequestData(serverContent.getParams(), encode).toString();
        }
        LogUtils.i(TAG, "curl -d '" + content + "' '" + this.url_ + "'");
    }

    @Override
    protected String doInBackground(String... params) {
        if (flag) {
            if (Constants.ISTEST) {
                return HttpUtils.HttpPost(url_, content, encode);
            } else {
                return HttpUtils.HttpsPost(url_, content, encode);
            }
        } else {
            if (Constants.ISTEST) {
                return HttpUtils.HttpGet(url_, encode);
            } else {
                return HttpUtils.HttpsGet(url_, encode);
            }
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ServerResponeEntity entity = new ServerResponeEntity();
        entity.setUrl(url_);
        if (TextUtils.isEmpty(s)) {
            entity.setCode(ServerResponeEntity.CODE_NO_CONTENT);
            entity.setErrorMsg(ServerResponeEntity.ERROR_NO_CONTENT);
            LogUtils.i(TAG, "结果：" + url_ + ": " + s);
            serverRespone.OnErrorRespone(entity);
        } else if (s.equals("faild")) {
            entity.setCode(ServerResponeEntity.CODE_FAIL);
            entity.setErrorMsg(ServerResponeEntity.ERROR_FAIL);
            LogUtils.i(TAG, "结果：" + url_ + ": " + s);
            serverRespone.OnErrorRespone(entity);
        } else {
            entity.setCode(ServerResponeEntity.CODE_SUCCESS);
            entity.setRespone(s);
            LogUtils.i(TAG, "结果：" + url_ + ": " + s);
            serverRespone.OnRespone(entity);
        }
    }

}
