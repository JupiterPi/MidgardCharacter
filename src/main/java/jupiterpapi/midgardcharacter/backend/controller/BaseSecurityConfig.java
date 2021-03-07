package jupiterpapi.midgardcharacter.backend.controller;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class BaseSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected ApplicationConfig config;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        Map<String, String> users = new HashMap<>();
        users.put("User1", "password1");
        users.put("User2", "password2");

        var builder = auth.inMemoryAuthentication().withUser("admin").password(config.getAdminPassword())
                .roles("USER", "ADMIN");
        ;
        for (String user : users.keySet()) {
            builder.and().withUser(user).password(users.get(user)).roles("USER");
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//.loginPage("/login")
                .and().logout()//.logoutSuccessUrl("/")
                .and()
                //			.rememberMe().tokenValiditySeconds(2419200).key("Tagesplaner").and()
                .httpBasic().realmName(config.getName()).and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, MidgardController.PATH + "/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, MidgardController.PATH + "/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, MidgardController.PATH + "/**").hasRole("ADMIN").anyRequest().permitAll();
    }
}

