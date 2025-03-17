package org.example.servicerest.config;

import org.example.servicerest.filter.SidFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SidFilter> sidFilter() {
        FilterRegistrationBean<SidFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SidFilter());
        registrationBean.addUrlPatterns("/api/v1/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
