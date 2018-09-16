package spring.auth.sample.service;

import spring.auth.sample.etc.BaseResult;
import spring.auth.sample.model.User;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@RestController
@RequestMapping(value = "/user")
public interface UserService extends UserDetailsService {

    @PostMapping(value = "/login")
    public BaseResult<User> login(@RequestBody User user);

    @PostMapping(value = "/logout")
    public BaseResult<User> logout();

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public BaseResult<User> save(@RequestBody User user);

    @PostMapping(value = "/delete")
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public BaseResult<User> delete(@RequestBody User user);

    @PostMapping(value = "/list")
    @PreAuthorize("hasAuthority('USER_LIST')")
    public List<User> list();

    @PostMapping(value = "/changePassword")
    @PreAuthorize("hasAuthority('USER_CHANGE_PASSWORD')")
    public BaseResult<User> changePassword(@RequestBody User user);

    @PostMapping(value = "/current")
    @PreAuthorize("hasAuthority('USER_CURRENT')")
    public BaseResult<User> current(); // Gets current user
}
