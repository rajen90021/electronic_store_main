package elctric.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import elctric.com.Security.JwtAuthenticationEntryPoint;
import elctric.com.Security.JwtAuthenticationFilter;
import lombok.Builder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {
	
	
	@Autowired
	  private UserDetailsService detailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired	
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	private final String[] PUBLIC_URL= {
			
			"/swagger-ui/**",
			"/webjars/**",
			"/swagger-resources/**",
			"/v3/api-docs",
			"/v2/api-docs"
	};

	
	@Bean
	  public DaoAuthenticationProvider daoAuthenticationProvider() {
		  
		  
		 DaoAuthenticationProvider dao= new DaoAuthenticationProvider();
		 
		 dao.setUserDetailsService(detailsService);
		 dao.setPasswordEncoder(passwordEncoder());
		 
		 return dao;
	  }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
		
		
		
	}
	
	
	@Bean
	
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		
		
		 http
		 .csrf()
		 .disable()
		 .authorizeRequests()
		  .antMatchers("/auth/login")
		  .permitAll()
		  .antMatchers("/auth/google")
		  .permitAll()
		  .antMatchers(HttpMethod.POST,"/users")
		 .permitAll()
		 .antMatchers(PUBLIC_URL)
		 .permitAll()
		 .anyRequest()
		 .authenticated()
		 .and()
		 .exceptionHandling()
		 .authenticationEntryPoint(jwtAuthenticationEntryPoint)
		 .and()
		 .sessionManagement()
		 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
	
	 @Bean
	    public FilterRegistrationBean corsFilter() {

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowCredentials(true);
//	        configuration.setAllowedOrigins(Arrays.asList("https://domain2.com","http://localhost:4200"));
	        configuration.addAllowedOriginPattern("*");
	        configuration.addAllowedHeader("Authorization");
	        configuration.addAllowedHeader("Content-Type");
	        configuration.addAllowedHeader("Accept");
	        configuration.addAllowedMethod("GET");
	        configuration.addAllowedMethod("POST");
	        configuration.addAllowedMethod("DELETE");
	        configuration.addAllowedMethod("PUT");
	        configuration.addAllowedMethod("OPTIONS");
	        configuration.setMaxAge(3600L);
	        source.registerCorsConfiguration("/**", configuration);

	        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new CorsFilter(source));
	        filterRegistrationBean.setOrder(-110);
	        return filterRegistrationBean;


	    }

}
