package com.azat.myretro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class Message {

	private Message() {
	}

	private String field = "";

	private String code = "";
	
	private String msg = "";

	private String type = "";

	public static Message create() {
		return new Message();
	}

	public String getMsg() {
		return msg;
	}

	public String getType() {
		return type;
	}

	public Message msg(String msg) {
		this.msg = msg;
		return this;
	}

	public Message type(String type) {
		this.type = type;
		return this;
	}

	public String getField() {
		return field;
	}

	public Message field(String field) {
		this.field = field;
		return this;
	}
	
	public String getCode() {
		return code;
	}

	public Message code(String code) {
		this.code = code;
		return this;
	}

	@Override
	public String toString() {
		return "Message [field=" + field + ", code=" + code + ", msg=" + msg + ", type=" + type + "]";
	}
}
