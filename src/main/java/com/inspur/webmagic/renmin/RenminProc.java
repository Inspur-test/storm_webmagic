package com.inspur.webmagic.renmin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class RenminProc  implements  PageProcessor {
	private Site site = Site.me().setRetryTimes(10).setSleepTime(1500).setTimeOut(30000);
	Set<String> url_set=new HashSet<String>();
	List<String> url_list=new ArrayList<String>();
	@Override
	public void process(Page page) {
		url_list=page.getHtml().links().regex("http://104\\.236\\.138\\.128/thread\\.php\\?fid=.*").all();
		url_set.addAll(url_list);
		url_list.clear();
		url_list.addAll(url_set);
		url_set.clear();
		page.addTargetRequests(url_list);
		page.putField("title", page.getHtml().xpath("//div[@style='margin:3px auto']/table/tbody/tr/td[@style='text-align:left;padding-left:8px']/h3/a/text()").all());
		page.putField("child_url", page.getHtml().xpath("//div[@style='margin:3px auto']/table/tbody/tr/td[@style='text-align:left;padding-left:8px']/h3/a").links().all());
		page.putField("time", page.getHtml().xpath("//div[@style='margin:3px auto']/table/tbody/tr/td[@class='tal y-style']/div[@class='f10']/text()").all());
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
}
