package com.example.animal.animal;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class AnimalHandler extends DefaultHandler {

	private List<Animal> list;
	private Animal animal;
	private String str;
	
	public AnimalHandler() {
		list = new ArrayList<>();
	}
	
	public void startElement(String uri, String localName, String name, Attributes att) {
		if(name.equals("item")) {
			animal = new Animal();
			list.add(animal);
		}
	}
	
	public void endElement(String uri, String localName, String name) {
		if(name.equals("age")) {
			animal.setAge(str);
		}else if(name.equals("careNm")) {
			animal.setCareNm(str);
		}else if(name.equals("careTel")) {
			animal.setCareTel(str);
		}else if(name.equals("chargeNm")) {
			animal.setChargeNm(str);
		}else if(name.equals("happenDt")) {
			animal.setHappenDt(Long.parseLong(str));
		}else if(name.equals("happenPlace")) {
			animal.setHappenPlace(str);
		}else if(name.equals("kindCd")) {
			animal.setKindCd(str);
		}else if(name.equals("noticeEdt")) {
			animal.setNoticeEdt(Long.parseLong(str));
		}else if(name.equals("officetel")) {
			animal.setOfficetel(str);
		}else if(name.equals("orgNm")) {
			animal.setOrgNm(str);
		}else if(name.equals("popfile")) {
			animal.setPopfile(str);
		}else if(name.equals("processState")) {
			animal.setProcessState(str);
		}else if(name.equals("sexCd")) {
			animal.setSexCd(str);
		}else if(name.equals("specialMark")) {
			animal.setSpecialMark(str);
		}else if(name.equals("weight")) {
			animal.setWeight(str);
		}
	}
	
	public void characters(char[] ch, int start, int length) {
		str = new String(ch, start, length);
	}
	
	public List<Animal> getAnimalList(){
		return list;
	}
	
	public void setAnimalList(List<Animal> list) {
		this.list = list;
	}
	
}
