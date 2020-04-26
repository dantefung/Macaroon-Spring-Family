package com.dantefung.sample.config;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author DANTE FUNG
 */
@Slf4j
@Configuration
@EnableSwagger2
public class Swagger2Config implements WebMvcConfigurer {

	/**
	 * 通用请求参数:调用能力中心后台服务接口时，如果接口要求验证用户身份token，则使用该Header Key在Http header中传入
	 */
	public static final String TOKEN_HTTP_HEADER_KEY ="X-OS-KERNEL-TOKEN";


	/**
	 * 通用请求参数:调用能力中心后台服务接口时，需要提供调用的应用系统ID，使用该Header Key在Http header中传入
	 */
	public static final String APPSYSID_HTTP_HEADER_KEY = "X-OS-KERNEL-APPSYSID";


	/**
	 * Shiro 要求的认证Header Key
	 */
	public static final String SHIRO_AUTHZ_HEADER_KEY = "Authorization";

	/**
	 * 由于身份认证使用CAS，不使用Cas，因此写入固定的值，跳过Shiro的认证模块
	 */
	public static final String SHIRO_AUTHZ_HEADER_VALUE = "Basic PASSTHR";

	/**
	 *
	 * 显示swagger-ui.html文档展示页，还必须注入swagger资源：
	 *
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
	 *
	 * @return Docket
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//此包路径下的类，才生成接口文档
				//.apis(RequestHandlerSelectors.basePackage("com.utopa.os.kernel.datactr"))
				//加了ApiOperation注解的类，才生成接口文档
	            //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(setHeaderToken());
	}

	/**
	 * JWT token
	 * @return
	 */
	private List<Parameter> setHeaderToken() {
		String string = "string";
		String header = "header";
        ParameterBuilder tokenPar = new ParameterBuilder();
		ParameterBuilder authPar = new ParameterBuilder();
		ParameterBuilder appPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(TOKEN_HTTP_HEADER_KEY).description("token").modelRef(new ModelRef(string)).parameterType(header).required(false).build();
		authPar.name(SHIRO_AUTHZ_HEADER_KEY).description("Authorization").defaultValue(SHIRO_AUTHZ_HEADER_VALUE).modelRef(new ModelRef(string)).parameterType(header).required(false).build();
		appPar.name(APPSYSID_HTTP_HEADER_KEY).description("X-OS-KERNEL-APPSYSID").modelRef(new ModelRef(string)).parameterType(header).required(false).build();
		pars.add(tokenPar.build());
		pars.add(authPar.build());
		pars.add(appPar.build());
        return pars;
    }

	/**
	 * api文档的详细信息函数,注意这里的注解引用的是哪个
	 *
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// //大标题
				.title("XXX中心后台服务API接口文档")
				// 版本号
				.version("1.0")
				.description("restful 风格接口")
				.build();
	}

}
