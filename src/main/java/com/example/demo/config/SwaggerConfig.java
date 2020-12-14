package com.example.demo.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration//配置类
@EnableSwagger2 //swagger注解
public class SwaggerConfig {

    @org.springframework.context.annotation.Bean
    public Docket webApiConfig(){
        ParameterBuilder ticketPar = new ParameterBuilder();
        java.util.List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("Token").description("user ticket")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("spring-boot-alipay")
                .description("本文档描述了alipay支付微服务接口定义")
                .version("1.0")
                .contact(new Contact("java", "http://baidu.com", "1123@qq.com"))
                .build();
    }
}
