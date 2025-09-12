package com.rocklin.offer.mapper;

import com.rocklin.offer.model.dto.request.PayOrderPageRequest;
import com.rocklin.offer.model.entity.PayOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PayOrderMapper {

    int insert(PayOrder order);

    PayOrder selectByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    int updateStatusAndTradeNo(@Param("outTradeNo") String outTradeNo,
                               @Param("tradeNo") String tradeNo,
                               @Param("status") Integer status);

    long countByCondition(@Param("req") PayOrderPageRequest req);

    List<PayOrder> selectByCondition(@Param("req") PayOrderPageRequest req,@Param("offset") int offset);
}
