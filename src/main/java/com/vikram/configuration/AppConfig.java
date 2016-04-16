package com.vikram.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.vikram.category.CategoryTree;
import com.vikram.db.ExpenseStore;
import com.vikram.db.KeyValueStore;
import com.vikram.db.awsdynamodb.AwsDynamoDBKeyValueStore;
import com.vikram.db.awsdynamodb.AwsExpenseStore;
import com.vikram.openidconnect.login.core.input.ICredentialInput;
import com.vikram.openidconnect.login.core.input.IOAuthCredentials;
import com.vikram.openidconnect.login.core.input.OAuthCredentials;
import com.vikram.openidconnect.login.core.providers.OAuthProvider;

@Configuration
@ComponentScan("com.vikram")
@ImportResource("classpath:META-INF/oal-core.xml")
public class AppConfig {
	
	
	@Bean
	public ExpenseStore getExpenseStore(){
		return new AwsExpenseStore();
	}
	
	@Bean
	public AmazonDynamoDB getAmazonDynamoDb(){
		AmazonDynamoDB amazonDynamoDb = new AmazonDynamoDBClient(getAwsCredentialsProvider());
		amazonDynamoDb.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));
		return amazonDynamoDb;
	}	
	
	@Bean
	public AWSCredentialsProvider getAwsCredentialsProvider(){				      
		return getStaticCredentialProvider();
	}
	
	@Bean
	public Resource getCategoryJson(){
		return new ClassPathResource("category.json");
	}

	@Bean
	public CategoryTree getCategoryLoader(){
		return new CategoryTree();
	}
	
	@Bean
	public IOAuthCredentials getOauthCredentials(){
		return new OAuthCredentials(getCredentials());
	}

		
	private List<ICredentialInput> getCredentials() {
		ICredentialInput input = new ICredentialInput() {
			
			@Override
			public String getRedirectUri() {
				return getKeyValueStore().getValue("google_redirect_uri");
			}
			
			@Override
			public OAuthProvider getProvider() {
				return OAuthProvider.GOOGLE;
			}
			
			@Override
			public String getClientSecret() {
				return getKeyValueStore().getValue("google_client_secret");
			}
			
			@Override
			public String getClientId() {
				return getKeyValueStore().getValue("google_client_id");
			}
		};
		List<ICredentialInput> credentials = new ArrayList<ICredentialInput>();
		credentials.add(input);
		return credentials;
	}

	@Bean
	public KeyValueStore getKeyValueStore(){
		return new AwsDynamoDBKeyValueStore();
	}

	
	private StaticCredentialsProvider getStaticCredentialProvider() {
				
		String accessKey = System.getProperty("AWS_ACCESS_KEY_ID");
		String secretKey = System.getProperty("AWS_SECRET_KEY");
		
		if(accessKey == null || secretKey == null){
			accessKey = System.getenv("AWS_ACCESS_KEY_ID");
			secretKey = System.getenv("AWS_SECRET_KEY"); 
		}
				
		return new StaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey));
	}

	
}
