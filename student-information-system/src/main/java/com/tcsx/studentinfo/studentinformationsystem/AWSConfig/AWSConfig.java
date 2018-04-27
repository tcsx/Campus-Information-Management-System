package com.tcsx.studentinfo.studentinformationsystem.AWSConfig;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.tcsx.studentinfo.studentinformationsystem.dao")
public class AWSConfig {


	@Bean
	public AmazonDynamoDB amazonDynamoDB(ProfileCredentialsProvider amazonAWSCredentials) {
		return AmazonDynamoDBClientBuilder.standard()
		.withCredentials(amazonAWSCredentials)
		.withRegion("us-west-2")
		.build();
	}
	
	@Bean
	public AmazonSNSClient snsClient(ProfileCredentialsProvider amazonAWSCredentials) {
		return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
		.withCredentials(amazonAWSCredentials)
		.withRegion("us-west-2")
		.build();
	}

	@Bean
	public ProfileCredentialsProvider amazonAWSCredentials() {
		return new ProfileCredentialsProvider();
	}

}
