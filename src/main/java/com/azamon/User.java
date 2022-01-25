package com.azamon;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "et_users")
@Table(name = "et_users")
public class User extends Person{
    public User() {
    }

    public User(String name, String surname, String email, String password, String secret_key, String iv) {
        super(name, surname, email, password, secret_key, iv);
    }
}
