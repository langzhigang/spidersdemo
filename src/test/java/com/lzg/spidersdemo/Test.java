package com.lzg.spidersdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.lzg.spidersdemo.dto.DataDto;

import rule.Rule;
import service.ExtractService;

public class Test {
	String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2014/";
	List<DataDto> allData = new ArrayList<DataDto>();
	String[] tagArr = { "provincetr", "citytr", "countytr", "towntr", "villagetr" };

	@org.junit.Test
	public void test1() throws FileNotFoundException {
		getDatasByClass("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2014/index.html", 1);
	}

	public void getDatasByClass(String url, int level) throws FileNotFoundException {
		Rule rule = new Rule(url, tagArr[level - 1], Rule.CLASS, Rule.GET);

		List<DataDto> datas = ExtractService.extract(rule, level);
		allData.addAll(datas);

		level++;
		String tempUrl = "";
		PrintWriter pw = null;
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
