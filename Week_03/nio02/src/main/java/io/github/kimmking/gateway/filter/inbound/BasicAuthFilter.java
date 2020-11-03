package io.github.kimmking.gateway.filter.inbound;

import io.github.kimmking.gateway.enums.ResultEnum;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.internal.StringUtil;

/**
 *  身份认证
 */
public class BasicAuthFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) throws Exception {
        String authorization = fullRequest.headers().get("Authorization");
        if (StringUtil.isNullOrEmpty(authorization)) {
            throw new RuntimeException(ResultEnum.PARAM_TOKEN_ERROR.getMessage());
        }
        //调用用户中心验证token是否合法
    }
}
