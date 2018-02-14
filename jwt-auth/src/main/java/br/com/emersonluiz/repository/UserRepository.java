package br.com.emersonluiz.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.emersonluiz.model.User;

@Named
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
