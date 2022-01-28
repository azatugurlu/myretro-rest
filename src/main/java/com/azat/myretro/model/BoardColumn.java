package com.azat.myretro.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.azat.myretro.model.base.BaseModelAudit;

@Entity
public class BoardColumn extends BaseModelAudit {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "board_id")
	private UUID board_id;
	
	@Column(name = "column_order")
	private int columnOrder;
	
	@Column(name = "color")
	private String color;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "board_row", joinColumns = {
			@JoinColumn(name = "column_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id", referencedColumnName = "id") })
	private Set<BoardRow> rows;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getBoard_id() {
		return board_id;
	}

	public void setBoard_id(UUID board_id) {
		this.board_id = board_id;
	}

	public Set<BoardRow> getRows() {
		return rows;
	}

	public void setRows(Set<BoardRow> rows) {
		this.rows = rows;
	}

	public int getColumnOrder() {
		return columnOrder;
	}

	public void setColumnOrder(int columnOrder) {
		this.columnOrder = columnOrder;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "BoardColumn [name=" + name + ", board_id=" + board_id + ", columnOrder=" + columnOrder + ", color="
				+ color + ", rows=" + rows + "]";
	}
}
