package br.com.emersonluiz.repository;

import java.util.List;

import javax.inject.Named;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.emersonluiz.model.Token;

@Named
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findFirst1ByUserId(long userId, Sort sort);

    Token findByNumber(String number);
}
