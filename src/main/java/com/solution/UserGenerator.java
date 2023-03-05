package com.solution;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Random;
import java.util.UUID;

public class UserGenerator {
    private static final String INSERT_SQL = "INSERT INTO users (id, username, email, age) VALUES (?, ?, ?, ?)";
    private static final Random rand = new Random();

    public static void generateUsers(JdbcTemplate jdbcTemplate, int numRecords) {

        int length = 10;
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        String userEmail = sb.toString();

        for (int i = 0; i < numRecords; i++) {
            UUID id = UUID.randomUUID();
            int randomNumber = random.nextInt(1000000) + 1;
            String username = "user" + randomNumber;
            String email = "user_" + userEmail + "@example.com";
            int age = rand.nextInt(50) + 18;
            jdbcTemplate.update(INSERT_SQL, id, username, email, age);
        }
    }
}
