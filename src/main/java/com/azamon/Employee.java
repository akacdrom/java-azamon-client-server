package com.azamon;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "et_employee")
@Table(name = "et_employee")
public class Employee extends Person{
    public Employee() {
    }

    public Employee(String name, String surname, String email, String password, String secret_key, String iv) {
        super(name, surname, email, password, secret_key, iv);
    }
}
