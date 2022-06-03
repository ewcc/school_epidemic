package com.ew.school_epidemic.config;

import com.ew.school_epidemic.handler.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author ew
 * @date 2022/3/12 18:20
 */
@Configuration
public class MymvcConfig extends WebMvcConfigurerAdapter implements WebMvcConfigurer {
        @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    //访问本地资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/school_picture/**")
                .addResourceLocations("file:///D:/school_picture/");
    }
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter=new WebMvcConfigurerAdapter() {
            //视图控制器
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/login").setViewName("login");
                //防止表单重复提交
                registry.addViewController("/main.html").setViewName("index_main");
            }
            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //拦截所有
                //如果静态资源访问不到，可能被拦截了，需要修改拦截器规则
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/","/login","/userLogin","/static/**"
                                , "/school_picture/**");//放行请求
            }
        };
        return  adapter;
    }
}
