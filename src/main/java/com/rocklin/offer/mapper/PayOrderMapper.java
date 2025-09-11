package com.rocklin.offer.mapper;

import com.rocklin.offer.model.entity.PayOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayOrderMapper {

    int insert(PayOrder order);

    PayOrder selectByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    int updateStatusAndTradeNo(@Param("outTradeNo") String outTradeNo,
                               @Param("tradeNo") String tradeNo,
                               @Param("status") Integer status);
}
