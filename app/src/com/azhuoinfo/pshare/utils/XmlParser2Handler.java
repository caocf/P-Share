package com.azhuoinfo.pshare.utils;


import com.azhuoinfo.pshare.model.CityModel;
import com.azhuoinfo.pshare.model.CountryModel;
import com.azhuoinfo.pshare.model.DistrictModel;
import com.azhuoinfo.pshare.model.ProvinceModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlParser2Handler extends DefaultHandler {

	/**
	 */
	private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();
	private List<CountryModel> countryList=new ArrayList<CountryModel>();

	public XmlParser2Handler() {
		
	}

	public List<ProvinceModel> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {
	}
	CountryModel countryModel=new CountryModel();
	ProvinceModel provinceModel = new ProvinceModel();
	CityModel cityModel = new CityModel();
	DistrictModel districtModel = new DistrictModel();
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("country")) {
			provinceModel = new ProvinceModel();
			provinceModel.setName(attributes.getValue(0));
			provinceModel.setCityList(new ArrayList<CityModel>());
		} else if (qName.equals("province")) {
			cityModel = new CityModel();
			cityModel.setName(attributes.getValue(0));
			cityModel.setDistrictList(new ArrayList<DistrictModel>());
		} else if (qName.equals("city")) {
			districtModel = new DistrictModel();
			districtModel.setName(attributes.getValue(0));
			districtModel.setZipcode(attributes.getValue(1));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("city")) {
			cityModel.getDistrictList().add(districtModel);
		} else if (qName.equals("province")) {
			provinceModel.getCityList().add(cityModel);
		} else if (qName.equals("country")) {
			provinceList.add(provinceModel);
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

}
