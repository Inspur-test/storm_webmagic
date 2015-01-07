package com.inspur.util;

import java.sql.PreparedStatement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.inspur.mysql.MysqlConnection;


public class Handle_Taobao {
	
	public void Handle_Info(String contents ,MysqlConnection mysql){
		contents=contents.replaceAll("\\[", "");
		String[] content=contents.split("];");
		try {
			for(int i=0;i<content.length;i++){
				if(content[i].contains("</li>")){
					String[] temp=content[i].split(", ");
					for(int j=0;j<temp.length;j++){
					if(!(temp[j].isEmpty())){
					Document doc=Jsoup.parse(temp[j]);
					Element shop_name=doc.select("a").first();
					Element shop_address=doc.select("span.shop-address").first();
					Element shop_owner=doc.select("span.shop-info-list > a").first();
					String shop_rate=doc.select("h4 > a").get(1).attr("href");
					String main_cat=doc.select("p.main-cat > a").text();
					String good_comt=doc.select("div.good-comt").text();
					good_comt=good_comt.substring(good_comt.indexOf(":")+1, good_comt.length());
					String sql="insert into Taobao_Shop_Info values(?,?,?,?,?,?,?)";
					PreparedStatement ps=mysql.getConnection().prepareStatement(sql);
					ps.setString(1, shop_name.text());
					ps.setString(2, shop_name.attr("href"));
					ps.setString(3, shop_address.text());
					ps.setString(4, shop_owner.text());
					ps.setString(5, shop_rate);
					ps.setString(6, main_cat);
					ps.setString(7, good_comt);
					ps.executeUpdate();
					System.out.println(shop_name.text());
					System.out.println(shop_name.attr("href"));
					System.out.println(shop_address.text());
					System.out.println(shop_owner.text());
					System.out.println(shop_rate);
					System.out.println(main_cat);
					System.out.println(good_comt);
					System.out.println("----------------------------------------");
				}
			
			}
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

