package br.com.emersonluiz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message="Person : name is null")
    @Length(max=60, message="Person : name the max length is 60 characters")
    private String name;

    @Length(max=50, message="Person : email the max length is 50 characters")
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
