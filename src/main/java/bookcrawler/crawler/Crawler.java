package bookcrawler.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

@Component
public class Crawler extends WebCrawler {

	@Override
	public void visit(Page page) {
		// TODO Auto-generated method stub

		HtmlParseData parseData = (HtmlParseData) page.getParseData();

		String html = parseData.getHtml();

		Document doc = Jsoup.parse(html);

		String content = doc.select("a.title").html();

		System.out.println(content);

		// super.visit(page);
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		// TODO Auto-generated method stub
		// return super.shouldVisit(referringPage, url);

		return url.getURL().startsWith("https://novel.munpia.com/");
	}

	
	public void run(String url) throws Exception {
		int numberOfCrawlers = 1;

		CrawlConfig config = new CrawlConfig();
		config.setMaxDepthOfCrawling(1);
		config.setPolitenessDelay(5000);
		config.setCrawlStorageFolder("data/crawl");

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		controller.addSeed("https://novel.munpia.com/page/novelous/group/pl.serial/gpage/1");

		controller.start(Crawler.class, numberOfCrawlers);
	}
}
