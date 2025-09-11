package com.rocklin.offer.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNoGenerator {
    public static String generate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(new Date());
        int random = new Random().nextInt(900000) + 100000; // 6位随机数
        return "O" + timeStr + random; // 例如：O20250911221000123456
    }
}
