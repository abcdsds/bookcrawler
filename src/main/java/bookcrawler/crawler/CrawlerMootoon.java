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

public class CrawlerMootoon extends WebCrawler {

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
		
		if (webURL.getURL().contains("nov_list")) {
			String title = doc.select("h2.fiction_title").text();
			String description = doc.select("h3.fiction_summary").text();
			String episodeCounts = doc.select("div.t_left_total_num").text();
			String author = doc.select("h2.fiction_writer").text();
			String point = doc.select("div.fiction_fav").select("span.spn_point").text();
			
			System.out.println(title);
			System.out.println(description);
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
			
		
		return url.getURL().startsWith("https://www.mootoon.co.kr/nov/genre_list.mg") || url.getURL().startsWith("https://www.mootoon.co.kr/nov/nov_list.mg");
	}

}
