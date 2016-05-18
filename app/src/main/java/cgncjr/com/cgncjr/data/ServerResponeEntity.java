package cgncjr.com.cgncjr.data;

/**
 * Created by Administrator on 2016/4/13.
 * 服务器返回的数据类（用来初步解析服务器传递过来的信息，判断出大致是什么样的或者说是什么类型的信息）
 */
public class ServerResponeEntity {

    public static final String ERROR_NO_NETWORK = "网络未开启";
    public static final String ERROR_NO_CONTENT = "网络不给力";//没有数据内容提示
    public static final String ERROR_FAIL = "网络缓慢，请稍后尝试";//访问失败，错误
    public static final int CODE_NO_NETWORK = -1;//没有网络code判断值
    public static final int CODE_NO_CONTENT = 0;//没有内容或者内容为null
    public static final int CODE_FAIL = 1;//访问服务器错误,失败
    public static final int CODE_SUCCESS = 2;//数据获取成功
    private int code;//数据类型
    private String url;//访问的路径
    private String respone;//数据内容
    private String errorMsg;//错误信息

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRespone() {
        return respone;
    }

    public void setRespone(String respone) {
        this.respone = respone;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
