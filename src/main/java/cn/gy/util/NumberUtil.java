package cn.gy.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class NumberUtil {
    public static String getPercent(Double val) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        String perc = nf.format(Math.abs(val));
        return perc.substring(0, perc.length() - 1);
    }

    public static String getScaleFormat(Double val, int scale) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(scale);
        nf.setGroupingUsed(false);
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(val);
    }

    public static BigDecimal pow(BigDecimal b, int n) {
        int i = 0;
        BigDecimal r = new BigDecimal(1);
        while (i < n) {
            r = r.multiply(b);
            i++;
        }
        return r;
    }
}
