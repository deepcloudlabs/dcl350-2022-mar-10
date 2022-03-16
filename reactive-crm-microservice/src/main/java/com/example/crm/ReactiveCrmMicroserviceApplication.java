package com.example.crm;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.reactivestreams.client.MongoClient;

@SpringBootApplication
public class ReactiveCrmMicroserviceApplication implements ApplicationRunner{

	@Autowired
	private MongoClient mongoClient;
	@Value("${dropCollections}")
	private boolean dropCollection;
	
	public static void main(String[] args) {
		SpringApplication.run(ReactiveCrmMicroserviceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (dropCollection) {
			mongoClient.getDatabase("crm")
			.getCollection("customers")
			.drop()
			.subscribe(new Subscriber<Void>() {

				@Override
				public void onSubscribe(Subscription s) {
					s.request(1);
					
				}

				@Override
				public void onNext(Void t) {
					
					
				}

				@Override
				public void onError(Throwable t) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onComplete() {
					System.out.println("Collection 'customers' has been dropped!");
					
				}
			});
			
		}
		
	}

}
