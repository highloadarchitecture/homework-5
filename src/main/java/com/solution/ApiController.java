package com.solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }


    @GetMapping("/drop_table")
    public void dropTable() {
        String dropTable = "drop table users";
        jdbcTemplate.execute(dropTable);
    }

    @GetMapping("/create_table")
    public void createTable() {
        String createTable = """ 
                  CREATE TABLE users (
                  id UUID PRIMARY KEY,
                  username VARCHAR(255) NOT NULL,
                  email VARCHAR(255) NOT NULL,
                  age INTEGER)
                """;
        jdbcTemplate.execute(createTable);
    }

    @GetMapping("/fill_table")
    public void fillTable() {
        UserGenerator.generateUsers(jdbcTemplate, 100);
    }

    @GetMapping("/query_table")
    public void queryTable() {
        Random random = new Random();
        int age = random.nextInt(50) + 18;
        var users = jdbcTemplate.query(
                "SELECT id, username, email, age FROM users WHERE age = ?",
                (rs, rowNum) ->
                        new Users(rs.getString("id"), rs.getString("username"),
                                rs.getString("email"), rs.getInt("age")),
                new Object[]{age}).stream().collect(Collectors.toList());
        System.out.println(users.size());
    }

    @GetMapping("/query_and_print")
    public void queryAndPrint() {
        Random random = new Random();
        int age = random.nextInt(50) + 18;
        jdbcTemplate.query(
                        "SELECT id, username, email, age FROM users WHERE age = ?",
                        (rs, rowNum) ->
                                new Users(rs.getString("id"), rs.getString("username"),
                                        rs.getString("email"), rs.getInt("age")),
                        new Object[]{age})
                .forEach(user -> System.out.println(user));
    }

    @GetMapping("/query_table_count")
    public void queryTableCount() {
        Random random = new Random();
        int age = random.nextInt(50) + 18;
        jdbcTemplate.query(
                        "SELECT count(*) FROM users WHERE age = ?",
                        (rs, rowNum) -> rs.getInt(1),
                        new Object[]{age}
                )
                .forEach(userCount -> System.out.println(userCount));
    }
}
