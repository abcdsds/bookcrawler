package bookcrawler.crawler;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;

public class CrawlerRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		CrawlConfig config = new CrawlConfig();
		config.setMaxDepthOfCrawling(1);
		config.setPolitenessDelay(500);
		config.setCrawlStorageFolder("data/crawl");
		
		//PageFetcher pageFetcher
	}

}
