package com.github.ankagrawal.nettyexp.nettyserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;
import io.netty.util.AttributeKey;


public class TrafficHandler extends ChannelTrafficShapingHandler {
  private int itr = 0;

  public TrafficHandler(long writeLimit, long readLimit) {
    super(writeLimit, readLimit, 1000, 3000000);
  }

  @Override
  protected void doAccounting(TrafficCounter counter) {
    super.doAccounting(counter);
    itr++;
    System.out.println("itr : " + itr + "TrafficCounter: " + counter.toString());
    System.out.println(counter.lastReadThroughput());
    System.out.println(counter.lastWriteThroughput());
  }

  @Override
  public void configure(long newWriteLimit, long newReadLimit) {
    super.configure(newWriteLimit, newReadLimit);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
    ctx.channel().attr(AttributeKey.valueOf("traffic-shaping-handler")).set(this);
  }
}
