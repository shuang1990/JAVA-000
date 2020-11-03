package io.github.kimmking.gateway.filter.outbound;

import io.github.kimmking.gateway.constants.GatewayConstant;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class ModifyResponseHeaderFilter implements HttpResponseFilter {

    @Override
    public void filter(FullHttpResponse fullResponse, ChannelHandlerContext ctx) throws Exception {
        fullResponse.headers().set("gateway_id", GatewayConstant.GATEWAY_ID);
    }
}
