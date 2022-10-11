package com.hrw.vsproject.twitter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrw.vsproject.entities.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the glue between the project and the Twitter API.<br>
 * Two handles are provided which servers different purpose.
 * <ul>
 *     <li>{@link TweetHandle}: Used as a lookup for recent tweets specified by a user</li>
 *     <li>{@link UserHandle}: Lookup method for a twitter user specified by its username or user ID</li>
 * </ul>
 * The class also provides a static method ({@link Twitter#getDataFromTwitter}) which handles the interaction with the Twitter APIs<br><br>
 * Project relevant shortcuts are also provided
 * <ul>
 *     <li>{@link Twitter#request()}: Fetches tweets from pre defined project relevant users</li>
 *     <li>{@link Twitter#tweets}: Contains tweets which are fetched by {@link Twitter#request()}</li>
 *     <li>{@link Twitter#getTweets()}: Access method for the {@link Twitter#tweets}</li>
 * </ul>
 */
@Component
public class Twitter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Twitter.class);
    /**
     * This static object is used for deserializing via the jackson library
     */
    public static final  ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Holds all project relevant tweets
     */
    private final List<Tweet> tweets = new ArrayList<>();

    /**
     * Handles twitter user interaction
     */
    private final UserHandle userHandle = new UserHandle();

    /**
     * Handles the tweet interaction
     */
    private final TweetHandle tweetHandle = new TweetHandle();

    /**
     * Access token for the twitter api, this is a bearer token.<br>
     */
    private static String token;

    /**
     * Access the associated {@link TweetHandle}
     * @return {@link TweetHandle}
     */
    public TweetHandle getTweetHandle() {
        return tweetHandle;
    }

    /**
     * Access the associated {@link UserHandle}
     * @return {@link UserHandle}
     */
    public UserHandle getUserHandle() {
        return userHandle;
    }

    /**
     * This method request all project relevant tweets and collects them into a single list of tweets.<br>
     * If the tweet list ({@link Twitter#tweets}) will be cleared after each call
     */
    public void request(){
        tweets.clear();
         tweets.addAll(tweetHandle.requestTweetsByIDs(
                "1038127541607956480",
                "707515829798182912",
                "1169574258919522304",
                "1142606887"));
        tweets.addAll(tweetHandle.requestTweetsByHashtags("$Waves", "$USDN"));
    }

    /**
     * This method is used to set the static {@link Twitter#token}.<br>
     * @param token Token of the application.properties
     */
    @Value("${twitter.token}")
    public void setPrivateName(String token) {
        Twitter.token = token;
    }

    /**
     * Access the project relevant {@link Twitter#tweets}
     * @return {@link Twitter#tweets}
     */
    public List<Tweet> getTweets() {
        return tweets;
    }

    /**
     * This is a generic Twitter API access method.<br>
     * The endpoint specifies the Twitter API endpoint which should be used. NOTE this is the full endpoint URL<br>
     * The method creates a new {@link HttpClient} and a {@link HttpRequest} which will interact with the Twitter API
     * @param endpoint Endpoint to use
     * @return JSON result as string
     * @throws IOException If an I/O error occurs when sending or receiving
     * @throws InterruptedException If the operation is interrupted
     */
    public static String getDataFromTwitter(String endpoint) throws IOException, InterruptedException {
        LOGGER.info("Request data from {}", endpoint);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create(endpoint))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}