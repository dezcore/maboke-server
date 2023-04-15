package com.zcore.mabokeserver.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.api.client.util.Value;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo; 
import springfox.documentation.spi.DocumentationType;*/

//@Configuration
//@EnableSwagger2
//@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    private static final String ERROR_PATH = "(?!/error).+";
    private static final String PARAMETERS_PATH = "(?!/parameters).+";
    private static final String BASE_PACKAGE = "com.zcore.mabokeserver";

    private static final String GROUP_NAME = "zcore";
    private static final String TITLE = "MABOKE REST API";
    private static final String DESCRIPTION = "Maboke server.";
    private static final String VERSION = "0.0.1";

    //@Value( "${swagger.basePath}")
    //private String basePath;

    //@Value( "${swagger.google.client}")
    //private String client;

    //@Value("${swagger.google.baseUrl}")
    //private String baseUrl;
    
    //@Value( "${swagger.google.loginEndpointBuilderPath}")
    //private String loginEndpointBuilderPath;

    /*@Bean
    public SecurityConfiguration securityConfiguration() {
        Map<String, Object> additionalQueryStringParams= new HashMap<>();
        additionalQueryStringParams.put("nonce","123456");
        return SecurityConfigurationBuilder.builder()
                //.clientId(client).realm(realm).appName("swagger-ui")
                //.additionalQueryStringParams(additionalQueryStringParams)
                .build();
    }*/

    /*private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION).build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(GROUP_NAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.regex("/.*"))
                .paths(PathSelectors.regex(ERROR_PATH))
                .paths(PathSelectors.regex(PARAMETERS_PATH))
                .build().apiInfo(apiInfo());
    }*/

    /*@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }*/
}
