//package bookcrawler.kafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class test implements ApplicationRunner{
//
//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
//	
//	
//	public void sendMessage(String msg) {
//	    kafkaTemplate.send(topicName, msg);
//	}
//
//
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
