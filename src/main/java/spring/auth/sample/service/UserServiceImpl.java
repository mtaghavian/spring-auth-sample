package spring.auth.sample.service;

import spring.auth.sample.SpringTestApplication;
import spring.auth.sample.etc.BaseResult;
import spring.auth.sample.model.User;
import spring.auth.sample.repository.UserRepository;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return user;
    }

    @Override
    public BaseResult<User> save(@RequestBody User user) {
        User dbUser = userRepository.findByUsername(SpringTestApplication.getCurrentUser().getUsername());
        if (user.getId() == null) { // save a new user
            user.setPassword(SpringTestApplication.getBean(PasswordEncoder.class).encode(user.getPassword()));
        } else { // edit user
            user.setPassword(dbUser.getPassword());
        }
        user = userRepository.save(user);
        return new BaseResult<>(user, HttpStatus.OK, "");
    }

    @Override
    public BaseResult<User> changePassword(@RequestBody User user) {
        User dbUser = userRepository.findByUsername(SpringTestApplication.getCurrentUser().getUsername());
        dbUser.setPassword(SpringTestApplication.getBean(PasswordEncoder.class).encode(user.getPassword()));
        user = userRepository.save(dbUser);
        return new BaseResult<>(user, HttpStatus.OK, "");
    }

    @Override
    public BaseResult<User> delete(@RequestBody User user) {
        userRepository.deleteById(userRepository.findByUsername(user.getUsername()).getId());
        return new BaseResult<>(user, HttpStatus.OK, "");
    }

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public BaseResult<User> login(@RequestBody User user) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(authToken);
            User principal = (User) authenticate.getPrincipal();
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticate);
            HttpSession session = SpringTestApplication.getRequest().getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT_TOKEN", securityContext);
            session.setAttribute("USERNAME_TOKEN", principal.getUsername());
            return new BaseResult<>(principal, HttpStatus.OK, "");
        } catch (Exception exc) {
            return new BaseResult<>(null, HttpStatus.BAD_REQUEST, "Incorrect username and/or password");
        }
    }

    @Override
    public BaseResult<User> current() {
        return new BaseResult<>(SpringTestApplication.getCurrentUser(), HttpStatus.OK, "");
    }

    @Override
    public BaseResult<User> logout() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpSession session = SpringTestApplication.getRequest().getSession(true);
        session.invalidate();
        return new BaseResult<>(principal, HttpStatus.OK, "");
    }
}
