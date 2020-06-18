package ro.mpp.web.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ro.mpp.core.Domain.User;
import ro.mpp.core.Service.IUserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Component
public class CatalogUserDetailsService implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {
    private static final Logger log = LoggerFactory.getLogger(CatalogUserDetailsService.class);

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("LoadByUsername: {}", username);
        User user = userService.getUserByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }


        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getUsername())
        );

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), true, true, true, true,authorities
        );

    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

    }
}
