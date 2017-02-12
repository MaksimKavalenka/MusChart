package com.muschart.spring.configuration;

import static com.muschart.constants.RoleConstants.*;
import static com.muschart.constants.UrlConstants.*;
import static com.muschart.constants.UrlConstants.Page.Operation.*;
import static com.muschart.constants.UrlConstants.Rest.Operation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    private UserDetailsService userDetailsSecurityService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsSecurityService);
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

        urlRegistry.antMatchers(Page.Resources.AUDIO_URL + ANY).permitAll();
        urlRegistry.antMatchers(Page.Resources.ARTIST_IMAGE_URL + ANY).permitAll();
        urlRegistry.antMatchers(Page.Resources.TRACK_IMAGE_URL + ANY).permitAll();

        urlRegistry.antMatchers(ANY + ADD_OPERATION).hasRole(ROLE_ADMIN.toString());

        urlRegistry.antMatchers(HttpMethod.GET, Rest.ARTISTS_URL + ANY).permitAll();
        urlRegistry.antMatchers(HttpMethod.GET, Rest.ARTISTS_URL + USER_OPERATION + ANY).hasRole(ROLE_USER.toString());
        urlRegistry.antMatchers(HttpMethod.POST, Rest.ARTISTS_URL + ANY).hasRole(ROLE_ADMIN.toString());
        urlRegistry.antMatchers(HttpMethod.DELETE, Rest.ARTISTS_URL + ANY).hasRole(ROLE_ADMIN.toString());

        urlRegistry.antMatchers(HttpMethod.GET, Rest.GENRES_URL + ANY).permitAll();
        urlRegistry.antMatchers(HttpMethod.GET, Rest.GENRES_URL + USER_OPERATION + ANY).hasRole(ROLE_USER.toString());
        urlRegistry.antMatchers(HttpMethod.POST, Rest.GENRES_URL + ANY).hasRole(ROLE_ADMIN.toString());
        urlRegistry.antMatchers(HttpMethod.POST, Rest.GENRES_URL + CHECK_OPERATION + ANY).permitAll();
        urlRegistry.antMatchers(HttpMethod.DELETE, Rest.GENRES_URL + ANY).hasRole(ROLE_ADMIN.toString());

        urlRegistry.antMatchers(HttpMethod.POST, Rest.SEARCH_URL + ANY).permitAll();

        urlRegistry.antMatchers(HttpMethod.GET, Rest.TRACKS_URL + ANY).permitAll();
        urlRegistry.antMatchers(HttpMethod.GET, Rest.TRACKS_URL + USER_OPERATION + ANY).hasRole(ROLE_USER.toString());
        urlRegistry.antMatchers(HttpMethod.POST, Rest.TRACKS_URL + ANY).hasRole(ROLE_ADMIN.toString());
        urlRegistry.antMatchers(HttpMethod.DELETE, Rest.TRACKS_URL + ANY).hasRole(ROLE_ADMIN.toString());

        urlRegistry.antMatchers(HttpMethod.GET, Rest.UNITS_URL + ANY).permitAll();

        urlRegistry.antMatchers(HttpMethod.POST, Rest.UPLOAD_URL + ANY).hasRole(ROLE_ADMIN.toString());

        urlRegistry.antMatchers(HttpMethod.GET, Rest.USERS_URL + ANY).permitAll();
        urlRegistry.antMatchers(HttpMethod.POST, Rest.USERS_URL + ANY).permitAll();
        urlRegistry.antMatchers(HttpMethod.POST, Rest.USERS_URL + LOGOUT_OPERATION + ANY).hasRole(ROLE_USER.toString());
        urlRegistry.antMatchers(HttpMethod.POST, Rest.USERS_URL + LIKE_OPERATION + ANY).hasRole(ROLE_USER.toString());

        urlRegistry.anyRequest().authenticated();
        urlRegistry.and().formLogin().loginPage(Page.Common.LOGIN_URL).permitAll();

        urlRegistry.and().httpBasic().authenticationEntryPoint(http403ForbiddenEntryPoint());
        urlRegistry.and().csrf().csrfTokenRepository(csrfTokenRepository());
        urlRegistry.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

}