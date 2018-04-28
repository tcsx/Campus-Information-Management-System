package com.amazonaws.lambda.demo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

public class LambdaFunctionHandler implements RequestHandler<DynamodbEvent, String> {

	private final AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

	@Override
	public String handleRequest(DynamodbEvent input, Context context) {

		for (DynamodbStreamRecord record : input.getRecords()) {
			if (record == null || !"INSERT".equals(record.getEventName())) {
				continue;
			}
			String title = record.getDynamodb().getNewImage().get("title").getS();
			String message = record.getDynamodb().getNewImage().get("message").getS();
			String topicArn = record.getDynamodb().getNewImage().get("topicArn").getS();
			
			sendEmailNotification(topicArn, title, message);
		}

		return "sent";
	}

	private void sendEmailNotification(final String topicArn, final String subject, final String message) {
		PublishRequest publishRequest = new PublishRequest(topicArn, message, subject);
		SNS_CLIENT.publish(publishRequest);
	}
}