package com.inspur.storm.webmagic.renmin;

import java.util.HashSet;
import java.util.Map;

import com.inspur.mysql.MysqlConnection;
import com.inspur.webmagic.renmin.RenminProc;
import com.inspur.webmagic.pipeline.renmin_pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;

public class SpiderUrlsSpout extends BaseRichSpout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static SpoutOutputCollector collector;
	ResultItems resultitems;
	renmin_pipeline renmin_pipeline;
	String start_url="http://104.236.138.128/";
	String start_num;
	String url;
	RenminProc RenminProc;
	public static HashSet<String> urls;
	public static MysqlConnection mysql;
	@SuppressWarnings({ "static-access", "rawtypes" })
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		start_num=conf.get("start_num").toString();
		RenminProc=new RenminProc();
		renmin_pipeline=new renmin_pipeline();
		mysql=new MysqlConnection();
		mysql.initSQLLink(null, null, null);
		this.urls=new HashSet<String>();
		this.collector=collector;
				}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		Spider.create(RenminProc)
		.addPipeline(renmin_pipeline)
		.addUrl(start_url)
		.thread(Integer.parseInt(start_num))
		.run();	
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("urls"));
	}

}
