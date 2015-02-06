package com.springrest.restserver.handler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.springrest.restserver.entity.user.Session;
import com.springrest.restserver.protocol.VideoGameDataPackage;
import com.springrest.restserver.repository.order.DepositOrderRepository;
import com.springrest.restserver.service.Authorization;
import com.springrest.restserver.service.BalanceService;
import com.springrest.restserver.service.SessionService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;

@Component
@Sharable
public class NettySecurityFilter extends MessageToMessageDecoder<VideoGameDataPackage>{
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private DepositOrderRepository depositOrderRepository;
	
	@Autowired
	private Authorization authorization;
	
	private static Log log = LogFactory.getLog(NettySecurityFilter.class);
	
	@SuppressWarnings("deprecation")
	private AttributeKey<String> tokenKey = new AttributeKey<String>(HandlerConstant.TOKEN);

	@Override
	protected void decode(ChannelHandlerContext ctx, VideoGameDataPackage msg,
			List<Object> out) throws Exception {
		
		Channel channel =  ctx.channel();
		
		log.debug("Receive data:"+msg);
		
		if( msg.getCommand().equals(HandlerConstant.COMMAND_LOGIN)){
			
			String token=msg.getParameters().get(HandlerConstant.TOKEN);
			
			// if cann't find token return
			if( StringUtils.isEmpty(token)){
				log.warn(msg);
				log.warn("Login Command , Token is empty , client:"+channel.remoteAddress());
				channel.close();
				return;
			}
			
			Session session = sessionService.findSessionByToken(token);
			
			if( session == null ){
				log.warn("Session not valid for token:"+token);
				channel.close();
				return;
			}
			
			out.add(msg);
			return;
		}
		
		
		String token = channel.attr(tokenKey).get();
		
		 //detect token
        // if cann't find token return
		if( StringUtils.isEmpty(token)){
			log.warn("Token key is tmpty , client:"+channel.remoteAddress());
			 channel.close();
			 return;
		}
		
        //find session information
        //if cann't find session return
        Session session = sessionService.findSessionByToken(token);
        if( session == null ){
        	 channel.close();
        	 return;
        }
    	
        //check ip
        if( session.getSessionIp()!=null && !channel.remoteAddress().toString().contains((String)session.getSessionIp())){
        	 channel.close();
        	 return;
        }
         
       
        //Verify user access
        /*RolesAllowed rolesAnnotation = method.getMethodAnnotation(RolesAllowed.class);
        Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
        
        if( !authorization.isUserAllowed(session.getUserId(), rolesSet)){
        	 log.trace("User:"+session.getUserName()+" do not has in role:"+rolesSet);
        	 channel.close();
        	 return;
        }*/
		
		log.info("token:"+token);
		
		out.add(msg);
	}

}
