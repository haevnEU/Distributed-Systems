package com.hrw.vsproject.twitter;

import com.hrw.vsproject.entities.Tweet;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class TweetHandleTest {

    private static final TweetHandle handle = new TweetHandle();
    private static final String USER_ID = "1185703510312140800";
    private static final String URL = "https://api.twitter.com/2/users/%s/tweets?tweet.fields=id,created_at,text,author_id";

    private static final Tweet TWEET_SHOULD_BE_VALID = new Tweet();
    private static final Tweet TWEET_SHOULD_BE_INVALID = new Tweet();


    @BeforeAll
    public static void setup(){

        TWEET_SHOULD_BE_VALID.setCreated_at(new Date(1644264497000L));
        TWEET_SHOULD_BE_VALID.setText("Test");
        TWEET_SHOULD_BE_VALID.setId("1490764371940814858");
        TWEET_SHOULD_BE_VALID.setAuthor_id("1185703510312140800");

        TWEET_SHOULD_BE_INVALID.setCreated_at(new Date(1644264497000L));
        TWEET_SHOULD_BE_INVALID.setText("ABC");
        TWEET_SHOULD_BE_INVALID.setId("1490764371940814858");
        TWEET_SHOULD_BE_INVALID.setAuthor_id("1185703510312140800");
        handle.allowRetweet(true);
        new Twitter();
    }

    @Test
    void requestTweetsByID() throws Exception {
        List<Tweet> actual = handle.requestTweetsByID(USER_ID).getData();
        List<Tweet> expected = new ArrayList<>();

        expected.add(TWEET_SHOULD_BE_VALID);

        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertEquals(expected.get(i), actual.get(i));
        }
    }


    @Test
    void requestTweetsByIDs() {
        List<Tweet> actual = handle.requestTweetsByIDs(USER_ID, USER_ID);
        List<Tweet> expected = new ArrayList<>();

        expected.add(TWEET_SHOULD_BE_VALID);
        expected.add(TWEET_SHOULD_BE_VALID);

        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    void requestTweetsByIDShouldFail() throws Exception {
        List<Tweet> actual = handle.requestTweetsByID(USER_ID).getData();
        List<Tweet> expected = new ArrayList<>();

        expected.add(TWEET_SHOULD_BE_INVALID);

        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertNotEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    void requestTweetsByIDsShouldFail() {
        List<Tweet> actual = handle.requestTweetsByIDs(USER_ID, USER_ID);
        List<Tweet> expected = new ArrayList<>();

        expected.add(TWEET_SHOULD_BE_INVALID);
        expected.add(TWEET_SHOULD_BE_INVALID);

        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertNotEquals(expected.get(i), actual.get(i));
        }
    }
}