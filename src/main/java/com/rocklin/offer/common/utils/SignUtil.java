package com.rocklin.offer.common.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.rocklin.offer.common.constants.Constants.AMPERSAND;
import static com.rocklin.offer.common.constants.Constants.EQUAL;

public class SignUtil {

    /**
     * 生成签名（ZPAY 规则：按 ASCII 升序拼接 + key，MD5 小写）
     *
     * @param params 参数
     * @param key    商户密钥
     * @return md5签名（小写）
     */
    public static String getSign(Map<String, String> params, String key) {
        Map<String, String> sortedParams = sortByKey(params);

        String signStr = sortedParams.entrySet().stream()
                .filter(e -> e.getValue() != null && !e.getValue().isEmpty())
                .map(e -> e.getKey() + EQUAL + e.getValue())
                .reduce((a, b) -> a + AMPERSAND + b)
                .orElse("");

        signStr += key;

        return DigestUtils.md5DigestAsHex(signStr.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 按 key 升序排序
     */
    private static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
