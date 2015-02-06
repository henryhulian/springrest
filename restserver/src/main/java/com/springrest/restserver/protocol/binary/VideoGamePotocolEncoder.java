package com.springrest.restserver.protocol.binary;

import com.springrest.restserver.protocol.VideoGameDataPackage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class VideoGamePotocolEncoder extends MessageToByteEncoder<VideoGameDataPackage>{

	@Override
	protected void encode(ChannelHandlerContext ctx, VideoGameDataPackage msg,
			ByteBuf out) throws Exception {
		
	}

}
