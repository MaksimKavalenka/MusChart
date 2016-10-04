package by.gsu.spring.configuration;

import static by.gsu.constants.RoleConstants.*;

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

import by.gsu.constants.UrlConstants.Page;
import by.gsu.constants.UrlConstants.Rest;
import by.gsu.spring.component.CsrfHeaderFilter;

@Configuration
@EnableWebSecurity
public class SecuritySpringConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ANY = "/**";

    @Autowired
    @Qualifier("userDetailsServiceSecurity")
    private UserDetailsService  userDetailsService;

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

        urlRegistry.antMatchers(Page.REGISTRATION_URL).permitAll();

        urlRegistry.antMatchers(Page.ARTIST_URL + ANY).permitAll();
        urlRegistry.antMatchers(Page.ARTISTS_URL).permitAll();
        urlRegistry.antMatchers(Page.GENRE_URL + ANY).permitAll();
        urlRegistry.antMatchers(Page.GENRES_URL).permitAll();
        urlRegistry.antMatchers(Page.TRACK_URL + ANY).permitAll();
        urlRegistry.antMatchers(Page.TRACKS_URL).permitAll();
        urlRegistry.antMatchers(Page.USER_URL + ANY).hasRole(ROLE_USER.toString());

        urlRegistry.antMatchers(Page.ARTIST_ADD_URL).hasRole(ROLE_ADMIN.toString());
        urlRegistry.antMatchers(Page.GENRE_ADD_URL).hasRole(ROLE_ADMIN.toString());
        urlRegistry.antMatchers(Page.TRACK_ADD_URL).hasRole(ROLE_ADMIN.toString());

        urlRegistry.antMatchers(Rest.ARTISTS_URL + ANY).permitAll();
        urlRegistry.antMatchers(Rest.GENRES_URL + ANY).permitAll();
        urlRegistry.antMatchers(Rest.TRACKS_URL + ANY).permitAll();
        urlRegistry.antMatchers(Rest.UNITS_URL + ANY).permitAll();
        urlRegistry.antMatchers(Rest.UPLOAD_URL + ANY).permitAll();
        urlRegistry.antMatchers(Rest.USERS_URL + ANY).permitAll();

        urlRegistry.anyRequest().authenticated();
        urlRegistry.and().formLogin().loginPage(Page.LOGIN_URL).permitAll();

        urlRegistry.and().httpBasic().authenticationEntryPoint(http403ForbiddenEntryPoint());
        urlRegistry.and().csrf().csrfTokenRepository(csrfTokenRepository());
        urlRegistry.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

}
