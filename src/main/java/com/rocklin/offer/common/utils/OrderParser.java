package com.rocklin.offer.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocklin.offer.model.dto.response.OrderDetailResponse;

public class OrderParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static OrderDetailResponse parse(String jsonString) throws Exception {
        // 直接解析第三方返回的 JSON
        JsonNode innerJson = mapper.readTree(jsonString);

        return mapper.treeToValue(innerJson, OrderDetailResponse.class);
    }
}
