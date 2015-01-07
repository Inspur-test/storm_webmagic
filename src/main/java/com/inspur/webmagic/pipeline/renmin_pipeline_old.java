package com.inspur.webmagic.pipeline;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inspur.storm.webmagic.renmin.SpiderUrlsSpout;

import backtype.storm.tuple.Values;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class renmin_pipeline_old  implements Pipeline {
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		String url=resultItems.get("child_url").toString();
		SpiderUrlsSpout.collector.emit(new Values(url),url);
		String self_url=resultItems.getRequest().getUrl();
		String title=resultItems.get("title").toString();
		String child_url=resultItems.get("child_url").toString();
		String time=resultItems.get("time").toString();
		String sql="insert into renmin_news_base_info values(?,?,?,?)";
		if(child_url!=null){
		try {
			PreparedStatement ps=SpiderUrlsSpout.mysql.getConnection().prepareStatement(sql);
			ps.setString(1, self_url);
			ps.setString(2, title);
			ps.setString(3, child_url);
			ps.setString(4, time);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("－－－－－－－－－－renmin_pipeline sql语句错误－－－－－－－－－－－－");
		}
	}
	}

}
