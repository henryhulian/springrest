package com.springrest.restserver.protocol.linebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.springrest.restserver.protocol.VideoGameDataPackage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;

@Component
@Sharable
public class StringLineCommandDecoder extends MessageToMessageDecoder<String>{
	
	private static Log log = LogFactory.getLog(StringLineCommandDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		
		try {

			String[] msges = msg.split(":");
			
			VideoGameDataPackage dataPackage = new VideoGameDataPackage();
			
			dataPackage.setCommand(msges[0]);
			
			String[] parameters = msges[1].split("&");
			
			Map<String, String> map = new HashMap<String, String>();
			for( String para : parameters ){
				if( StringUtils.isEmpty(para)){
					log.error(msg+"empty parameter!");
					continue;
				}
				
				String[] keyValues = para.split("=");
				
				if( keyValues==null || keyValues.length<2 ){
					log.error(msg+" parameter error!");
					continue;
				}
				
				map.put(keyValues[0], keyValues[1]);
			}
			
			dataPackage.setParameters(map);

			out.add(dataPackage);
		} catch (Exception e) {
			log.error("Command decode error",e);
			ctx.channel().close();
		}
	}

}
