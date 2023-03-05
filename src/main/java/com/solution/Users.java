package com.solution;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Users {
    @Id
    String id;
    String username;
    String email;
    Integer age;

    @Override
    public String toString() {
        return "User{" +
                       "id'" + id + '\'' +
                       ", username='" + username + '\'' +
                       ", email=" + email + '\'' +
                       ", age=" + age +
                       '}';
    }

    public Users() {};

    public Users(String username, String email, Integer age) {
        this.username = username;
        this.email = email;
        this.age = age;

    }
    public Users(String id,String username, String email, Integer age) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;

    }
}
