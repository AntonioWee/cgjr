package cgncjr.com.cgncjr.data;

import android.os.Build;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.Date;

import cgncjr.com.cgncjr.CGApplication;
import cgncjr.com.cgncjr.constants.Constants;
import cgncjr.com.cgncjr.utils.DateUtil;


/**
 * 程序错误类
 * Created by Administrator on 2016/4/13.
 */
public class ErrorMsg extends DataSupport {
    public ErrorMsg() {
    }

    public ErrorMsg(CGApplication cgApp) {
        setNet(cgApp.getNetWorkStatus());
        setuId(cgApp.getUserId());
    }


    public ErrorMsg(CGApplication cgApp, String msg, int log) {
       // setNet(cgApp.getNetWorkStatus());
        //setuId(cgApp.getUserId());
        setMsg(msg);
        setLog(log);
    }

    /**
     * Priority 日志级别
     */
    private int log = Log.INFO;

    /**
     * 用户ID
     */
    private String uId;


    /**
     * netstatus; //网络可用状态，0 表示无网络可用，1 表示 wifi网络可用  2 表示 手机网络可用
     */
    private int net = 0;

    /**
     * 手机型号
     */
    private String mt = Build.MODEL;

    /**
     * 系统 版本号
     */
    private String sv = Build.VERSION.RELEASE;

    /**
     * os
     */
    private String os = "android";

    /**
     * app version
     */
    private String av = Constants.version_;

    /**
     * 时间
     */
    private String time = DateUtil.formatDatetoString(new Date(), DateUtil.ISO8601_DATETIME_PATTERN);

    /**
     * 错误内容
     */
    private String msg;


    public int getLog() {
        return log;
    }

    public void setLog(int log) {
        this.log = log;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getNet() {
        return net;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getAv() {
        return av;
    }

    public void setAv(String av) {
        this.av = av;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
