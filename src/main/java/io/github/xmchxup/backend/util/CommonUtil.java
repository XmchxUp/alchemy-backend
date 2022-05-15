package io.github.xmchxup.backend.util;

import io.github.xmchxup.backend.bo.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        int pageNum = start / count;
        return PageCounter.builder()
                .page(pageNum)
                .count(count)
                .build();
    }

    public static String timestamp10() {
        long timestamp13 = Calendar.getInstance().getTimeInMillis();
        String timestamp13Str = Long.toString(timestamp13);
        return timestamp13Str.substring(0, timestamp13Str.length() - 3);
    }

    public static Calendar addSomeSeconds(Calendar calendar, int seconds) {
        calendar.add(Calendar.SECOND, seconds);
        return calendar;
    }

    public static Boolean isInTimeLine(Date date, Date start, Date end) {
        long time = date.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();
        return time > startTime && time < endTime;
    }

    public static boolean isOutOfDate(Date expiredTime) {
        long now = Calendar.getInstance().getTimeInMillis();
        long expiredTimeStamp = expiredTime.getTime();
        return now > expiredTimeStamp;
    }

    public static String yuanToFenPlainString(BigDecimal p) {
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

    public static String toPlain(BigDecimal p) {
        return p.stripTrailingZeros().toPlainString();
    }
}
