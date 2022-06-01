/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mycompany.springmvc.formatter.CategoryFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 *
 * @author Asus
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.mycompany.springmvc.controllers",
                               "com.mycompany.springmvc.repository",
                               "com.mycompany.springmvc.service"})
@EnableTransactionManagement
public class WebApplicationContextConfig implements WebMvcConfigurer{
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Validator getValidator() {
        return validator();
    }
    
    
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/resources/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/resources/js");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/resources/images");
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver s = new SpringResourceTemplateResolver();
        s.setApplicationContext(applicationContext);
        s.setPrefix("/WEB-INF/views/");
        s.setSuffix(".html");
        s.setCharacterEncoding("UTF-8");
        s.setOrder(1);
        return s;
    }
    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine s = new SpringTemplateEngine();
        s.setTemplateResolver(templateResolver());
        s.setEnableSpringELCompiler(true);
        s.setTemplateEngineMessageSource(messageSource());
        return s;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver t = new ThymeleafViewResolver();
        t.setTemplateEngine(templateEngine());
        t.setCharacterEncoding("UTF-8");
        
        registry.viewResolver(t);
    }
    
    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource r = new ResourceBundleMessageSource();
        r.setBasenames("message","error");
        return r;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver c = new CommonsMultipartResolver();
        c.setDefaultEncoding("UTF-8");
        return c;        
    }
    
    
    
    @Bean(name = "validator")
    public LocalValidatorFactoryBean validator(){
        LocalValidatorFactoryBean l = new LocalValidatorFactoryBean();
        l.setValidationMessageSource(messageSource());
        
        return l;
                
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new CategoryFormatter());
    }   
    
    
}
