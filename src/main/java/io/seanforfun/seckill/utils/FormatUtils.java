package io.seanforfun.seckill.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 20:19
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class FormatUtils {
    public static final String formatDate(long time) {
        DateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.CANADA);
        return df.format(new Date(time));
    }

    public static final String formatTime(long time) {
        DateFormat df = new SimpleDateFormat("HH-mm-ss");
        return df.format(new Date(time));
    }

    public static final String formatDateTime(long time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date(time));
    }
}
