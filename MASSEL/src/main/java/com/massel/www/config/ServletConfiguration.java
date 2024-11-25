package com.massel.www.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan(basePackages= {"com.massel.www.controller"})
@EnableGlobalMethodSecurity(prePostEnabled=true) // 추가
public class ServletConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// resource 경로 설정, upload 경로 설정
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/upload/**").addResourceLocations("file:\\C:\\projectImg\\MASSEL\\MASSELImgFile\\fileUpload/");

	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// prefix(/WEB-INF/views) , suffix(.jsp) 설정
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}
	
	//MultipartResolver 설정 (첨부파일 기능 넣을때 사용)
	@Bean(name="multipartResolver")
	public MultipartResolver getMultipartResolver() throws IOException{
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		return multipartResolver;
	}
	
}
