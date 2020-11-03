package io.github.kimmking.gateway.utils;

import io.github.kimmking.gateway.VO.ResultVO;
import io.github.kimmking.gateway.enums.RouterEnum;
import io.github.kimmking.gateway.router.IPHashHttpEndpointRouter;
import io.github.kimmking.gateway.router.RoundRobinHttpEndPointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Arrays;
import java.util.List;

public class RouterUtil {

    //可用的服务列表应单独启一个线程从服务发现中心获取并写入当前网关的缓存
    private static final List<String> ROUTER_LIST = Arrays.asList(
            "https://www.baidu.com",
            "https://www.zhihu.com");

    /**
     * @param fullHttpRequest
     * @param ctx
     * @param type
     * @return
     */
    public static String get(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, RouterEnum type) {
        switch (type) {
            case ROUND_ROBIN:
                return new RoundRobinHttpEndPointRouter(fullHttpRequest, ctx).route(ROUTER_LIST);
            case IP_HASH:
                return new IPHashHttpEndpointRouter(fullHttpRequest, ctx).route(ROUTER_LIST);
            default:
                throw new RuntimeException("不支持此路由方式");
        }
    }
}
