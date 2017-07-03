package com.whatistest.util.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rubyvirusqq@gmail.com on 26/05/17.
 * <p>
 * 时间工具类
 */
public class TimeUtil {

    /**
     * 获取当前时间
     *
     * @param format
     * @return
     */
    public static String getTimestamp(String format) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

}
