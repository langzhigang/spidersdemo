package service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lzg.spidersdemo.dto.DataDto;

import rule.Rule;

/**
 * 利用jsonp去爬去网页数据
 * 
 * @author lzg
 * @date 2016年7月6日
 */
public class ExtractService {
	/**
	 * @param rule
	 * @return
	 */
	public static List<DataDto> extract(Rule rule, int level) {

		// 进行对rule的必要校验
		validateRule(rule);

		List<DataDto> datas = new ArrayList<DataDto>();
		try {
			/**
			 * 解析rule
			 */
			String url = rule.getUrl();
			String[] params = rule.getParams();
			String[] values = rule.getValues();
			String filterAttrName = rule.getFilterAttrName();
			int type = rule.getType();
			int requestType = rule.getRequestMoethod();

			Connection conn = Jsoup.connect(url);
			// 设置查询参数

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					conn.data(params[i], values[i]);
				}
			}

			// 设置请求类型
			Document doc = null;
			switch (requestType) {
			case Rule.GET:
				// doc = conn.timeout(100000).get();
				doc = Jsoup.parse(new URL(url).openStream(), "GBK", url);// 编码根据爬取的页面的编码一样
				break;
			case Rule.POST:
				doc = conn.timeout(100000).post();
				break;
			}

			// 处理返回数据
			Elements results = new Elements();
			switch (type) {
			case Rule.CLASS:
				results = doc.getElementsByClass(filterAttrName);
				break;
			case Rule.ID:
				Element result = doc.getElementById(filterAttrName);
				results.add(result);
				break;
			case Rule.SELECTION:
				results = doc.select(filterAttrName);
				break;
			default:
				// 当resultTagName为空时默认去body标签
				if (filterAttrName == null || "".equals(filterAttrName)) {
					results = doc.getElementsByTag("body");
				}
			}

			// 根据对应的class 或者 id 过滤 数据后,分析页面结构，获取下面的标签内容。
			for (Element result : results) {
				DataDto dataDto = null;

				Elements links = result.getElementsByTag("a");
				if (level == 1) {
					for (Element link : links) {
						dataDto = new DataDto();
						String linkHref = link.attr("href");
						String name = link.text();
						dataDto.setLinkHref(linkHref);
						dataDto.setCode(null);
						dataDto.setName(name);
						datas.add(dataDto);
					}
				} else {
					if (links != null && links.size() > 0) {
						// 必要的筛选
						String linkHref = links.get(0).attr("href");
						String code = links.get(0).text();
						String name = links.get(1).text();

						dataDto = new DataDto();
						dataDto.setLinkHref(linkHref);
						dataDto.setCode(code);
						dataDto.setName(name);
						dataDto.setCityType(null);
						datas.add(dataDto);
					} else {
						Elements tds = result.getElementsByTag("td");
						if (tds.size() == 3) {
							String code = tds.get(0).text();
							String cityType = tds.get(1).text();
							String name = tds.get(2).text();

							dataDto = new DataDto();
							dataDto.setLinkHref(null);
							dataDto.setCode(code);
							dataDto.setCityType(cityType);
							dataDto.setName(name);
						} else if (tds.size() == 2) {
							String code = tds.get(0).text();
							String name = tds.get(1).text();

							dataDto = new DataDto();
							dataDto.setLinkHref(null);
							dataDto.setCode(code);
							dataDto.setCityType(null);
							dataDto.setName(name);
						}

						datas.add(dataDto);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return datas;

	}

	/**
	 * 对传入的参数进行必要的校验
	 */
	private static void validateRule(Rule rule) {
		String url = rule.getUrl();
		if (url == null || "".equals(url)) {
			throw new RuntimeException("url不能为空！");
		}
		if (!url.startsWith("http://")) {
			throw new RuntimeException("url的格式不正确！");
		}

		if (rule.getParams() != null && rule.getValues() != null) {
			if (rule.getParams().length != rule.getValues().length) {
				throw new RuntimeException("参数的键值对个数不匹配！");
			}
		}

	}
}
