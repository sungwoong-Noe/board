package simpleboard.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        .antMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()
                        .and()
                    .formLogin()
                        .loginPage("/login")
                        .permitAll()
                        .and()
                    .logout()
                        .permitAll();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth)  //설정을 하면 스프링 내부에서 알아서 인증처리를 해줌
                throws Exception {
            auth.jdbcAuthentication()
                    .dataSource(dataSource) //dataSource를 넘겨주면 스프링에서 알아서 처리해줌
                    .passwordEncoder(passwordEncoder()) //passwordEncoder를 통해 비밀번호 암호화를 알아서 해줌
                    .usersByUsernameQuery("select email,password,enabled "  //Authentication 인증 처리
                            + "from bael_users "
                            + "where email = ?")
                    .authoritiesByUsernameQuery("select email,authority "   //Authroization 권한 처리
                            + "from authorities "
                            + "where email = ?");
        }

        //Authentication 로그인 관한 설정
        //Authroization 권한 관한 설정

        @Bean
        public PasswordEncoder passwordEncoder() {  //스프링에서 PasswordEncoder 인스턴스를 알아서 관리해줌
            return new BCryptPasswordEncoder();
        }
    }

