package com.hrw.vsproject.twitter;

import static com.hrw.vsproject.twitter.Twitter.OBJECT_MAPPER;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hrw.vsproject.entities.Tweet;

/**
 * This class handles the interaction with the Twitter tweet endpoint.<br>
 * Two methods are provided.
 * <ul>
 *     <li>{@link TweetHandle#requestTweetsByID}: Searches all tweets by a given user id</li>
 *     <li>{@link TweetHandle#requestTweetsByIDs}}: Searches for all tweets for a couple user ids</li>
 * </ul>
 */
public class TweetHandle {

    /**
     * This internal class is a "dummy" class which servers as a compiler check.<br>
     * Without this class the compiler will generate a warning about unchecked or raw use of a generic.<br>
     */
    private static final class TwitterTweetObject extends TwitterObject<List<Tweet>>{}

    private String retweetQuery = "";
    private final List<String> tweetFields = new ArrayList<>();

    /**
     * Constructs a new {@link TweetHandle} object.<br>
     * <ul>
     * The following defaults fields will be set for a request.
     * <li>id</li>
     * <li>created_at</li>
     * <li>text</li>
     * <li>author_id</li>
     * The following query params will be set
     * <li>allowRetweet: False</li>
     * </ul>
     */
    public TweetHandle(){
        this.addTweetField("id").addTweetField("created_at").addTweetField("text").addTweetField("author_id").allowRetweet(true);
    }

    /**
     * Enables or disables the retweet search
     * @param allowed True iff retweets are allowed
     * @return Reference to this object
     */
    public TweetHandle allowRetweet(boolean allowed){
        retweetQuery = allowed ? "" : "-is%3Aretweet";
        return this;
    }

    /**
     * Clears the fields of the twitter search
     * @return Reference to this object
     */
    public TweetHandle clearFields(){
        tweetFields.clear();
        return this;
    }

    /**
     * Adds a field to the twitter search.<br>
     * If a field already exists the field is ignored
     * @param field Name of the field
     * @return Reference to this object
     */
    public TweetHandle addTweetField(String field){
        if(!tweetFields.contains(field)){
            tweetFields.add(field);
        }
        return this;
    }

    /**
     * Searches for tweets specified by a given user id.<br><br>
     * Access the Twitter API via {@link Twitter#getDataFromTwitter} and returns a {@link List} as of
     * {@link TwitterObject TwitterObjects} with {@link Tweet} as parameter.<br>
     * @param userID User ID which is used as lookup
     * @return {@link TwitterObject} which contains a {@link List} with the {@link Tweet Tweets}
     * @throws IOException If an I/O error occurs when sending or receiving
     * @throws InterruptedException If the operation is interrupted
     */
    public TwitterObject<List<Tweet>> requestTweetsByID(String userID) throws IOException, InterruptedException {
        String query = "query=from%3A" + userID + "%20" + retweetQuery;
        String fields = String.join(",", this.tweetFields);
        String url = "https://api.twitter.com/2/tweets/search/recent?" + query + "&tweet.fields=" + fields;
        String data = Twitter.getDataFromTwitter(url);
        return OBJECT_MAPPER.readValue(data, TwitterTweetObject.class);
    }

    /**
     * Searches for tweets specified by the given list of user ids.<br><br>
     * Access the Twitter API via {@link Twitter#getDataFromTwitter} and returns a {@link List} of
     * {@link Tweet Tweets} as parameter from all provided user IDs.<br>
     * @param userIDs List of user IDs which are used as lookup
     * @return {@link TwitterObject} which contains a {@link List} with all {@link Tweet Tweets}
     */
    public List<Tweet> requestTweetsByIDs(String ... userIDs){
        List<Tweet> result = new ArrayList<>();
        Arrays.stream(userIDs).forEach(id -> {
            try {
                List<Tweet> requestedTweets = requestTweetsByID(id).getData();
                if(null != requestedTweets) {
                    result.addAll(requestedTweets);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        return result;
    }


    /**
     * Searches for tweets specified by a given hashtag.<br><br>
     * Access the Twitter API via {@link Twitter#getDataFromTwitter} and returns a {@link List} as of
     * {@link Tweet Tweets} as parameter.<br>
     * @param hashtag User ID which is used as lookup
     * @return {@link TwitterObject} which contains a {@link List} with the {@link Tweet Tweets}
     * @throws IOException If an I/O error occurs when sending or receiving
     * @throws InterruptedException If the operation is interrupted
     */
    public List<Tweet> requestTweetsByHashtag(String hashtag) throws IOException, InterruptedException {
        List<Tweet> result;
        hashtag = "entity:\"" + hashtag + "\"";
        hashtag = URLEncoder.encode(hashtag, StandardCharsets.UTF_8.toString());
        String url = "https://api.twitter.com/2/tweets/search/recent?query=%s&tweet.fields=id,created_at,text,author_id";
        String data = Twitter.getDataFromTwitter(String.format(url, hashtag));

        result = OBJECT_MAPPER.readValue(data, TwitterTweetObject.class).getData();

        return result;
    }

    /**
     * Searches for tweets specified by a given list of hashtags.<br><br>
     * Access the Twitter API via {@link Twitter#getDataFromTwitter} and returns a corresponding {@link List} of
     * {@link Tweet Tweets}.<br>
     * @param hastags List of all requested hashtags
     * @return List of all tweets associated with the given hashtags
     */
    public List<Tweet> requestTweetsByHashtags(String ... hastags){
        List<Tweet> result = new ArrayList<>();
        Arrays.stream(hastags).forEach(hashtag -> {
            try {
                List<Tweet> requestedTweets = requestTweetsByHashtag(hashtag);
                if(null != requestedTweets) {
                    result.addAll(requestedTweets);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        return result;
    }
}
