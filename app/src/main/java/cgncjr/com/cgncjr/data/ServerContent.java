package cgncjr.com.cgncjr.data;

import java.util.Map;

/**
 *
 * 访问服务器信息的类（用于集中存储和处理，比如说url ,params路径和参数，以及访问方式的存储）
 * Created by Administrator on 2016/4/13.
 */
public class ServerContent {

    private String url;//路径
    private Map<String,String> params;//post传递的内容
    private boolean splicing = false;//是否需要路径拼接,默认不拼接路径
    private boolean isPost = true;//是否post访问，默认post访问
    private String splingStr;  //Constants.SERVER_URL

    public boolean isPost() {
        return isPost;
    }

    public void setIsPost(boolean isPost) {
        this.isPost = isPost;
    }

    public boolean isSplicing() {
        return splicing;
    }

    public void setSplicing(boolean splicing) {
        this.splicing = splicing;
    }

    public void setSplingStr(String splingStr) {
        this.splingStr = splingStr;
    }


    public String getUrl() {

        //判断是否需要接口拼接
        if(splicing){
            //接口需要拼接,返回拼接后的路径
            return splingStr+ url;
        }else{
            //接口不需要拼接，返回接收到的访问路径
            return url;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String,String> getParams() {
        return params;
    }

    public void setParams(Map<String,String> params) {
        this.params = params;
    }

}
