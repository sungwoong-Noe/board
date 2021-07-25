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
            http    //페이지 설정
                    .authorizeRequests()    //권한이 확인되었다면 index 페이지로 이동
                        .antMatchers("/", "/account/register").permitAll()
                        .anyRequest().authenticated()
                        .and()
                    .formLogin()            //확인되지 않았다면 로그인 페이지로 이동
                        .loginPage("/account/login")
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
                    .usersByUsernameQuery("select username,password,enabled "  //Authentication 인증 처리, 사용자 정보 가져오기
                            + "from user "
                            + "where username = ?")
                    .authoritiesByUsernameQuery("select username, name "   //Authroization 권한 처리, 사용자 권한 가져오기
                            + "from user_role ur inner join user u on ur.user_id = u.id "   //user_role과 user 테이블 조인
                            +"inner join role r on ur.role_id = r.id "  //권한 매핑, user_role 테이블의 role_id가 role 테이블의 id와 동일해야함
                            + "where email = ?");
        }

        //Authentication 로그인 관한 설정
        //Authroization 권한 관한 설정

        @Bean
        public PasswordEncoder passwordEncoder() {  //스프링에서 PasswordEncoder 인스턴스를 알아서 관리해줌
            return new BCryptPasswordEncoder();
        }
    }

