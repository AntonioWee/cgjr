package cgncjr.com.cgncjr.model;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface IsNetConnect {
    boolean isNetConnect(Context context);
    /**
     * 无网络
     * @param activity
     * @return
     */
    boolean unNetConnect(Activity activity);
}
