package bookcrawler.crawler;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import bookcrawler.crawler.config.CrawlerSiteSetting;
import bookcrawler.crawler.config.CrawlerSiteType;
import bookcrawler.crawler.config.CrawlerType;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CrawlerRunner implements ApplicationRunner {

	private final CrawlerSiteSetting siteSetting;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
				
		int numberOfCrawlers = 1;

		CrawlConfig config = new CrawlConfig();
		config.setMaxDepthOfCrawling(1);
		config.setPolitenessDelay(5000);
		//config.setMaxPagesToFetch(1000);
		config.setCrawlStorageFolder("data/crawl");

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		//controller.addSeed("https://novel.munpia.com/page/novelous/group/pl.serial/gpage/1");

		//controller.addSeed("https://ridibooks.com/new-releases/fantasy_serial?page=1");
		
		controller.addSeed("https://www.mootoon.co.kr/nov/genre_list.mg?guid=0&s=N&pg=1");
		
		controller.start(CrawlerMootoon.class, numberOfCrawlers);
		
		
	}

}
