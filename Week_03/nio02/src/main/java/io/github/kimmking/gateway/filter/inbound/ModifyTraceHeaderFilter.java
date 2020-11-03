package io.github.kimmking.gateway.filter.inbound;

import io.github.kimmking.gateway.constants.GatewayConstant;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.UUID;

/**
 *  添加追踪日志头过滤器，实现服务的全链路追踪
 *  x-request-id
 *  x-b3-traceid
 *  x-b3-spanId
 *  x-b3-parentspanid
 *  x-b3-sampled
 *  x-b3-flags
 *  b3
 */
public class ModifyTraceHeaderFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("GATEWAY_ID", GatewayConstant.GATEWAY_ID);
        fullRequest.headers().set("x-request-id", UUID.randomUUID());
    }
}
