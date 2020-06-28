package bookcrawler.kafka.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import bookcrawler.common.message.ScrapingMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaCrawlerListener {

	@KafkaListener(topics = "crawler-topic", containerFactory = "crawlerKafkaListenerContainerFactory")
	public void listenMessage(List<byte[]> datas) {
		
		log.info("null check : {}", datas.get(0) == null);
		log.info("type check : {}", datas.get(0) instanceof byte[]);
		
		for (byte[] data : datas) {
			System.out.println(data);
		}
		
//		ScrapingMessage messages = ((ScrapingMessage) SerializationUtils.deserialize(datas));
//		log.info("received message : {}", messages);
	}

}
