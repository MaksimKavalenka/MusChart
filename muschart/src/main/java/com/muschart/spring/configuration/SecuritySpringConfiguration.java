package com.muschart.spring.configuration;

import static com.muschart.constants.RoleConstants.*;
import static com.muschart.constants.UrlConstants.*;
import static com.muschart.constants.UrlConstants.Page.Operation.*;
import static com.muschart.constants.UrlConstants.Rest.Operation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.muschart.spring.component.CsrfHeaderFilter;

@Configuration
@EnableWebSecurity
public class SecuritySpringConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceSecurity")
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        ReflectionSaltSource saltSource = new ReflectionSaltSource();
        saltSource.setUserPropertyToUse("username");

        authenticationProvider.setSaltSource(saltSource);
        return authenticationProvider;
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public Http403ForbiddenEntryPoint http403ForbiddenEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

    @Bean
    public MessageDigestPasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/html/**");
        web.ignoring().antMatchers("/img/**");
        web.ignoring().antMatchers("/js/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http
                .authorizeRequests();

        urlRegistry.antMatchers(Page.Common.REGISTRATION_URL).permitAll();
        urlRegistry.antMatchers(Page.Common.SETTINGS_URL).permitAll();
        urlRegistry.antMatchers(Page.Common.PLAYLIST_URL).permitAll();

        urlRegistry.antMatchers(Page.Artist.ARTISTS_URL).permitAll();
        urlRegistry.antMatchers(Page.Artist.ARTIST_FULL_URL).permitAll();
        urlRegistry.antMatchers(Page.Artist.ARTIST_GENRES_URL).permitAll();
        urlRegistry.antMatchers(Page.Artist.ARTIST_TRACKS_URL).permitAll();

        urlRegistry.antMatchers(Page.Genre.GENRES_URL).permitAll();
        urlRegistry.antMatchers(Page.Genre.GENRE_FULL_URL).permitAll();
        urlRegistry.antMatchers(Page.Genre.GENRE_ARTISTS_URL).permitAll();
        urlRegistry.antMatchers(Page.Genre.GENRE_TRACKS_URL).permitAll();

        urlRegistry.antMatchers(Page.Track.TRACKS_URL).permitAll();
        urlRegistry.antMatchers(Page.Track.TRACK_FULL_URL).permitAll();
        urlRegistry.antMatchers(Page.Track.TRACK_ARTISTS_URL).permitAll();
        urlRegistry.antMatchers(Page.Track.TRACK_GENRES_URL).permitAll();

        urlRegistry.antMatchers(ANY + ADD_OPERATION).hasRole(ROLE_ADMIN.toString());

        urlRegistry.antMatchers(ANY + AUTH_OPERATION + ANY).permitAll();
        urlRegistry.antMatchers(ANY + CHECK_OPERATION + ANY).permitAll();
        urlRegistry.antMatchers(ANY + CREATE_OPERATION + ANY).permitAll();
        urlRegistry.antMatchers(ANY + DELETE_OPERATION + ANY).hasRole(ROLE_ADMIN.toString());
        urlRegistry.antMatchers(ANY + GET_OPERATION + ANY).permitAll();
        urlRegistry.antMatchers(ANY + LIKE_OPERATION + ANY).hasRole(ROLE_USER.toString());
        urlRegistry.antMatchers(ANY + LOGOUT_OPERATION + ANY).permitAll();
        urlRegistry.antMatchers(ANY + UPDATE_OPERATION + ANY).hasRole(ROLE_ADMIN.toString());
        urlRegistry.antMatchers(ANY + USER_OPERATION + ANY).permitAll();

        urlRegistry.antMatchers("/audio" + ANY).permitAll();
        urlRegistry.antMatchers("/image" + ANY).permitAll();

        urlRegistry.anyRequest().authenticated();
        urlRegistry.and().formLogin().loginPage(Page.Common.LOGIN_URL).permitAll();

        urlRegistry.and().httpBasic().authenticationEntryPoint(http403ForbiddenEntryPoint());
        urlRegistry.and().csrf().csrfTokenRepository(csrfTokenRepository());
        urlRegistry.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

}
