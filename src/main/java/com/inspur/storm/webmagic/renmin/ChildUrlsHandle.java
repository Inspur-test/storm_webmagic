package com.inspur.storm.webmagic.renmin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Spider;

import com.inspur.mysql.MysqlConnection;
import com.inspur.webmagic.pipeline.renmin_content_pipeline;
import com.inspur.webmagic.renmin.RenminContent;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class ChildUrlsHandle extends BaseRichBolt{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OutputCollector collector;
	RenminContent renmincontent;
	renmin_content_pipeline renmin_content_pipeline;
	HashSet<String> url_set;
	public static MysqlConnection mysql;
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		mysql=new MysqlConnection();
		mysql.initSQLLink(null, null, null);
		this.collector=collector;
		this.url_set=new HashSet<String>();
		renmin_content_pipeline=new renmin_content_pipeline();
		renmincontent=new RenminContent();
	}

	@Override
	public void execute(Tuple input) {
		if(!(input.getValues().get(0)==null)){
		String url=input.getValues().get(0).toString();
		if(url_set.size()>=50){
		List<String> url_list=new ArrayList<String>();
		url_list.addAll(url_set);
		url_set.clear();
		String[] urls=new String[url_list.size()];
		urls=url_list.toArray(urls);
		Spider.create(renmincontent)
		.thread(1)
		.addPipeline(renmin_content_pipeline)
		.addUrl(urls)
		.run();
		}else{
			url_set.add(url);
			collector.ack(input);
		}
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("content"));
	}

}
