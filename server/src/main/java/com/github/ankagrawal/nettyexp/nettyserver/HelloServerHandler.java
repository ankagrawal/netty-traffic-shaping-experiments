package com.github.ankagrawal.nettyexp.nettyserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class HelloServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    TrafficHandler trafficHandler = (TrafficHandler) ctx.attr(AttributeKey.valueOf("traffic-shaping-handler")).get();
    ByteBuf inBuffer = (ByteBuf) msg;

    String received = inBuffer.toString(CharsetUtil.UTF_8);
    System.out.println("Server received: " + received);

    String s = "";
    try {
      s = new String(Files.readAllBytes(Paths.get(received)), StandardCharsets.UTF_8);
    } catch(IOException ex) {
      s = "Error in opening file" + ex.getMessage();
    }

    ctx.write(Unpooled.copiedBuffer(s, CharsetUtil.UTF_8));
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
