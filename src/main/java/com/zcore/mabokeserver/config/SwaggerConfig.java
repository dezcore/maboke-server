package com.zcore.mabokeserver.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.util.Value;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//import springfox.documentation.spring.web.paths.RelativePathProvider;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String ERROR_PATH = "(?!/error).+";
    private static final String PARAMETERS_PATH = "(?!/parameters).+";
    private static final String BASE_PACKAGE = "com.zcore.mabokeserver";

    private static final String GROUP_NAME = "zcore";
    private static final String TITLE = "MABOKE REST API";
    private static final String DESCRIPTION = "Maboke server.";
    private static final String VERSION = "0.0.1";

    @Value( "${swagger.basePath}")
    private String basePath;

    @Value( "${swagger.google.client}")
    private String client;

    @Value("${swagger.google.baseUrl}")
    private String baseUrl;
    
    //@Value( "${swagger.google.loginEndpointBuilderPath}")
    //private String loginEndpointBuilderPath;

    @Bean
    public SecurityConfiguration securityConfiguration() {
        Map<String, Object> additionalQueryStringParams= new HashMap<>();
        additionalQueryStringParams.put("nonce","123456");
        return SecurityConfigurationBuilder.builder()
                //.clientId(client).realm(realm).appName("swagger-ui")
                //.additionalQueryStringParams(additionalQueryStringParams)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION).build();
    }

    @Bean
    public Docket salariesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(GROUP_NAME)
                /*.pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return basePath;
                    }
                })*/
                .select().apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.regex("/.*"))
                .paths(PathSelectors.regex(ERROR_PATH))
                .paths(PathSelectors.regex(PARAMETERS_PATH))
                .build().apiInfo(apiInfo());
    }

    /*private List<SecurityContext> buildSecurityContext() {
        List<SecurityReference> securityReferences = new ArrayList<>();

        securityReferences.add(SecurityReference.builder().reference("oauth2").scopes(scopes().toArray(new AuthorizationScope[]{})).build());

        SecurityContext context = SecurityContext.builder().forPaths(Predicates.alwaysTrue()).securityReferences(securityReferences).build();

        List<SecurityContext> ret = new ArrayList<>();
        ret.add(context);
        return ret;
    }*/

    /*private List<? extends SecurityScheme> buildSecurityScheme() {
        List<SecurityScheme> lst = new ArrayList<>();
        LoginEndpoint login = new LoginEndpointBuilder().url(baseUrl + loginEndpointBuilderPath).build();

        List<GrantType> gTypes = new ArrayList<>();
        gTypes.add(new ImplicitGrant(login, "acces_token"));

        lst.add(new OAuth("oauth2", scopes(), gTypes));
        return lst;
    }*/

    /*private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> scopes = new ArrayList<>();
        for (String scopeItem : new String[]{"openid=openid", "profile=profile"}) {
            String scope[] = scopeItem.split("=");
            if (scope.length == 2) {
                scopes.add(new AuthorizationScopeBuilder().scope(scope[0]).description(scope[1]).build());
            } else {
                logger.warn("Scope '{}' is not valid (format is scope=description)", scopeItem);
            }
        }

        return scopes;
    }*/
}
