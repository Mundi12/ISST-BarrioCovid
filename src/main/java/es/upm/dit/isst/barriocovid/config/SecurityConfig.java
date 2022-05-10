package es.upm.dit.isst.barriocovid.config;


import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    DataSource ds;

    @Autowired
    public void WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(ds)
        .usersByUsernameQuery("select nombre as username , contraseña as password , 'true' as enabled from usuarios where nombre=?")
        .authoritiesByUsernameQuery("select username, authority from authorities where username=?");
    }

    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //Define who can access the resources
        .antMatchers("/misproductos/**").hasAnyRole("VEND")
        .antMatchers("/").permitAll()
        .antMatchers("/comprador/**","/carrito","/verCarrito","/borrarCarrito/*","/perfil/*").hasAnyRole("COMP")
        .antMatchers("/voluntario/**").hasAnyRole("VOL")
        .antMatchers("/correoEnviado","/contacto").hasAnyRole("VOL", "VEND","COMP")
        .anyRequest().authenticated().and()
        .formLogin().successHandler(authenticationSuccessHandler).permitAll().and()
        .logout().permitAll().and()
        .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
        .ignoring()
        .antMatchers("/h2/*");
    }

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    
   
}