package com.inspur.webmagic.pipeline;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import backtype.storm.tuple.Values;

import com.inspur.storm.webmagic.renmin.SpiderUrlsSpout;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class renmin_pipeline implements Pipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		Object[] entry=resultItems.getAll().entrySet().toArray();
		String title_string=entry[0].toString();
		String child_url_string=entry[1].toString();
		String time_string=entry[2].toString();
		String[] title=title_string.substring(title_string.indexOf("[")+1, title_string.indexOf("]")).split(",");
		String[] child_url=child_url_string.substring(child_url_string.indexOf("[")+1, child_url_string.indexOf("]")).split(",");
		String[] time=time_string.substring(time_string.indexOf("[")+1, time_string.indexOf("]")).split(",");
		String self_url=resultItems.getRequest().getUrl();
		String sql="insert into renmin_news_base_info values(?,?,?,?)";
		for(int i=0;i<child_url.length;i++){
			SpiderUrlsSpout.collector.emit(new Values(child_url[i].toString().trim()),child_url[i].toString().trim());
			try {
				PreparedStatement ps=SpiderUrlsSpout.mysql.getConnection().prepareStatement(sql);
				ps.setString(1, self_url);
				ps.setString(2, title[i].toString());
				ps.setString(3, child_url[i].toString().trim());
				ps.setString(4, time[i].toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("－－－－－－－－－－renmin_pipeline sql语句错误－－－－－－－－－－－－");
			}
		}
	}

}
