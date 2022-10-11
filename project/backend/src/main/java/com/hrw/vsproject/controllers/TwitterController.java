package com.hrw.vsproject.controllers;

import com.hrw.vsproject.entities.Tweet;
import com.hrw.vsproject.entities.User;
import com.hrw.vsproject.twitter.Twitter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * This class is a controller to interact with the twitter api
 */
@RestController()
@CrossOrigin(origins = "https://hrw-vs-app.herokuapp.com/")
@RequestMapping("/twitter")
public class TwitterController {
    private final Twitter twitter;
    public TwitterController(Twitter twitter){
        this.twitter = twitter;
    }

    /**
     * This is a endpoint test
     * @return ACK message
     */
    @Operation(summary = "Testet den TwitterController.", description = "Dieser Endpunkt ist dafür da um die Erreichbarkeit des Twitterendpunkts zu testen.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Es wird immer 200 OK zurück gegeben",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    })
    })
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Twitter endpoint is reachable");
    }

    /**
     * Searches Twitter for tweets from a given comma separated lsit of user IDs.<br>
     * Utilizes the {@link Twitter} class<br>
     * <ul>
     *     Example(s)
     *     <li>/twitter/tweets/find/by/12345678</li>
     *     <li>/twitter/tweets/find/by/12345678,567891234</li>
     * </ul>
     * @param ids Comma separated list of user IDs
     * @return List of all Tweets from the given User
     */
    @Operation(summary = "Sucht tweets eines Nutzers", description = "Dieser Endpunkt durchsucht Twitter nach den aktuellsten Tweets eines Nutzer.\n" +
            "Als Argument wird die Nutzer ID erwartet.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste mit allen Tweets die zu dem übergebenen Nutzer gehören.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    })
    })
    @GetMapping("/tweets/findby/user/{ids}")
    public ResponseEntity<List<Tweet>> findTweetsByUser(@PathVariable String ids) {
        var result = twitter.getTweetHandle().requestTweetsByIDs(ids.split(","));
        return ResponseEntity.ok(result);
    }

    /**
     * Searches Twitter for tweets with given comma separated hashtags.<br>
     * Utilizes the {@link Twitter} class<br>
     * <ul>
     *     Example(s)
     *     <li>/twitter/tweets/find/by/hashtag/$Waves</li>
     *     <li>/twitter/tweets/find/by/hashtag/$Waves,$USD</li>
     * </ul>
     * @param tags Comma separated hashtags used for searching
     * @return List of all Tweets from the given User
     */
    @GetMapping("/tweets/findby/tags/{tags}")
    @Operation(summary = "Sucht tweets anhand einer Entität", description = "Dieser Endpunkt durchsucht Twitter nach den aktuellsten Tweets einer Entität.\n" +
            "Eine Entität kann unter anderem ein Hashtag darstellen aber auch ein Cashtag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste mit allen Tweets die zu der Entität gehören.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    })
    })
    public ResponseEntity<List<Tweet>> findTweetsBy(@PathVariable String tags) {
        var result = twitter.getTweetHandle().requestTweetsByHashtags(tags.split(","));
        return ResponseEntity.ok(result);
    }

    /**
     * Searches Twitter for a user based on his/her ID.<br>
     * Utilizes the {@link Twitter} class<br>
     * <ul>
     *     Example(s)
     *     <li>/twitter/user/find/by/id/123456789</li>
     * </ul>
     * @param id User ID which should be used for searching
     * @return Information about a twitter user
     * @throws IOException If an I/O exception occurred
     * @throws InterruptedException If the thread is interrupted
     */
    @GetMapping("/user/findby/id/{id}")
    @Operation(summary = "Sucht ein Nutzer anhand seiner userID", description = "Dieser Endpunkt durchsucht Twitter nach einem Nutzer anhand seiner nutzer id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Nutzer information die zu der übergebenen Nutzer ID gehören",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "Es konnte kein Nutzer zur übergebenen Nutzer ID gefunden werden.")})
    public ResponseEntity<User> findUserById(@PathVariable String id) throws IOException, InterruptedException {
        var result = twitter.getUserHandle().findUserByID(id).getData();
        if(null == result){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Searches Twitter for a user based on his/her username.<br>
     * Utilizes the {@link Twitter} class<br>
     * <ul>
     *     Example(s)
     *     <li>/twitter/user/find/by/username/username</li>
     * </ul>
     * @param name Username which should be used for searching
     * @return Information about a twitter user
     * @throws IOException If an I/O exception occurred
     * @throws InterruptedException If the thread is interrupted
     */
    @GetMapping("/user/findby/username/{name}")
    @Operation(summary = "Sucht ein Nutzer anhand seines Nutzernames", description = "Dieser Endpunkt durchsucht Twitter nach einem Nutzer anhand seines Nutzernames.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Nutzer information die zu dem übergebenen Nutzernamen gehören",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "Es konnte kein Nutzer für den geforderten Nutzer gefunden werden.")})
    public ResponseEntity<User> findUserByName(@PathVariable String name) throws IOException, InterruptedException {
        var result = twitter.getUserHandle().findUserByUsername(name).getData();
        if(null == result){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
