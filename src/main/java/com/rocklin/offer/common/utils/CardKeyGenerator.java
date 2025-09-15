package com.rocklin.offer.common.utils;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CardKeyGenerator {

    // 随机字符源（去掉了容易混淆的 O/0, I/l）
    private static final String CHAR_POOL = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成卡密
     * @param prefix   前缀（如 "VIP"）
     * @param batch    批次号（如 "202509"）
     * @param length   随机码长度（如 10）
     * @return 生成的卡密
     */
    public static String generate(String prefix, String batch, int length) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(batch);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 批量生成卡密
     * @param prefix 前缀
     * @param batch 批次
     * @param length 随机部分长度
     * @param count 生成数量
     * @return 卡密数组
     */
    public static String[] generateBatch(String prefix, String batch, int length, int count) {
        String[] codes = new String[count];
        for (int i = 0; i < count; i++) {
            codes[i] = generate(prefix, batch, length);
        }
        return codes;
    }

    // 示例
    public static void main(String[] args) {
        // 批次号默认用年月
        String batch = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        // 生成 5 个卡密
        String[] codes = generateBatch("VIP", batch, 10, 5);
        for (String code : codes) {
            System.out.println(code);
        }
    }
}
