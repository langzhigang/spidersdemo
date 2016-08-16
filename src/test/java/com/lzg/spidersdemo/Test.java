package com.lzg.spidersdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import com.lzg.spidersdemo.dto.DataDto;

import rule.Rule;
import service.ExtractService;

public class Test {
	String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/";
	// 层级类型
	String[] tagArr = { "provincetr", "citytr", "countytr", "towntr", "villagetr" };

	@org.junit.Test
	public void test1() throws FileNotFoundException {
		// getDatasByClass("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2014/index.html",
		// 1);
		getDatasByClass("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html", 1);
	}

	public void getDatasByClass(String url, int level) throws FileNotFoundException {
		if (level > 5) { // 最多只有5级
			return;
		}
		Rule rule = new Rule(url, tagArr[level - 1], Rule.CLASS, Rule.GET);

		List<DataDto> datas = ExtractService.extract(rule, level);

		String tempUrl = "";
		PrintWriter pw = null;
		level++;
		try {
			if (datas == null || datas.size() == 0) { // 说明这一层没找到
				getDatasByClass(url, level);
			} else {
				OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File("F:/test.txt"), true),
						"UTF-8");
				pw = new PrintWriter(osw);
				for (DataDto data : datas) {
					pw.println(data);
					pw.flush();
					String href = data.getLinkHref();
					if (href != null) {
						tempUrl = url.substring(0, url.lastIndexOf("/") + 1) + href;
						getDatasByClass(tempUrl, level);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
