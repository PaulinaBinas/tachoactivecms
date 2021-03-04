package com.binas.tachographcms.config;

import com.binas.tachographcms.security.AdminAuthenticationProvider;
import com.binas.tachographcms.security.SecretAuthenticationProvider;
import com.binas.tachographcms.security.SecretTokenFilter;
import com.binas.tachographcms.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AdminAuthenticationProvider adminAuthenticationProvider;

    @Autowired
    public void configureAuthenticatonManager(AuthenticationManagerBuilder builder, SecretAuthenticationProvider authenticationProvider) throws Exception {
        builder.authenticationProvider(authenticationProvider).authenticationProvider(adminAuthenticationProvider)
                .inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER");
    }

    @Order(0)
    @Configuration
    public static class SecretConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("admin")
        private AuthenticationManager authenticationManager;

        @Autowired
        private AdminAuthenticationProvider adminAuthenticationProvider;

        @Autowired
        private SecurityContextHolderConfig securityContextHolderConfig;

        @Bean(name = "admin")
        @Override
        public AuthenticationManager authenticationManagerBean() {
            return authentication -> adminAuthenticationProvider.authenticate(authentication);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/tacho/**")
                    .authorizeRequests()
                    .antMatchers("/tacho/login")
                    .permitAll()
                    .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                    .antMatchers("/tacho/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated()
                    .and()
                    .authenticationProvider(this.adminAuthenticationProvider)
                    .formLogin()
                    .loginPage("/tacho/login")
                    .loginProcessingUrl("/tacho/login")
                    .defaultSuccessUrl("/tacho/editUsers")
                    .and()
                    .logout()
                    .logoutUrl("/tacho/logout")
                    .logoutSuccessUrl("/")
                    .and()
                    .csrf()
                    .disable();
//                    .addFilterBefore(new SecretTokenFilter(authManager, secretContextStrategy), BasicAuthenticationFilter.class)
//                    .csrf()
//                    .disable();;
        }
    }

    @Order(1)
    @Configuration
    public static class HttpBasicConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("user")
        private AuthenticationManager authenticationManager;

        @Autowired
        private SecretAuthenticationProvider secretAuthenticationProvider;

        @Autowired
        private SecurityContextHolderStrategy secretContextStrategy;

        @Bean(name = "user")
        @Primary
        @Override
        public AuthenticationManager authenticationManagerBean() {
            return authentication -> secretAuthenticationProvider.authenticate(authentication);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/android/**")
                    .authorizeRequests()
                    .antMatchers("/android/user")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic()
                    .and()
                    .addFilterBefore(new SecretTokenFilter(authenticationManager, secretContextStrategy), BasicAuthenticationFilter.class)
                    .csrf()
                    .disable();;
        }
    }

//    @Order(2)
//    @Configuration
//    public static class FormLoginConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.antMatcher("/tacho/**")
//                    .authorizeRequests()
//                    .antMatchers("/tacho/login")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated()
//                    .and()
//                    .formLogin()
//                    .loginPage("/tacho/login")
//                    .loginProcessingUrl("/tacho/login")
//                    .and()
//                    .csrf()
//                    .disable();
//        }
//    }

    @Configuration
    public static class SecurityContextHolderConfig {
        @Bean
        public SecurityContextHolderStrategy securityContextHolderStrategy() {
            return SecurityContextHolder.getContextHolderStrategy();
        }
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(new AuthenticationProvider() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                return new UsernamePasswordAuthenticationToken("any principle", "any credentials",
//                        List.of(new SimpleGrantedAuthority("USER")));
//            }
//
//            @Override
//            public boolean supports(Class<?> aClass) {
//                return true;
//            }
//        });
//    }
}
