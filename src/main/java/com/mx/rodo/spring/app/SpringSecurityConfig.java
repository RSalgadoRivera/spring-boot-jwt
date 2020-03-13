package com.mx.rodo.spring.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mx.rodo.spring.app.auth.filter.JWTAuthenticationFilter;
import com.mx.rodo.spring.app.auth.filter.JWTAuthorizationFilter;
import com.mx.rodo.spring.app.auth.handler.LoginSuccessHandler;
import com.mx.rodo.spring.app.auth.service.IJwtService;
import com.mx.rodo.spring.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@SuppressWarnings("unused")
	@Autowired
	private LoginSuccessHandler successHandler;

	/*
	 * @Autowired private DataSource dataSource;
	 */

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private IJwtService jwtService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar**", "/locale").permitAll()
				/*
				 * .antMatchers("/ver/**").hasAnyRole("USER")
				 * .antMatchers("/uploads/**").hasAnyRole("USER")
				 * .antMatchers("/form/**").hasAnyRole("ADMIN")
				 * .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
				 * .antMatchers("/factura/**").hasAnyRole("ADMIN")
				 */
				.anyRequest()
				.authenticated()
				/*.and()
				.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
				.permitAll()
				.and()
				.logout()
				.permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/error_403")
				*/.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

		/*
		 * builder.jdbcAuthentication() .dataSource(dataSource)
		 * .passwordEncoder(passwordEncoder)
		 * .usersByUsernameQuery("select username, password, enabled from users where username=?"
		 * )
		 * .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on(a.user_id=u.id) where username=?"
		 * );
		 */

		/*
		 * PasswordEncoder encoder = this.passwordEncoder; UserBuilder users =
		 * User.builder().passwordEncoder(encoder::encode);
		 * 
		 * builder.inMemoryAuthentication()
		 * .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		 * .withUser(users.username("rodo").password("12345").roles("USER"));
		 */
	}

}
