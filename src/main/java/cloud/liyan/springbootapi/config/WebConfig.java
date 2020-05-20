package cloud.liyan.springbootapi.config;

import cloud.liyan.springbootapi.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author liyan
 * @description
 * @date 2020-05-19 17:35
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/api/**");
    }


}
