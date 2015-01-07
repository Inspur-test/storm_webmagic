package com.inspur.storm.webmagic.taobao;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;



public class TopologyMain {
	public static void main(String[] args) throws Exception{
		TopologyBuilder builder=new TopologyBuilder();
		builder.setSpout("MakeUrlsSpout",new MakeUrlsSpout());
		builder.setBolt("HandleUrls", new HandleUrls()).shuffleGrouping("MakeUrlsSpout");
		builder.setBolt("FinalResultItems", new HandleResultItem()).shuffleGrouping("HandleUrls");
		Config conf=new Config();
		conf.put("urls_count", args[0]);
		conf.put("chromedriver", args[1]);
		conf.put("start_url", "http://s.taobao.com/search?q=%CA%D6%BB%FA&q=%CA%D6%BB%FA&commend=all&commend=all&ssid=s5-e&ssid=s5-e&search_type=shop&search_type=shop&sourceId=tb.index&sourceId=tb.index&spm=1.7274553.1997520841.1&spm=1.7274553.1997520841.1&initiative_id=tbindexz_20141224&initiative_id=tbindexz_20141224&app=shopsearch&s=");
//		conf.put("save_file_path", args[3]);
		conf.setDebug(true);
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
//		LocalCluster cluster=new LocalCluster();
		StormSubmitter.submitTopology("Started-Topologie-crawl", conf, builder.createTopology());
//		Thread.sleep(1000);
//		cluster.shutdown();
		
	}
}
