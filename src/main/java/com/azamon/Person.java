package com.azamon;

import javax.persistence.*;

//@Entity(name = "et_restaurant")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public class Person {
    @Id
    /* IDENTITY is used because I used SERIAL in postgreSQL database. */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "secret_key")
    private String secret_key;

    @Column(name = "iv")
    private String iv;

    public Person() {
        super();
    }
    /* Constructor for add new outlet*/

    public Person(String name, String surname, String email, String password, String secret_key, String iv) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.secret_key = secret_key;
        this.iv = iv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}


