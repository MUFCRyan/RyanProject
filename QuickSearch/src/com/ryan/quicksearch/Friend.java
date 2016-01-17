package com.ryan.quicksearch;

public class Friend implements Comparable<Friend>{
	//姓名
	public String name;
	//姓名相应拼音
	public String pinyin;
	//拼音相应首字母
	public String initLetter;
	
	public Friend(String name){
		this.name = name;
		pinyin = PinYinUtil.getPinyin(name);
		initLetter = pinyin.charAt(0)+"";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getInitLetter() {
		return initLetter;
	}
	public void setInitLetter(String initLetter) {
		this.initLetter = initLetter;
	}
	@Override
	public int compareTo(Friend another) {
		
		return pinyin.compareTo(another.getPinyin());
	}
}
