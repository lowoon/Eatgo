package kr.co.fastcampus.eatgo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class ETagHeaderFilter {
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>(
            new ShallowEtagHeaderFilter());
        filterRegistrationBean.addUrlPatterns("/restaurants", "/restaurants/*");
        filterRegistrationBean.setName("etagFilter");
        return filterRegistrationBean;
    }
}
