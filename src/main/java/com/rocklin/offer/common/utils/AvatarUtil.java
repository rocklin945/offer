package com.rocklin.offer.common.utils;

import java.util.Random;

/**
 * @ClassName AvatarUtil
 * @Description 头像生成工具类
 * @Author: rocklin
 */
public class AvatarUtil {
    private static final String[] AVATAR_SERVICE_URLS = {
            "https://api.dicebear.com/9.x/thumbs/svg?seed=",
            "https://api.dicebear.com/9.x/icons/svg?seed=Felix"
    };

    private static final Random RANDOM = new Random();

    public static String generateRandomAvatarUrl(String seed) {
        int index = RANDOM.nextInt(AVATAR_SERVICE_URLS.length);
        return AVATAR_SERVICE_URLS[index] + seed;
    }
}
