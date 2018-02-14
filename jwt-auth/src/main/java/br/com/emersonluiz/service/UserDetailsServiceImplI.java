package br.com.emersonluiz.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.emersonluiz.model.Token;
import br.com.emersonluiz.repository.TokenRepository;
import br.com.emersonluiz.repository.UserRepository;

//@Service
//@Named
public class UserDetailsServiceImplI implements UserDetailsService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private TokenRepository tokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String name = null;
        br.com.emersonluiz.model.User user = userRepository.findByUsername(username);
        if (user == null) {
            user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            } else {
                name = user.getEmail();
            }
        } else {
            name = user.getUsername();
        }

        List<Token> lastToken = tokenRepository.findFirst1ByUserId(user.getId(), new Sort(new Order(Direction.DESC, "id")));

        //verify last access:
        if (lastToken != null) {
           if (lastToken.size() > 0) {

                Date now = new Date();
                Date lastAccess = lastToken.get(0).getCreatedDate();

                long dt = (now.getTime() - lastAccess.getTime()) + 3600000;
                long dias = (dt / 86400000L);

                if (dias > 60) {
                    throw new UsernameNotFoundException("User expired!!!");
                }
            }
        }

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority("read:tasks"));

        return new User(name, user.getPassword(), setAuths);
    }
}
