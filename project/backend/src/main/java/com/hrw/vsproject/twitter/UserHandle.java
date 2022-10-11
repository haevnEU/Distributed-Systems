package com.hrw.vsproject.twitter;

import com.hrw.vsproject.entities.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import static com.hrw.vsproject.twitter.Twitter.OBJECT_MAPPER;

/**
 * This class handles the interaction with the Twitter user endpoint.<br>
 * Two methods are provided.
 * <ul>
 *     <li>{@link UserHandle#findUserByUsername}: Searches a for user by its username</li>
 *     <li>{@link UserHandle#findUserByID(String)}: Searches for a user by its user id</li>
 * </ul>
 */
public class UserHandle {

    /**
     * This internal class is a "dummy" class which servers as a compiler check.<br>
     * Without this class the compiler will generate a warning about unchecked or raw use of a generic.<br>
     */
    private static final class TwitterUserObject extends TwitterObject<User>{}

    /**
     * Gets information about a Twitter user.<br><br>
     * Access the Twitter API via {@link Twitter#getDataFromTwitter} and returns the result as a {@link TwitterObject}
     * with {@link User} as parameter.<br>
     * @param username Username which is used as lookup
     * @return {@link TwitterObject} which contains information about an {@link User}
     * @throws IOException If an I/O error occurs when sending or receiving
     * @throws InterruptedException If the operation is interrupted
     */
    public TwitterObject<User> findUserByUsername(String username) throws IOException, InterruptedException {
        String url = "https://api.twitter.com/2/users/by/username/%s?user.fields=id,name,username";
        String data = Twitter.getDataFromTwitter(String.format(url, username));
        return OBJECT_MAPPER.readValue(data, TwitterUserObject.class);
    }

    /**
     * Gets information about a Twitter user.<br><br>
     * Access the Twitter API via {@link Twitter#getDataFromTwitter} and returns the result as a {@link TwitterObject}
     * with {@link User} as parameter.<br>
     * @param id User id which is used for the lookup
     * @return {@link TwitterObject} which contains information about an {@link User}
     * @throws IOException If an I/O error occurs when sending or receiving
     * @throws InterruptedException If the operation is interrupted
     */
    public TwitterObject<User> findUserByID(String id) throws IOException, InterruptedException {
        String url = "https://api.twitter.com/2/users/%s?user.fields=id,name,username";
        String data = Twitter.getDataFromTwitter(String.format(url, id));
        return OBJECT_MAPPER.readValue(data, TwitterUserObject.class);
    }

    /**
     * This method searches for the user id of a given username.
     * @param username Username which should for the lookup
     * @return ID corresponding to the username
     * @throws IOException If an I/O error occurs when sending or receiving
     * @throws InterruptedException If the operation is interrupted
     */
    public String findUserIDFor(String username) throws IOException, InterruptedException {
        return findUserByUsername(username).getData().getId();
    }

    /**
     * This method searches for the username of a given user id.
     * @param id User id which should for the lookup
     * @return Username corresponding to the user id
     * @throws IOException If an I/O error occurs when sending or receiving
     * @throws InterruptedException If the operation is interrupted
     */
    public String findUserNameFor(String id) throws IOException, InterruptedException {
        return findUserByID(id).getData().getName();
    }

    /**
     * This method searches for the usernames of a given user id list.<br>
     * It will return a new Map which contains the user ID as key and username as a value.<br>
     * Note. A missing user id inside the map could be the result of a non-existing username
     * @param ids IDs which should be used for the lookup
     * @return Key/Value map corresponding to the given list
     */
    public Map<String, String> findUsernamesForIDs(String ... ids){
        Map<String, String> result = new HashMap<>();

        Arrays.stream(ids).forEach(id->{
            try {
                String username = findUserIDFor(id);
                result.put(id, username);
            } catch (IOException | InterruptedException es) {
                es.printStackTrace();
            }
        });

        return  result;
    }

    /**
     * This method searches for the user Ids of a given username list.<br>
     * It will return a new Map which contains the username as key and the user ID as value.<br>
     * Note. A missing username inside the map could be the result of a non-existing user ID
     * @param usernames Usernames which should be used for the lookup
     * @return Key/Value map corresponding to the given list
     */
    public Map<String, String> findIdsForUsernames(String ... usernames){
        Map<String, String> result = new HashMap<>();

        Arrays.stream(usernames).forEach(username->{
            try {
                String id = findUserNameFor(username);
                result.put(username, id);
            } catch (IOException | InterruptedException es) {
                es.printStackTrace();
            }
        });

        return  result;
    }
}
