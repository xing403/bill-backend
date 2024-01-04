package com.bill.backend.config;


import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    @Bean
    public Docket docket() {
        // http://localhost:8888/swagger-ui.html
        return new Docket(DocumentationType.SWAGGER_2)
                // 配置接口相关信息
                .apiInfo( apiInfo() )
                // 选择哪些接口作为 swagger 的 doc 发布
                .select()
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("校园资源共享系统接口文档")
                .description("校园资源共享系统项目描述")
                .contact(new Contact("星如雨", "http://ilstudy.vip", "liyun200104@163.com"))
                .version("0.1")
                .build();
    }
}