package bookcrawler.serialize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.SerializationUtils;

import bookcrawler.common.message.ScrapingMessage;

@SpringBootTest
public class serializeTest {


	@Test
	void test() {
		ScrapingMessage a = new ScrapingMessage("ddddddd" , "" , null);
		byte[] serialize = SerializationUtils.serialize(a);
		
		System.out.println("aaaaaaaaaaaaaaaa");
		System.out.println(serialize instanceof byte[]);
		
		ScrapingMessage b = (ScrapingMessage)SerializationUtils.deserialize(serialize);
		System.out.println(b.getDomain());
	}
}
