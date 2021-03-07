package jupiterpapi.midgardcharacter.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ApplicationConfig config;
    @Autowired
    MongoUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
        Map<String, String> users = new HashMap<>();
        users.put("User1", "password1");
        users.put("User2", "password2");

        var builder = auth.inMemoryAuthentication().withUser("admin").password(config.getAdminPassword())
                .roles("USER", "ADMIN");
        ;
        for (String user : users.keySet()) {
            builder.and().withUser(user).password(users.get(user)).roles("USER");
        }
         */
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//.formLogin()//.loginPage("/login")
                //    .and().logout()//.logoutSuccessUrl("/")
                //    .and()
                //			.rememberMe().tokenValiditySeconds(2419200).key("Tagesplaner").and()
                .httpBasic()//.realmName(config.getName())
                .and().csrf().disable().authorizeRequests()
                //                .antMatchers(HttpMethod.GET, MidgardController.PATH + "/**").hasAnyRole("USER", "ADMIN")
                //                .antMatchers(HttpMethod.PUT, MidgardController.PATH + "/**").hasRole("ADMIN")
                //                .antMatchers(HttpMethod.POST, MidgardController.PATH + "/**").hasRole("ADMIN")
                .anyRequest().authenticated().and().sessionManagement().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

