package jupiterpapi.midgardcharacter.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//.formLogin()//.loginPage("/login")
                //    .and().logout()//.logoutSuccessUrl("/")
                //    .and()
                //			.rememberMe().tokenValiditySeconds(2419200).key("ApplName").and()
                .httpBasic()//.realmName(config.getName())
                .and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, UserController.PATH + "**")
                .hasRole("ADMIN").antMatchers(HttpMethod.POST, UserController.PATH + "**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, MidgardController.PATH + "**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, MidgardController.PATH + "/learning/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, MidgardController.PATH + "/levelUp/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, MidgardController.PATH + "**").hasRole("ADMIN").anyRequest()
                .authenticated().and().sessionManagement().disable();

    }

    @Autowired
    MongoUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

