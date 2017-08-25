package manhua.shenmanhua;

import java.io.IOException;
import java.net.URLDecoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.zhaoyao.com.http.HttpUtils;

public class TestShenManHua {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
//		try {
//			Document document = Jsoup.connect("http://m.manhuatai.com/asonline/321.html").get();
//			System.out.println(document.toString());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String decode = URLDecoder.decode("http://mhpic.zymkcdn.com/comic/D/斗破苍穹拆分版/618话/$$.jpg");
////		String decode = URLDecoder.decode("http://mhpic.zymkcdn.com/comic/A%2F%E9%98%BF%E8%A1%B0ONLINE%2F274%E8%AF%9DSM%2F1.jpg-mht.middle");
//		System.out.println(decode);
		String get2String = HttpUtils.newInstance().get2String("https://api.yyhao.com/app_api/v2/getcomicinfo/?comic_id=1368");
		JSONObject jsonObject = new JSONObject(get2String);
		System.out.println(jsonObject);
		JSONArray jsonArray = jsonObject.getJSONArray("comic_chapter");
		JSONObject jsonObject2 = jsonArray.getJSONObject(0);
		JSONArray jsonArray_chapter_list = jsonObject2.getJSONArray("chapter_list");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < jsonArray_chapter_list.length(); i++) {
			try {
				JSONObject jsonObject3 = jsonArray_chapter_list.getJSONObject(i);
				JSONArray jsonArray_chapter_source = jsonObject3.getJSONArray("chapter_source");
				JSONObject jsonObject4 = jsonArray_chapter_source.getJSONObject(0);
				String chapter_domain = jsonObject4.getString("chapter_domain");
				ManHua manHua = JSON.parseObject(jsonObject4.toString(), ManHua.class);
				String rule = manHua.getRule();
				sb.append("=========================================================\n");
				if ("zymkcdn.com".equals(chapter_domain)) {
					rule = "http://mhpic."+chapter_domain+URLDecoder.decode(rule);//ONLINE
				}else if ("samanlehua.com".equals(chapter_domain)) {
					rule = "http://mhpic."+chapter_domain+URLDecoder.decode(rule);//ONLINE
				}else {
					rule = "http://mhpic."+"samanlehua.com"+URLDecoder.decode(rule);//ONLINE
				}
				rule = URLDecoder.decode(rule);//ONLINE
				int end_num = manHua.getEnd_num();
				int start_num = manHua.getStart_num();
				for (int j = start_num; j <= end_num; j++) {
					String rule_ = rule.replace("$$", ""+j);
					if (rule_.contains("话")) {
						rule_ += "-mht.middle";
					}else {
					}
					sb.append(rule_+"\n");
				}
				sb.append("\n");
				System.out.println(sb.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println(sb.toString());
//		String decode = URLDecoder.decode("https://api.yyhao.com/app_api/v2/getsortlist/?comic_sort=&page=1&orderby=click&search_key=%E9%98%BF%E8%A1%B0");
//		System.out.println(decode);
//		pase("http://www.shenmanhua.com/asonline/1ce.html");
		
	}
	public static void pase(String url) {
		try {
			String get2String = HttpUtils.newInstance().get2String(url);
			Document document = 
//					Jsoup.connect(url).get();
//					Jsoup.connect("http://www.manhuatai.com/asonline/321.html?from=kmhapp").get();
			Jsoup.parse(get2String);
			Element element_comiclist = document.getElementById("comiclist");
			Elements elements_mh_comicpic = element_comiclist.getElementsByClass("mh_comicpic");
			
			for (int i = 0; i < elements_mh_comicpic.size(); i++) {
				Element element = elements_mh_comicpic.get(i);
				Elements elementsByAttribute_p = element.getElementsByAttribute("p");
				Elements elementsByTag_img = element.getElementsByTag("img");
				System.out.println(elementsByTag_img.toString());
			}
			System.out.println(document.toString());
			Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
