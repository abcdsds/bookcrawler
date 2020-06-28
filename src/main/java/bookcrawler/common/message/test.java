package bookcrawler.common.message;

import org.springframework.util.SerializationUtils;

public class test {

	public static void main(String[] args) {
		ScrapingMessage a = new ScrapingMessage("ddddddd" , "" , null);
		byte[] serialize = SerializationUtils.serialize(a);
		
		System.out.println("aaaaaaaaaaaaaaaa");
		System.out.println(serialize instanceof byte[]);
		
		ScrapingMessage b = (ScrapingMessage)SerializationUtils.deserialize(serialize);
		System.out.println(b.getClass());
	}
}
