package bookcrawler.crawler;

import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import bookcrawler.crawler.config.CrawlerSiteSetting;
import bookcrawler.crawler.config.CrawlerSiteType;
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
import lombok.RequiredArgsConstructor;

public class CrawlerRidiBooks extends WebCrawler {

	private boolean checked = false;

	@Override
	public void visit(Page page) {
		// TODO Auto-generated method stub

		WebURL webURL = page.getWebURL();
		System.out.println(webURL.getParentUrl());
		System.out.println(webURL.getURL());
		System.out.println(webURL.getDepth());

		HtmlParseData parseData = (HtmlParseData) page.getParseData();

		String html = parseData.getHtml();

		Document doc = Jsoup.parse(html);
		
		if (!webURL.getURL().contains("new-releases")) {
			String title = doc.select("h3.info_title_wrap").text();
			String description = doc.select("p.introduce_paragraph").text();
			String startedAt = doc.select("li.published_date_info").text();
			String episodeCounts = doc.select("span.metadata_item book_count").text();
			String author = doc.select("a.js_author_detail_link").text();
			String point = doc.select("p.buyer_score").select("span.score").text();
			
			System.out.println(title);
			System.out.println(description);
			System.out.println(startedAt);
			System.out.println(episodeCounts);
			System.out.println(author);
			System.out.println(point);
			
			
		}

		
		// super.visit(page);
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		// TODO Auto-generated method stub
		// return super.shouldVisit(referringPage, url);

		return url.getURL().startsWith("https://ridibooks.com/books");
	}

}
