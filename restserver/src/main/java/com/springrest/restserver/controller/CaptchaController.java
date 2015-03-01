package com.springrest.restserver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.springrest.restserver.common.Code;
import com.springrest.restserver.entity.user.Session;
import com.springrest.restserver.service.SessionService;
import com.springrest.restserver.util.CookieUtil;
import com.springrest.restserver.util.IpUtil;
import com.springrest.restserver.util.TokenUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/captcha")
@Api(value = "/captcha", description = "验证码模块")
public class CaptchaController {
	
	private static final Log log = LogFactory.getLog(CaptchaController.class);
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private DefaultKaptcha defaultKaptcha;

	@RequestMapping(value = "/image", method = RequestMethod.GET , produces=MediaType.IMAGE_JPEG_VALUE)
	@ApiOperation(value = "用户获取验证码",produces=MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> guest( 	HttpServletRequest request , 
											HttpServletResponse response
																			) throws IOException {
		
		String token = CookieUtil.getCookie(request, TokenUtil.TOKEN_COOKIE_NMAE);
		
		String capText = defaultKaptcha.createText();
		BufferedImage bi = defaultKaptcha.createImage(capText);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( bi , "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		
		Session session = null;
		
		if( StringUtils.isEmpty(token) ){
			session = sessionService.createGuestSession(IpUtil.getIp(request));
		}else{
			session = sessionService.findSessionByToken(token);
			if( session == null ){
				session = sessionService.findGuestSessionByToken(token);
			}
		}
		
		if( session == null ){
			session = sessionService.createGuestSession(IpUtil.getIp(request));
		}
		CookieUtil.setCookie(response, TokenUtil.TOKEN_COOKIE_NMAE,
				session.getSessionSign(), request.getContextPath(), true, -1);
		
		session.setCheckCode(capText);
		sessionService.updateGuestSession(session);
		CookieUtil.setCookie(response, TokenUtil.TOKEN_COOKIE_NMAE,
				session.getSessionSign(), request.getContextPath(), true, -1);
		
		log.trace("session:"+session.getId()+" captcha:"+capText);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		return new ResponseEntity<byte[]>(imageInByte, headers,
				HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ApiOperation(value = "用户核对证码")
	public Code value( HttpServletRequest request ,
										HttpServletResponse response,
										@ApiIgnore @CookieValue(TokenUtil.TOKEN_COOKIE_NMAE) String token,
										@RequestParam String checkCode) throws IOException {
		
		Session session = sessionService.findSessionByToken(token);
		if( session == null ){
			session = sessionService.findGuestSessionByToken(token);
		}
		if( session == null ){
			return new Code(Code.CHECK_CODE_NOT_MATCH);
		}
		
		if( session.getCheckCode()!=null && session.getCheckCode().equals(checkCode)){
			return new Code(Code.SUCCESS);
		}else{
			return new Code(Code.CHECK_CODE_NOT_MATCH);
		}
		
	}
}
