package com.inspur.storm.webmagic.taobao;

import java.util.List;
import java.util.Map;
import com.inspur.mysql.MysqlConnection;
import com.inspur.util.Handle_Taobao;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class HandleResultItem extends BaseRichBolt{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OutputCollector collector;
	MysqlConnection mysql;
	List<Object> result;
	String[] result_items;
	Handle_Taobao handle_taobao;
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector=collector;
		handle_taobao=new Handle_Taobao();
		this.mysql=new MysqlConnection();
		mysql.initSQLLink(null, null, null);
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		result=input.getValues();
		if(result.size()!=0){
		for(Object r:result){
			result_items=r.toString().split("];]");
			for(String s:result_items){
			handle_taobao.Handle_Info(s.toString(), mysql);
		}
		}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("FinalResultItems"));
	}

}
