package cgncjr.com.cgncjr.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/4/13.
 */
public class Constants {
    public static final String ENCONDING = "UTF-8";
    public static final int HANDLER_RL_START = 1;//handler判断值，下载图片成功返回值
    public static final boolean ISTEST = true;//是否为测试环境
    public static final String version = "1.0.0";
    public static final String version_ = "100";
    public static final String PATH = Environment.getExternalStorageDirectory()
            + File.separator + Constants.COMPANY_FILEDIR + File.separator + "download" +
            File.separator;
    public static final String COMPANY_FILEDIR = "com.cgtz";//用于保存app下载的文件根目录
    public static final String IMG_FILE_NAME = "loading" + version_ + ".jpg";
    //下载的欢迎图片，保存路径
    public static final String IMG_FILE_PATH = PATH + IMG_FILE_NAME;

    public static final String REFRE_START_IMAGE = "start_image_download";//启动页面图片下载
    public static final String URL_API_STARTUP = "api/startUp ";//启动新接口，获取内容
    public static final boolean is28 = false;
    public static final String ClientHTTP2 = "http://115.29.245.28:42111/tz" + version_ + "/";//28测试
    public static final String ClientHTTP1 = "http://172.16.34.188:45680/phpapi/";//dev测试服务器
    public static final String ClientHTTP = is28 ? ClientHTTP2 : ClientHTTP1;//28测试
    public static final String HOST_ONLINE_HTTPS = "https://d5ds88.cgtz.com/tz" + version_ + "/";//线上服务器
    public static final String SERVER_URL = ISTEST ? ClientHTTP : HOST_ONLINE_HTTPS;//访问服务器地址
    //public static final String order_from = "version=" + version + "&mobileOS=android&";//向服务器发送版本号
    public static final String order_from = "type=android&"+ "version=" + version +"&";//向服务器发送版本号
    public static final int HANDLER_API_STARTIMAGE = 2014112510;//获取开机图片handler回调判断值
    public static final String URL_API_STARTIMAGE = "api/newStartImage";//获取开机图片
    public static final String UTF8 = "UTF-8";//字符编码


    public static final String version_url1 = "http://172.16.34.188:45680/version/app/cgjrVersion";//dev测试服务器,判断是否更新
    public static final String version_url2 = "http://115.29.245.28:42111/version/app/cgjrVersion";//28测试,判断是否更新
    public static final String version_url = is28 ? version_url2 : version_url1;//28测试,判断是否更新
    public static final String version_url_online = "https://d5ds88.cgtz.com/version/app/cgjrVersion";//线上服务器，,判断是否更新


    public static final int HANDLER_SERVER_MAINTAIN = 2015011914;//服务器维护判断值
    public static final String URL_SERVER_MAINTAIN = "android/Maintain";//服务器是否维护的判断接口
    /**
     * 保存内容的key值
     **/
    public static final String LASTUPDATE = "lastUpdate";//服务器端欢迎图片的替换时间
    public static final int BOOLEAN = 1;
    public static final int FLOAT = 2;
    public static final int INT = 3;
    public static final int LONG = 4;
    public static final int STRING = 5;
    public static final int STRINGSET = 6;

    public static final String WALLET_INVEST = "http://m.cgtz.com";//投资H5界面 草根投资/精选投资
    public static final String WALLET_LOAN="https://m.cgjr.com";//借款H5界面，草根投资/精选借款
    public static final String WALLET_LOAN_INDEX="https://m.cgjr.com/?xd=android";
    public static final String WALLET_LOAN_INDEX_TEST="http://test.m.cgjr.com/?environment=app";
    public static final String WALLET_LOAN_TEST="http://test.m.cgjr.com";//用来测试自动登录
    public static final String WALLET_INVEST_WX = "http://m.cgtz.com/?weixin=weixin";//投资H5界面 草根投资/精选投资



}
