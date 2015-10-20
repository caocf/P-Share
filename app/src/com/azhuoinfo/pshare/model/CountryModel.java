package com.azhuoinfo.pshare.model;

import java.util.List;

public class CountryModel {
	private String name;
	private List<ProvinceModel> provinceList;

	public CountryModel() {
	}

	public CountryModel(String name, List<ProvinceModel> provinceList) {
		this.name = name;
		this.provinceList = provinceList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProvinceModel> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<ProvinceModel> provinceList) {
		this.provinceList = provinceList;
	}

	@Override
	public String toString() {
		return "CountryModel{" +
				"name='" + name + '\'' +
				", provinceList=" + provinceList +
				'}';
	}
}
