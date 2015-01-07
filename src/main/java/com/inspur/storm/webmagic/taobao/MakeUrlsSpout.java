package com.inspur.storm.webmagic.taobao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class MakeUrlsSpout extends BaseRichSpout 
{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 7328199544391190379L;
	private SpoutOutputCollector collector;
	int count=0;
	int temp=0;
	List<Object> urls;
	String url;
	String emit_url="";
	private boolean completed=false;
	private boolean emit_completed=false;
	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		if(completed){
			return;
		}
		this.count=Integer.parseInt((String) conf.get("urls_count"));
		this.url=(String) conf.get("start_url");
		urls=new ArrayList<Object>();
    	for(int i=0;i<=count*6;i+=6){
    		String staticurl=url;
    		staticurl+=i;
    		urls.add(staticurl);
    		temp=i;
    	}
    	if(count%6!=0&&(count%6)>6){
        	temp=temp+(count%6);
        	urls.add(url+temp);
        	}
    	
		this.collector=collector;
		completed=true;
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		for(int i=0;i<urls.size();i++){
		emit_url+=urls.get(i).toString()+";";
		if((i+1)%5==0){
		if(!emit_completed){
		System.out.println(emit_url);
		this.collector.emit(new Values(emit_url),"MakeUrlsSpout");
		emit_url="";
		}
		}
		}
		emit_completed=true;
		urls.clear();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("url"));
	}
}
