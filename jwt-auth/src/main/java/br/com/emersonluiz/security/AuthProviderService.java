package br.com.emersonluiz.security;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.emersonluiz.exception.ExpiredException;
import br.com.emersonluiz.model.User;
import br.com.emersonluiz.service.UserService;

@Component
public class AuthProviderService implements AuthenticationProvider {

    @Inject
    private UserService userService;

    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password =  auth.getCredentials().toString();

        User user;
        try {
            user = userService.loadUserByUserName(username);

            if(bCryptPasswordEncoder.matches(password, user.getPassword())) {
                Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
                auths.add(new SimpleGrantedAuthority("read:tasks"));

                return new UsernamePasswordAuthenticationToken(user, null, auths);
            } else {
                throw new BadCredentialsException("Invalid Password");
            }

        } catch (ExpiredException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
