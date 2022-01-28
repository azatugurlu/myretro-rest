package com.azat.myretro.model;

import org.springframework.data.domain.Page;

/**
 * Reserved for pagination details.
 * 
 * 
 *
 */
public class Pagination {

	Integer totalPages;
	Long totalElements;
	Integer size;
	Integer number;
	Boolean first;
	Boolean last;
	Integer numberOfElements;
	Boolean empty;
	
	public Pagination() {
	}
	
	public Pagination(Page page) {
		this.map(page);
	}
	
	public void map(Page page) {
		this.setTotalPages(page.getTotalPages());
		this.setTotalElements(page.getTotalElements());
		this.setSize(page.getSize());
		this.setNumber(page.getNumber());
		this.setFirst(!page.hasPrevious());
		this.setLast(!page.hasNext());
		this.setNumberOfElements(page.getNumberOfElements());
		this.setEmpty(page.isEmpty());
		
	}
	
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long l) {
		this.totalElements = l;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Boolean getFirst() {
		return first;
	}
	public void setFirst(Boolean first) {
		this.first = first;
	}
	public Boolean getLast() {
		return last;
	}
	public void setLast(Boolean last) {
		this.last = last;
	}
	public Integer getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public Boolean getEmpty() {
		return empty;
	}
	public void setEmpty(Boolean empty) {
		this.empty = empty;
	}

	@Override
	public String toString() {
		return "Pagination [totalPages=" + totalPages + ", totalElements=" + totalElements + ", size=" + size
				+ ", number=" + number + ", first=" + first + ", last=" + last + ", numberOfElements="
				+ numberOfElements + ", empty=" + empty + "]";
	}
}
