package bookcrawler.kafka;

import javax.annotation.PostConstruct;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import bookcrawler.common.message.ScrapingMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaService {

	private final String topicName = "crawler-topic";
	
	private final KafkaTemplate<String, byte[]> kafkaTemplate;

	public static KafkaService kafkaService;


	public void sendMessage(ScrapingMessage scrapingMessage) {

		//byte[] serialize = SerializationUtils.serialize(scrapingMessage);
		
		//kafkaTemplate.send(topicName, SerializationUtils.serialize(scrapingMessage));
		
		ListenableFuture<SendResult<String, byte[]>> future = kafkaTemplate.send(topicName, SerializationUtils.serialize(scrapingMessage));

		future.addCallback(new ListenableFutureCallback<SendResult<String, byte[]>>() {

			@Override
			public void onSuccess(SendResult<String, byte[]> result) {
				System.out.println(
						"Sent message=[" + scrapingMessage + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Unable to send message=[" + scrapingMessage + "] due to : " + ex.getMessage());
			}
		});
	}

//	public void sendMessageToPartion(String message, int partition) {
//		kafkaTemplate.send(partionedTopicName, partition, null, message);
//	}
//
//	public void sendMessageToFiltered(String message) {
//		kafkaTemplate.send(filteredTopicName, message);
//	}
//
//	public void sendGreetingMessage(Greeting greeting) {
//		greetingKafkaTemplate.send(greetingTopicName, greeting);
//	}
	
	@PostConstruct
	public void init() {
		this.kafkaService = this;
	}
	
}
