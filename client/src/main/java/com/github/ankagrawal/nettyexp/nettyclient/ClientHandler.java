package com.github.ankagrawal.nettyexp.nettyclient;

import com.codahale.metrics.Timer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
  private final Timer t = new Timer();
  private Timer.Context context;

  @Override
  public void channelActive(ChannelHandlerContext channelHandlerContext){
    String s = "/Users/ankuagra/scratch/tempfile";
    context = t.time();
    channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(s, CharsetUtil.UTF_8));
  }

  @Override
  public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf in) {
    System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
    System.out.println("\n\n");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
    cause.printStackTrace();
    channelHandlerContext.close();
    System.out.println(context.stop());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    super.channelInactive(ctx);
  }
}
