package com.inspur.webmagic.renmin;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class RenminContent  implements  PageProcessor {
	private Site site = Site.me().setRetryTimes(100).setSleepTime(3000).setTimeOut(30000);

	@Override
	public void process(Page page) {
		page.putField("content", page.getHtml().xpath("//div[@id='read_tpc']/text()"));
	}
	@Override
	public Site getSite() {
		return site;
	}
}
