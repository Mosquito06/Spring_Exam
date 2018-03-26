package com.dgit.domain;

import java.util.Arrays;

public class MemberVO {
	private int num;
	private String id;
	private String pw;
	private String name;
	private String email;
	private String tel;
	private PhotoVO[] images;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public PhotoVO[] getImages() {
		return images;
	}

	public void setImages(PhotoVO[] images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "MemberVO [num=" + num + ", id=" + id + ", pw=" + pw + ", name=" + name + ", email=" + email + ", tel="
				+ tel + ", images=" + Arrays.toString(images) + "]";
	}

}
