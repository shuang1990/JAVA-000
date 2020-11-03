package io.github.kimmking.gateway.filter.inbound;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.inbound.HttpInboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  打印完整请求信息
 */
public class DebugRequestFilter implements HttpRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(DebugRequestFilter.class);

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        logger.info("REQUEST:: > {}", fullRequest.uri());

        fullRequest.headers().entries().forEach(header -> {
            logger.info("REQUEST:: > {}:{} ", header.getKey(), header.getValue());

        });
        String body = fullRequest.content().toString(CharsetUtil.UTF_8);
        if (!StringUtil.isNullOrEmpty(body)) {
            logger.info("REQUEST:: > {}", body);
        }
    }
}
