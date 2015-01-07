package com.inspur.webmagic.pipeline;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inspur.storm.webmagic.renmin.ChildUrlsHandle;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class renmin_content_pipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		
		String content=resultItems.get("content").toString();
		String self_url=resultItems.getRequest().getUrl();
		String sql="insert into renmin_news_content values(?,?)";
		try {
			PreparedStatement ps=ChildUrlsHandle.mysql.getConnection().prepareStatement(sql);
			ps.setString(1, self_url);
			ps.setString(2, content);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("－－－－－－－－－－－－－－ renmin_content_pipeline sql 语句错误－－－－－－－－－－－－－－－－－");
		}
	}
	
}
