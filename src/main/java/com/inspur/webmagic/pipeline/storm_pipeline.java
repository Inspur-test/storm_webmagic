package com.inspur.webmagic.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class storm_pipeline implements Pipeline {
	private List<Object> resultItems=new ArrayList<Object>();
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		
		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            this.resultItems.add(entry.getValue());
        }
	}
	public List<Object> getResultItems(){
		return resultItems;
	}
	public void clear(){
		resultItems.clear();
	}
}
