package com.springrest.restserver.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springrest.restserver.controller.AuthenticationController;
import com.springrest.restserver.protocol.VideoGameDataPackage;

@Component
@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<VideoGameDataPackage> {
	
	private static Log log = LogFactory.getLog(NettyServerHandler.class);
	
	@Autowired
	private AuthenticationController authenticationController;
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, VideoGameDataPackage msg)
			throws Exception {
		
		Channel channel =  ctx.channel();
		
		switch( msg.getCommand()){
			case HandlerConstant.COMMAND_LOGIN:
				
			break;
			
		}
		
		log.info(msg);
		channel.writeAndFlush("OK"+";");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("Client "+ctx.channel().remoteAddress()+" is connected!");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("Client "+ctx.channel().remoteAddress()+" is disconnected!");
		super.channelInactive(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		 try {
		        log.error("ERROR: Unhandled exception: " + cause.getMessage()
		                + ". Closing channel " + ctx.channel().remoteAddress(),cause);
		        ctx.channel().close();
		    } catch (Exception ex) {
		    	log.debug("ERROR trying to close socket because we got an unhandled exception",cause);
		    }
	}

}
