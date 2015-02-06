package com.springrest.restserver.protocol.linebase;


import org.springframework.beans.factory.annotation.Autowired;

import com.springrest.restserver.handler.NettySecurityFilter;
import com.springrest.restserver.handler.NettyServerHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class StringLineChannelInitializer extends ChannelInitializer<SocketChannel> {

	    private StringDecoder stringDecoder;

	    private StringEncoder stringEncoder;
	    
	    @Autowired
	    private StringLineCommandDecoder commandDecoder;

	    @Autowired
	    private NettyServerHandler serverHandler;
	    
	    @Autowired
	    private NettySecurityFilter securityFilter;


	    @Override
	    protected void initChannel(SocketChannel ch) throws Exception {
	        ChannelPipeline pipeline = ch.pipeline();
	        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192,true,Unpooled.wrappedBuffer(new byte[]{';'})));
	        pipeline.addLast("decoder", stringDecoder);
	        pipeline.addLast("commandDecoder", commandDecoder);
	        pipeline.addLast("filter", securityFilter);
	        pipeline.addLast("handler", serverHandler);
	        pipeline.addLast("encoder", stringEncoder);
	       
	    }

		public StringDecoder getStringDecoder() {
			return stringDecoder;
		}

		public void setStringDecoder(StringDecoder stringDecoder) {
			this.stringDecoder = stringDecoder;
		}

		public StringEncoder getStringEncoder() {
			return stringEncoder;
		}

		public void setStringEncoder(StringEncoder stringEncoder) {
			this.stringEncoder = stringEncoder;
		}

		public NettyServerHandler getServerHandler() {
			return serverHandler;
		}

		public void setServerHandler(NettyServerHandler serverHandler) {
			this.serverHandler = serverHandler;
		}

		public NettySecurityFilter getSecurityFilter() {
			return securityFilter;
		}

		public void setSecurityFilter(NettySecurityFilter securityFilter) {
			this.securityFilter = securityFilter;
		}

}
