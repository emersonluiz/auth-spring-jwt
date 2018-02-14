package br.com.emersonluiz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

/**
 * This class is responsable for the Token data
 * @author Emerson Castro ecastro@controlunion.com.br
 * @version 1.0
 */
@Entity
@Table(name="token")
public class Token {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="id_user_account")
    @Fetch(FetchMode.SELECT)
    @Where(clause="enabled=1")
    private User user;

    private String number;

    @NotNull(message="Token:created date is null")
    @Column(name="created_date", nullable=false)
    private Date createdDate;

    private boolean enabled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
