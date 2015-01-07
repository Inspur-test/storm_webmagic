package com.inspur.storm.webmagic.taobao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import com.inspur.webmagic.pipeline.storm_pipeline;
import com.inspur.webmagic.taobao.Taobao_BaseInfo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class HandleUrls extends BaseRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4038516304171215056L;
	OutputCollector collector;
	List<Object> urls;
	Taobao_BaseInfo BaseInfo;
	String chromedriver;
	String save_file_path;
	List<String> url_list;
	List<Object> resultItems;
	storm_pipeline storm_pipeline;
	String[] url_array;
	boolean complete=false;
	String result="";
	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		urls=new ArrayList<Object>();
		chromedriver=(String) stormConf.get("chromedriver");
		save_file_path=(String) stormConf.get("save_file_path");
		url_list=new ArrayList<String>();
		this.collector=collector;
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		urls=input.getValues();
		for(Object o:urls){
		url_array=o.toString().split(";");
		}
		
		 Spider.create(new Taobao_BaseInfo()).thread(1)
         .addPipeline(storm_pipeline=new storm_pipeline())
         .setDownloader(new SeleniumDownloader(chromedriver))
         .addUrl(url_array)
         .run();
		try {
			Thread.sleep(35000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Object> arr=storm_pipeline.getResultItems();
		if(arr.size()!=0){
			for(int i=0;i<arr.size();i++){
				result+=arr.get(i).toString()+";";
		}
			collector.emit(new Values(result));
			result="";
			arr.clear();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("resultItems"));
	}

}
