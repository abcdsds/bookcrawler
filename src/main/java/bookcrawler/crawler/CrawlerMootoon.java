package bookcrawler.crawler;

import static bookcrawler.kafka.producer.KafkaCrawlerService.kafkaService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import bookcrawler.common.message.ScrapingMessage;
import bookcrawler.common.message.SiteType;
import bookcrawler.crawler.config.CrawlerSiteSetting;
import bookcrawler.crawler.config.CrawlerSiteType;
import bookcrawler.kafka.producer.KafkaCrawlerService;
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

	private Set<String> shoudVisitURLs = new HashSet<String>();
	private Set<String> visitURLs = new HashSet<String>();
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
		
		Elements select = doc.select("a[href^=/nov/nov_list.mg]");
		
		select.forEach(v -> {
			
			if (visitURLs.contains(v.attr("href"))) {
				visitURLs.add(v.attr("href"));
			} else {
				
				System.out.println(webURL.getURL());
				System.out.println("==== URL ==========");
				System.out.println(v.attr("href"));
				
				kafkaService.sendMessage(new ScrapingMessage(webURL.getDomain(), v.attr("href"), SiteType.MOOTOON));
			}			
		});
		
		
//		if (webURL.getURL().contains("nov_list")) {
//			String title = doc.select("h2.fiction_title").text();
//			String description = doc.select("h3.fiction_summary").text();
//			String episodeCounts = doc.select("div.t_left_total_num").text();
//			String author = doc.select("h2.fiction_writer").text();
//			String point = doc.select("div.fiction_fav").select("span.spn_point").text();
//			
//			System.out.println(title);
//			System.out.println(description);
//			System.out.println(episodeCounts);
//			System.out.println(author);
//			System.out.println(point);
//		}

//		if (webURL.getURL().contains("nov_list")) {
//			kafkaService.sendMessage(new ScrapingMessage("domain", "link", SiteType.MUNPIA));
//		}
		// super.visit(page);
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		// TODO Auto-generated method stub
		// return super.shouldVisit(referringPage, url);
		
		
		if (shoudVisitURLs.contains(url.getURL())) {
			return false;
			
		}
		
		shoudVisitURLs.add(url.getURL());
		//return url.getURL().startsWith("https://www.mootoon.co.kr/nov/genre_list.mg") || url.getURL().startsWith("https://www.mootoon.co.kr/nov/nov_list.mg");
		return url.getURL().startsWith("https://www.mootoon.co.kr/nov/genre_list.mg");
	}

}
