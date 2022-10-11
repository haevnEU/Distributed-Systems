package com.hrw.vsproject.twitter;

import com.hrw.vsproject.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class UserHandleTest {

    private static final UserHandle handle = new UserHandle();
    private static final User USER_SHOULD_BE_VALID = new User();
    private static final User USER_SHOULD_BE_INVALID = new User();

    @BeforeAll
    public static void setup(){
        USER_SHOULD_BE_VALID.setUsername("EuHaevn");
        USER_SHOULD_BE_VALID.setId("1185703510312140800");
        USER_SHOULD_BE_VALID.setName("haevnEU");

        USER_SHOULD_BE_INVALID.setUsername("EuHaevn");
        USER_SHOULD_BE_INVALID.setId("118570351031214080F");
        USER_SHOULD_BE_INVALID.setName("haevnEU");
        new Twitter();
    }

    @Test
    void findUserByUsername() throws IOException, InterruptedException {
        User actual = handle.findUserByUsername("EuHaevn").getData();
        Assertions.assertEquals(USER_SHOULD_BE_VALID,actual);
    }

    @Test
    void findUserByID() throws IOException, InterruptedException {
        User actual = handle.findUserByID("1185703510312140800").getData();
        Assertions.assertEquals(USER_SHOULD_BE_VALID,actual);
    }


    @Test
    void findUserByUsernameShouldFail() throws IOException, InterruptedException {
        User actual = handle.findUserByUsername("EuHaevn").getData();
        Assertions.assertNotEquals(USER_SHOULD_BE_INVALID,actual);
    }

    @Test
    void findUserByIDShouldFail() throws IOException, InterruptedException {
        User actual = handle.findUserByID("1185703510312140800").getData();
        Assertions.assertNotEquals(USER_SHOULD_BE_INVALID,actual);
    }


    @Test
    void findUserNameFor() throws IOException, InterruptedException {
        String id = USER_SHOULD_BE_VALID.getId();
        var result = handle.findUserNameFor(id);
        Assertions.assertEquals(USER_SHOULD_BE_VALID.getName(), result);
    }

    @Test
    void findUserNameForShouldFail() throws IOException, InterruptedException {
        String id = USER_SHOULD_BE_VALID.getId();
        var result = handle.findUserNameFor(id);
        Assertions.assertNotEquals("NON_EXISTING_USER_NAME", result);
    }

    @Test
    void findUserIDFor() throws IOException, InterruptedException {
        String name = USER_SHOULD_BE_VALID.getUsername();
        var result = handle.findUserIDFor(name);
        Assertions.assertEquals(USER_SHOULD_BE_VALID.getId(), result);
    }

    @Test
    void findUserIDForShouldFail() throws IOException, InterruptedException {
        String id = USER_SHOULD_BE_VALID.getName();
        var result = handle.findUserIDFor(id);
        Assertions.assertNotEquals(USER_SHOULD_BE_INVALID.getId(), result);
    }
}