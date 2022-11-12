package com.homesharing_backend.security;


import com.homesharing_backend.security.jwt.AuthEntryPointJwt;
import com.homesharing_backend.security.jwt.AuthTokenFilter;
import com.homesharing_backend.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/messaging/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/room-type/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/home/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/post-detail/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/demo/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/address/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/search/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/utility/**").permitAll()
                .antMatchers("/api/manage-account/**").permitAll()
                .antMatchers("/api/manage-post/**").permitAll()
                .antMatchers("/api/services/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/report/**").permitAll()
                .antMatchers("/api/rate/**").permitAll()
                .antMatchers("/api/booking/**").permitAll()
                .antMatchers("/api/posting/**").permitAll()
                .antMatchers("/api/payment-package/**").permitAll()
                .antMatchers("/api/voucher/**").permitAll()
                .antMatchers("/api/like-dislike/**").permitAll()
                .antMatchers("/api/follow-favourite/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}