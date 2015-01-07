package com.inspur.storm.webmagic.renmin;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;


public class TopologyMain {
	public static void main(String[] args) throws Exception{
		TopologyBuilder builder=new TopologyBuilder();
		builder.setSpout("SpiderUrlsSpot",new SpiderUrlsSpout(),1);
		builder.setBolt("ChildUrlsHandle", new ChildUrlsHandle(),17).fieldsGrouping("SpiderUrlsSpot", new Fields("urls"));
		Config conf=new Config();
		conf.put("start_num", args[0]);
//		conf.setDebug(true);
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 50);
		conf.setNumWorkers(22);
		conf.put(Config.TOPOLOGY_MESSAGE_TIMEOUT_SECS, 5000);
//		LocalCluster cluster=new LocalCluster();
		StormSubmitter.submitTopology("Started-Topologie-crawl", conf, builder.createTopology());
	}
}
