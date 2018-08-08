package com.muschart.spring.configuration;

import static com.muschart.constants.UrlConstants.ANY;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.muschart.constants.UrlConstants.Page;
import com.muschart.spring.component.CsrfHeaderFilter;

@Configuration
@EnableWebSecurity
public class SecuritySpringConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(Page.Resources.CSS_URL + ANY);
        web.ignoring().antMatchers(Page.Resources.HTML_URL + ANY);
        web.ignoring().antMatchers(Page.Resources.IMG_URL + ANY);
        web.ignoring().antMatchers(Page.Resources.JS_URL + ANY);
        web.ignoring().antMatchers(Page.Resources.WEBJARS_URL + ANY);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();

        urlRegistry.antMatchers("/").permitAll();

        urlRegistry.and().csrf().csrfTokenRepository(csrfTokenRepository());
        urlRegistry.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

}