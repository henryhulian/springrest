package com.springrest.restserver.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.springrest.restserver.protocol.amf.AmfChannelInitializer;

@Configuration
public class NettyConfig {
	
	private static Log log = LogFactory.getLog(NettyConfig.class);

	@Autowired
	private Environment env;
	
	@Autowired
	private AmfChannelInitializer amfChannelInitializer;

	@SuppressWarnings("unchecked")
	@Bean
	public ServerBootstrap bootstrap() {

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup(), workerGroup())
				.channel(NioServerSocketChannel.class)
				.childHandler(amfChannelInitializer);
		Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
		Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
		for (@SuppressWarnings("rawtypes")
		ChannelOption option : keySet) {
			bootstrap.option(option, tcpChannelOptions.get(option));
		}
		InetSocketAddress address = tcpPort();
		bootstrap.bind(address);
		log.info("netty started at "+address.getAddress()+":"+address.getPort());
		return bootstrap;
	}

	@Bean(destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(env.getProperty("io.core",Integer.class,Runtime.getRuntime().availableProcessors() + 1));
	}

	@Bean(destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(env.getProperty("io.worker.max",Integer.class,Runtime.getRuntime().availableProcessors() + 1));
	}

	@Bean
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(env.getProperty("tcp.socket.port",Integer.class,12727));
	}

	@Bean
	public Map<ChannelOption<?>, Object> tcpChannelOptions() {
		Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
		options.put(ChannelOption.SO_KEEPALIVE, true);
		options.put(ChannelOption.SO_BACKLOG, 128);
		return options;
	}

}
