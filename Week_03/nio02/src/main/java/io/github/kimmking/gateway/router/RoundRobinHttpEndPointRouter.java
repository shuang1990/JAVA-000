package io.github.kimmking.gateway.router;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Round Robin
 */
public class RoundRobinHttpEndPointRouter implements HttpEndpointRouter {

    private static AtomicInteger counter = new AtomicInteger(0);

    public RoundRobinHttpEndPointRouter(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {

    }

    @Override
    public String route(List<String> endpoints) {
        int index = counter.getAndIncrement() % endpoints.size();
        return endpoints.get(index);
    }
}
