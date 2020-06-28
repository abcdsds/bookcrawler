package bookcrawler.kafka.producer;

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
public class KafkaCrawlerService {

	private final String topicName = "crawler-topic";
	
	private final KafkaTemplate<String, byte[]> kafkaTemplate;

	public static KafkaCrawlerService kafkaService;


	public void sendMessage(ScrapingMessage scrapingMessage) {
		
		ListenableFuture<SendResult<String, byte[]>> future = kafkaTemplate.send(topicName, SerializationUtils.serialize(scrapingMessage));

//		future.addCallback(new ListenableFutureCallback<SendResult<String, byte[]>>() {
//
//			@Override
//			public void onSuccess(SendResult<String, byte[]> result) {
//				System.out.println(
//						"Sent message=[" + scrapingMessage + "] with offset=[" + result.getRecordMetadata().offset() + "]");
//			}
//
//			@Override
//			public void onFailure(Throwable ex) {
//				System.out.println("Unable to send message=[" + scrapingMessage + "] due to : " + ex.getMessage());
//			}
//		});
	}

	
	@PostConstruct
	public void init() {
		this.kafkaService = this;
	}
	
}
