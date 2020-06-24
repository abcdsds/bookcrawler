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

public class CrawlerMunpia extends WebCrawler {

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

		System.out.println("========== checked ==============");
		System.out.println(checked);
		System.out.println("========== checked ==============");

		Optional<Element> findFirst = doc.select("a.ma").stream().filter(s -> s.attr("title").equals("끝쪽")).findFirst();

		String defaultUrl = webURL.getURL().split("gpage\\/")[0];
		String attr = findFirst.get().attr("href").split("gpage\\/")[1];
		int pages = Integer.parseInt(attr);

		if (checked == false) {

			for (int i = 1; i <= 2; i++) {

				this.myController.addSeed(defaultUrl + i);
			}

			checked = true;
		}

		if (webURL.getURL().contains("pl.serial")) {
			String content = doc.select("a.title").html();

			System.out.print(content.replace("\n", ""));
			System.out.println("========================");
			
		} else {
			
			String title = doc.select("div.detail-box").select("h2").select("a").text();
			System.out.println(title);
			String author = doc.select("div.detail-box").select("dl.meta-author").select("a").text();
			System.out.println(author);
			String startedAt = doc.select("div.detail-box").select("dl.meta-etc").get(0).select("dd").get(0).text();
			String episodeCounts = doc.select("div.detail-box").select("dl.meta-etc").get(1).select("dd").get(0).text();
			String views = doc.select("div.detail-box").select("dl.meta-etc").get(1).select("dd").get(1).text();
			String suggestCounts = doc.select("div.detail-box").select("dl.meta-etc").get(1).select("dd").get(2).text();;
			
			System.out.println("작품 등록일 = " + startedAt + " &    연재시작일 = " + startedAt);
			System.out.println("연재수 = " + episodeCounts + " &   조회수 = " + views + " &    추천수 = " + suggestCounts);
			
			System.out.println("========================");
		}
		
		System.out.println(webURL.getURL());
		
		if (webURL.getURL().equals(defaultUrl + pages)) {
			this.myController.shutdown();
		}

		System.out.println(this.myController.getPageFetcher().toString());
		// super.visit(page);
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		// TODO Auto-generated method stub
		// return super.shouldVisit(referringPage, url);

		return url.getURL().startsWith("https://novel.munpia.com/");
	}

}
