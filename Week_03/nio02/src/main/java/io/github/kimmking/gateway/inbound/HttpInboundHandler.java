package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.VO.ResultVO;
import io.github.kimmking.gateway.enums.ResultEnum;
import io.github.kimmking.gateway.enums.RouterEnum;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.inbound.BasicAuthFilter;
import io.github.kimmking.gateway.filter.inbound.DebugRequestFilter;
import io.github.kimmking.gateway.filter.inbound.ModifyTraceHeaderFilter;
import io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RoundRobinHttpEndPointRouter;
import io.github.kimmking.gateway.utils.ResultVOUtil;
import io.github.kimmking.gateway.utils.RouterUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.util.CharsetUtil;
import com.google.gson.Gson;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;
    private HttpOutboundHandler handler;
    private static final List<HttpRequestFilter> inboundFilters;

    static {
        inboundFilters = new ArrayList<>();
        inboundFilters.add(new DebugRequestFilter());
        inboundFilters.add(new BasicAuthFilter());
        inboundFilters.add(new ModifyTraceHeaderFilter());
    }

    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        handler = new HttpOutboundHandler();
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        FullHttpRequest fullRequest = null;
        try {
            fullRequest = (FullHttpRequest) msg;
            FullHttpRequest finalFullRequest = fullRequest;
            for (HttpRequestFilter filter: inboundFilters) {
                filter.filter(finalFullRequest, ctx);
            }
            //执行router filter
            String backendUrl = RouterUtil.get(fullRequest, ctx, RouterEnum.ROUND_ROBIN);
            logger.info("获取到的后端URL: {}", backendUrl);
            //调用后端服务
            handler.handle(fullRequest, ctx, backendUrl);
        } catch(Exception e) {
            if (fullRequest != null) {
                handler.errorHandle(fullRequest, ctx, e.getMessage());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


//    private void handlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
//        FullHttpResponse response = null;
//        try {
//            String value = "hello,kimmking";
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", response.content().readableBytes());
//
//        } catch (Exception e) {
//            logger.error("处理测试接口出错", e);
//            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
//        } finally {
//            if (fullRequest != null) {
//                if (!HttpUtil.isKeepAlive(fullRequest)) {
//                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                } else {
//                    response.headers().set(CONNECTION, KEEP_ALIVE);
//                    ctx.write(response);
//                }
//            }
//        }
//    }
//
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
