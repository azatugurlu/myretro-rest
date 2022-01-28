package com.azat.myretro.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.azat.myretro.model.base.BaseModelAudit;


@Entity
public class Permission extends BaseModelAudit {

	@Column(name = "name")
	private String name;
	
	@Column(name = "title")
	private String title;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Permission [name=" + name + ", title=" + title + "]";
	}
}
