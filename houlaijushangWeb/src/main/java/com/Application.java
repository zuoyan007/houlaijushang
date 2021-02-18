/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com;

import com.lamb.util.TokenApiInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Application
 * @author ThinkGem
 * @version 2018-10-13
 */
@SpringBootApplication(scanBasePackages= "com.lamb")
public class Application extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		this.setRegisterErrorPageFilter(false); // 错误页面有容器来处理，而不是SpringBoot
		return builder.sources(Application.class);
	}

	@Configuration
	public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
		// 增加拦截器
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new TokenApiInterceptor()) // 指定拦截器类
					.addPathPatterns("/api/**"); // 指定该类拦截的url
			super.addInterceptors(registry);
		}
	}
	
}