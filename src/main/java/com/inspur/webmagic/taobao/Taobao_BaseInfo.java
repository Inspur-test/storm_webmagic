package com.inspur.webmagic.taobao;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class Taobao_BaseInfo implements PageProcessor {

    private Site site;

    @Override
    public void process(Page page) {
        List<String> flag=page.getHtml().xpath("//div[@id='J_ShopList']/div[@id='list-content']/ul[@id='list-container']/li[@class='list-item']/ul[@class='clearfix']/li[@class='list-info icon-5zhe']").all();
        if(flag.isEmpty()){
				try {
					Thread.sleep(180000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        page.putField("base_info", page.getHtml().xpath("//div[@id='J_ShopList']/div[@id='list-content']/ul[@id='list-container']/li[@class='list-item']/ul[@class='clearfix']/li[@class='list-info icon-5zhe']").all());
    }

    @Override
    public Site getSite() {
        if (null == site) {
            site = Site.me().setDomain("s.taobao.com").setSleepTime(5000).setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");;
        }
        return site;
    }
}
