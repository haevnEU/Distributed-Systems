package com.hrw.vsproject.sentimentAnalysis;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hrw.vsproject.entities.SentimentResult;
import com.hrw.vsproject.entities.Tweet;
import com.hrw.vsproject.services.SentimentAnalysisService;
import com.hrw.vsproject.twitter.Twitter;

@RunWith(MockitoJUnitRunner.class)
public class SentimentAnalysisServiceTest {

	 @Mock
	 private Twitter twitter;
	 
	 private SentimentAnalysisService sentimentAnalysisService; 
	 
	 @Before
	 public void setUp()  {
		 sentimentAnalysisService = new SentimentAnalysisService(twitter); 
	 }
	 
	 @Test
	 public void testSA() {
		    List<Tweet> tweets = new ArrayList<>();
		    var fantasticTweet = new Tweet();
		    fantasticTweet.setText("Fantastic");
		    tweets.add(fantasticTweet);

		    var goodTweet = new Tweet();
		    goodTweet.setText("Good");
		    tweets.add(goodTweet);

		    var neutralTweet = new Tweet();
		    neutralTweet.setText("Neutral");
		    tweets.add(neutralTweet);

		    var badTweet = new Tweet();
		    badTweet.setText("Bad");
		    tweets.add(badTweet);

		 
		 Mockito.when(twitter.getTweets()).thenReturn(tweets);
		 
		 SentimentResult actual = sentimentAnalysisService.calculateSentiment();
		 
		 Assertions.assertThat(actual.getPositive()).isEqualTo(50.0);
		 Assertions.assertThat(actual.getNegative()).isEqualTo(25.0);
		 Assertions.assertThat(actual.getNeutral()).isEqualTo(25.0);
	    }
}
