package io.github.kimmking.gateway.router;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;

public class IPHashHttpEndpointRouter implements HttpEndpointRouter {

    private String ip;

    public IPHashHttpEndpointRouter(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        ip = fullHttpRequest.headers().get("X-Real-IP");
        // 如果为空则使用 netty 默认获取的客户端 IP
        if (this.ip == null) {
            InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
            ip = insocket.getAddress().getHostAddress();
        }
    }

    @Override
    public String route(List<String> endpoints) {
        if (this.ip == null) {
            int index = Math.abs(this.ip.hashCode()) % endpoints.size();
            return endpoints.get(index);
        }
        int random = new Random().nextInt(endpoints.size());
        return endpoints.get(random);
    }
}
