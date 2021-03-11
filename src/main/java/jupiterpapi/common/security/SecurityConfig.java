package jupiterpapi.common.security;

import jupiterpapi.common.ApplicationConfig;
import jupiterpapi.midgardcharacter.backend.controller.MidgardController;
import jupiterpapi.user.backend.controller.UserController;
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

    @Autowired
    ApplicationConfig applicationConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//.formLogin()//.loginPage("/login")
                .rememberMe().tokenValiditySeconds(2419200).key(applicationConfig.getSecurityKey())

                .and().logout().deleteCookies("JSESSIONID")//.logoutSuccessUrl("/")

                .and().httpBasic().realmName(applicationConfig.getName()).and().sessionManagement().disable()

                .authorizeRequests()

                .antMatchers("/login").permitAll().antMatchers("/logout").permitAll()

                .antMatchers(HttpMethod.GET, UserController.PATH).hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, UserController.PATH).permitAll()

                .antMatchers(HttpMethod.GET, MidgardController.PATH + "**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, MidgardController.PATH + "/learning/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, MidgardController.PATH + "/levelUp/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, MidgardController.PATH + "**")
                .hasAnyAuthority("ADMIN", "USER")  //Preliminary

                .anyRequest().authenticated().and().csrf().disable()

        ;

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

