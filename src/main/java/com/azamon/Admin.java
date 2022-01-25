package com.azamon;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "et_admin")
@Table(name = "et_admin")
public class Admin extends Person {
    public Admin() {
    }

    public Admin(String name, String surname, String email, String password, String secret_key, String iv) {
        super(name, surname, email, password, secret_key, iv);
    }
}
