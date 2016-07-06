package com.lzg.spidersdemo.dto;

/**
 * 存数据的一个dto
 * 
 * @author lzg
 * @date 2016年7月6日
 */
public class DataDto {
	private String code;
	private String cityType;
	private String name;

	private String linkHref;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkHref() {
		return linkHref;
	}

	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
	}

	@Override
	public String toString() {
		return code + "," + cityType + "," + name;
	}
}
