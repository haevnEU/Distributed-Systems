package com.hrw.vsproject.services;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.core.credential.AzureKeyCredential;
import com.hrw.vsproject.entities.SentimentResult;
import com.hrw.vsproject.entities.Tweet;
import com.hrw.vsproject.twitter.Twitter;
import com.hrw.vsproject.twitter.UserHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentimentAnalysisService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SentimentAnalysisService.class);

	private final Twitter twitter;
	private TextAnalyticsClient client;

	//Login datas to azure sentiment analysis
	public SentimentAnalysisService(Twitter twitter){
		this.twitter = twitter;

		String ENDPOINT = "https://vs-sentiment-analysis.cognitiveservices.azure.com/";
		String KEY = "4d145b62a6554128bb7ded3396d939ef";
		client = authenticateClient(KEY, ENDPOINT);
	}

	/*
	 * Organize all tweets in a List.
	 * Give every tweet a Sentiment Analysis result(positive, neutral, negative).
	 * 
	 */ 
	public SentimentResult calculateSentiment() {

		twitter.request();
		List<Tweet> result = twitter.getTweets();


		double positives = 0;
		double neutrals = 0;
		double negatives = 0;
		for (Tweet tweet : result) {

			DocumentSentiment documentSentiment = client.analyzeSentiment(tweet.getText());

			Double positiveConfidence = documentSentiment.getConfidenceScores().getPositive();
			Double neutralConfidence = documentSentiment.getConfidenceScores().getNeutral();
			Double negativeConfidence = documentSentiment.getConfidenceScores().getNegative();

			if(positiveConfidence > neutralConfidence && positiveConfidence > negativeConfidence) positives += 1;
			else if (neutralConfidence > positiveConfidence && neutralConfidence > negativeConfidence) neutrals += 1;
			else if (negativeConfidence > neutralConfidence && negativeConfidence > positiveConfidence) negatives += 1;


		}

		double sum = positives + negatives + neutrals;

		SentimentResult resultData = new SentimentResult();
		resultData.setPositive(calcPercentage(sum, positives));
		resultData.setNeutral(calcPercentage(sum, neutrals));
		resultData.setNegative(calcPercentage(sum, negatives));

		return resultData;
	}

	private double calcPercentage(double sum, double value){
		if(sum == 0) return 0;
		return value / sum * 100;
	}

	// Method to authenticate the client object with your key and endpoint
	static TextAnalyticsClient authenticateClient(String key, String endpoint) {
		return new TextAnalyticsClientBuilder()
				.credential(new AzureKeyCredential(key))
				.endpoint(endpoint)
				.buildClient();
	}
}
