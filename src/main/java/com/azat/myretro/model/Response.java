package com.azat.myretro.model;

import java.util.Iterator;
/**
 * 
 * Single response class for REST calls. The following 
 * rules should be followed to have a REST API
 * having a uniform response model.
 * 
 *  	Updated and Insert calls should return
 *  		<code>status</code>, <code>item</code>
 *  		and optionally <code>messages</code>
 *  	attributes.
 *  
 *  	Fetch calls should return
 *  		<code>status</code>, <code>items</code>
 *  		and optionally <code>pagination</code>,
 *          <code>messages</code>
 *  	attributes.
 *  
 *
 *  	
 * 
 */
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class Response<T> {

	@JsonIgnore
	protected HttpStatus status;
	@JsonInclude(Include.NON_EMPTY)
	protected List<Message> messages;
	protected List<T> items;
	protected T item;
	protected Pagination pagination;
	protected PageImpl<T> data;
	

	public PageImpl<T> getData() {
		return data;
	}


	public Response<T> setData(Page<T> pageImpl) {
		this.data = (PageImpl<T>) pageImpl;
		return this;
	}
	public Response<T> setPagination(Pagination pagination) {
		this.pagination = pagination;
		return this;
	}

	protected Response() {
		this.status = HttpStatus.OK;
		this.items = new LinkedList<T>();
		this.messages = new LinkedList<Message>();
	}
	
	public static Response create() {
		return new Response();
	}

	public static <T> Response<T>  create(Class<T> type) {
		return (Response<T>)new Response();
	}
	
	public List<T> getItems() {
		return items;
	}

	public T getItem() {
		return item;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public Pagination getPagination() {
		return pagination;
	}
	
	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return String.format("items: %s \n item: %s \n", items, item != null ? item.toString() : "");
	}

	public Response<T> items(List<T> data) {
		this.items = data;
		return this;
	}

	public Response<T> item(T data) {
		this.item = data;
		return this;
	}

	public Response<T> addItem(T data) {
		if (this.items == null ) {
			this.items = new LinkedList<T>();
		}
		this.items.add(data);
		return this;
	}
	
	public Response<T> addMessage(Message m) {
		messages.add(m);
		return this;
	}
	
	public Response<T> message(String msg, String type) {
		messages.add(Message.create().msg(msg).type(type));
		return this;
	}
	
	public Response<T> message(String field, String msg, String type) {
		messages.add(Message.create().msg(msg).type(type).field(field));
		return this;
	}

	
	public Response<T> message(String field, String code, String msg, String type) {
		messages.add(Message.create().msg(msg).type(type).field(field).code(code));
		return this;
	}
	
	public Response<T> code(HttpStatus s) {
		this.status = s;
		return this;
	}
	
	public Response<T> merge(Response s) {
		if (s.getMessages() != null) {
			Iterator<Message> i = s.getMessages().iterator();
			while(i.hasNext()) {
				this.messages.add(i.next());
			}
		}
		this.code(s.getStatus());
		return this;
	}
	
	public boolean hasErrors() {
		return this.getMessages().stream().filter(m -> "error".equals(m.getType())).count() > 0;
	}
	public String printMessages() {
		StringBuilder errorMessages = new StringBuilder();
		for (Message message : messages) {
			errorMessages.append(message.toString());
		}
		return errorMessages.toString();
	}
}
