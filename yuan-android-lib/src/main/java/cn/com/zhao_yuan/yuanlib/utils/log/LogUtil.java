package cn.com.zhao_yuan.yuanlib.utils.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * 功能描述:日志工具类
 *
 * @author zhao-yuan
 * @date 2018-08-24 13:54:23
 */
public class LogUtil {

    private static final int LOG_LEVEL_VERBOSE = 0;
    private static final int LOG_LEVEL_DEBUG = 1;
    private static final int LOG_LEVEL_INFO = 2;
    private static final int LOG_LEVEL_WARN = 3;
    private static final int LOG_LEVEL_ERROR = 4;
    // 是否输入日志
    private static boolean isOutPut = false;
    // 是否只输入指定等级日志
    private static boolean isOnlyLevel = false;
    // 输出日志等级
    private static int outPutLevel = 0;
    private static String TAG = "";

    /**
     * 日志工具类初始化
     *
     * @param isOutPut    是否输出日志
     * @param isOnlyLevel 是否只输出指定级别日志
     */
    public static void init(boolean isOutPut, int outPutLevel, boolean isOnlyLevel) {
        LogUtil.isOutPut = isOutPut;
        LogUtil.outPutLevel = outPutLevel;
        LogUtil.isOnlyLevel = isOnlyLevel;
    }

    /**
     * 方法开始打印日志
     */
    public static void wMethodStart() {
        if (!isOutPut) {
            return;
        } else if (isOnlyLevel && LOG_LEVEL_DEBUG != outPutLevel) {
            return;
        } else if (!isOnlyLevel && LOG_LEVEL_DEBUG < outPutLevel) {
            return;
        }
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
        StringBuffer sb = new StringBuffer();
        sb.append(stackTraceElement.getClassName());
        sb.append(".");
        sb.append(stackTraceElement.getMethodName());
        sb.append("方法执行开始");
        wLog("", sb.toString(), LOG_LEVEL_DEBUG);
    }

    /**
     * 方法结束打印日志
     */
    public static void wMethodEnd() {
        if (!isOutPut) {
            return;
        } else if (isOnlyLevel && LOG_LEVEL_DEBUG != outPutLevel) {
            return;
        } else if (!isOnlyLevel && LOG_LEVEL_DEBUG < outPutLevel) {
            return;
        }
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
        StringBuffer sb = new StringBuffer();
        sb.append(stackTraceElement.getClassName());
        sb.append(".");
        sb.append(stackTraceElement.getMethodName());
        sb.append("方法执行结束");
        wLog("", sb.toString(), LOG_LEVEL_DEBUG);
    }

    /**
     * 打印verbose日志
     */
    public static void wVerbose(String title, Object content) {
        wLog(title, content, LOG_LEVEL_VERBOSE);
    }

    /**
     * 打印info日志
     */
    public static void wInfo(String title, Object content) {
        wLog(title, content, LOG_LEVEL_INFO);
    }

    /**
     * 打印日志
     *
     * @param title
     * @param content
     * @param level
     */
    private static void wLog(String title, Object content, int level) {
        if (!isOutPut) {
            return;
        } else if (isOnlyLevel && level != outPutLevel) {
            return;
        } else if (!isOnlyLevel && level < outPutLevel) {
            return;
        }
        TAG = new Exception().getStackTrace()[2].getClassName();
        String contentStr = contentToString(content);
        String msg = TextUtils.isEmpty(title) ? contentStr : (title + ":" + contentStr);
        if (level == LOG_LEVEL_VERBOSE) {
            Log.v(TAG, msg);
        } else if (level == LOG_LEVEL_DEBUG) {
            Log.d(TAG, msg);
        } else if (level == LOG_LEVEL_INFO) {
            Log.i(TAG, msg);
        } else if (level == LOG_LEVEL_WARN) {
            Log.w(TAG, msg);
        } else if (level == LOG_LEVEL_ERROR) {
            Log.e(TAG, msg);
        }
    }


    /**
     * 日志内容toString
     *
     * @param content
     * @return
     */
    private static String contentToString(Object content) {
        if (content == null) {
            return "NULL";
        } else if (content instanceof String) {
            return content + "";
        } else if (content instanceof Integer) {
            return content + "";
        } else if (content instanceof Boolean) {
            return ((Boolean) content).booleanValue() + "";
        } else {
            return content + "";
        }
    }
}
