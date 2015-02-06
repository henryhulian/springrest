package com.springrest.restserver.protocol.amf;

import org.springframework.stereotype.Component;

import com.springrest.restserver.protocol.VideoGameDataPackage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Component
@Sharable
public class AmfEncoder extends MessageToByteEncoder<VideoGameDataPackage>{

	@Override
	protected void encode(ChannelHandlerContext ctx, VideoGameDataPackage msg,
			ByteBuf out) throws Exception {
		
	}

}
