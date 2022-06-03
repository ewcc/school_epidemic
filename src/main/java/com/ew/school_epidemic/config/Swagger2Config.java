package com.ew.school_epidemic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ew
 * @date 2022/2/7 15:47
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     * 用于配置swagger2，包含文档基本信息
     * 指定swagger2的作用域（这里指定包路径下的所有API）
     *
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //指定需要扫描的controller
                .apis(RequestHandlerSelectors.basePackage("com.ew.school_epidemic.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建文档基本信息，用于页面显示，可以包含版本、
     * 联系人信息、服务地址、文档描述信息等
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("校园疫情在线接口文档")
                .description("通过访问swagger-ui.html,实现接口测试、文档生成")
                .termsOfServiceUrl("http://localhost:8080")
                //设置联系方式
                .version("1.0")
                .build();
    }
}
