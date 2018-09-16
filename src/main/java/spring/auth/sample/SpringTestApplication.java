package spring.auth.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import spring.auth.sample.model.User;
import spring.auth.sample.model.Role;
import spring.auth.sample.repository.RoleRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import spring.auth.sample.repository.UserRepository;
import spring.auth.sample.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import spring.auth.sample.model.Authority;
import spring.auth.sample.repository.AuthorityRepository;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@SpringBootApplication
public class SpringTestApplication extends SpringBootServletInitializer implements ApplicationContextAware {

    private static ApplicationContext context;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
    }

    @PostConstruct
    public void init() {
        if (userRepository.findAll().isEmpty()) {
            Authority authorityUserEdit = new Authority(null, "USER_EDIT");
            authorityRepository.save(authorityUserEdit);
            Authority authorityUserList = new Authority(null, "USER_LIST");
            authorityRepository.save(authorityUserList);
            Authority authorityUserChangePassword = new Authority(null, "USER_CHANGE_PASSWORD");
            authorityRepository.save(authorityUserChangePassword);
            Authority authorityUserCurrent = new Authority(null, "USER_CURRENT");
            authorityRepository.save(authorityUserCurrent);
            Authority authorityTaskReadWrite = new Authority(null, "TASK_READ_WRITE");
            authorityRepository.save(authorityTaskReadWrite);

            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleAdmin.setAuthorities(list(authorityUserChangePassword, authorityUserCurrent, authorityUserList, authorityUserEdit, authorityTaskReadWrite));
            roleRepository.save(roleAdmin);
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleUser.setAuthorities(list(authorityUserChangePassword, authorityUserCurrent, authorityTaskReadWrite));
            roleRepository.save(roleUser);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("$2a$10$E1PzZC3oNsrGO7N7R.E5VetQfEhdTc6J3tddX3ylXqF1.Qo07zFOq"); // password=123456
            admin.setEnabled(true);
            admin.setRoles(list(roleAdmin));
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword("$2a$10$E1PzZC3oNsrGO7N7R.E5VetQfEhdTc6J3tddX3ylXqF1.Qo07zFOq"); // password=123456
            user.setEnabled(true);
            user.setRoles(list(roleUser));
            userRepository.save(user);
        }
    }

    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        UserService userService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/user/login").permitAll()
                    .anyRequest().authenticated();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(getBean(PasswordEncoder.class));
        }

        @Bean
        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManager();
        }
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
    }

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        context = ac;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static <K> List<K> list(K... args) {
        return Arrays.asList(args);
    }

}
