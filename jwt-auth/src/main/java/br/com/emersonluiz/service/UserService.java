package br.com.emersonluiz.service;

import static br.com.emersonluiz.security.SecurityConstants.EXPIRATION_TIME;
import static br.com.emersonluiz.security.SecurityConstants.SECRET_RECOVER;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.emersonluiz.exception.ExpiredException;
import br.com.emersonluiz.exception.NotFoundException;
import br.com.emersonluiz.mail.EmailRecover;
import br.com.emersonluiz.mail.EmailService;
import br.com.emersonluiz.mail.Mail;
import br.com.emersonluiz.model.Token;
import br.com.emersonluiz.model.User;
import br.com.emersonluiz.repository.TokenRepository;
import br.com.emersonluiz.repository.UserRepository;

@Named
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private TokenRepository tokenRepository;

    @Inject
    private EmailService emailService;

    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${spring.mail.username}")
    String emailFrom;

    @Value("${recover.url}")
    String recoverUrl;

    @Value("${img.url}")
    String imgUrl;

    public User loadUserByUserName(String username) throws ExpiredException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User name not found");
            }
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
                    throw new ExpiredException("User expired!!!");
                }
            }
        }

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority("read:tasks"));

        return user;
    }

    public void updatePassword(String username, User user) throws NotFoundException {
        User userFound = getUser(username);
        userFound.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(userFound);
    }

    public void recover(String username) throws NotFoundException {
        User user = getUser(username);
        sendEmailRecover(user, generateTokenRecover(username));
    }

    private User getUser(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = userRepository.findByEmail(username);
            if (user == null) {
                throw new NotFoundException("Username: " + username + " not found!");
            }
        }
        return user;
    }

    private String generateTokenRecover(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_RECOVER.getBytes())
                .compact();

        String t = Base64.getUrlEncoder().encodeToString(token.getBytes());
        return t;
    }

    private void sendEmailRecover(User user, String token) {
        Mail mail = new Mail();
        mail.setFrom(emailFrom);
        String[] to = {user.getEmail()};
        mail.setTo(to);
        mail.setSubject("Password Recover");
        mail.setText(EmailRecover.recoverPassword(user, recoverUrl + token, imgUrl));

        try {
            emailService.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("Error on send alert email");
        }
    }
}
