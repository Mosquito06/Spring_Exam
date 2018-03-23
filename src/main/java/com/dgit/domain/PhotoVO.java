package com.dgit.domain;

import java.util.Date;

public class PhotoVO {
	private String filepath;
	private int num;
	private Date regdate;

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "PhotoVO [filepath=" + filepath + ", num=" + num + ", regdate=" + regdate + "]";
	}

}
