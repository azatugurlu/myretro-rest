package com.azat.myretro.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.azat.myretro.model.base.BaseIdEntity;

@Entity
public class VoteUser extends BaseIdEntity {
	
	@Column(name = "board_row_id")
	private UUID board_row_id;
	
	@Column(name = "user_id")
	private UUID user_id;
	
	@Column(name = "author_name")
	private String authorName;
	
	public UUID getBoard_row_id() {
		return board_row_id;
	}
	public void setBoard_row_id(UUID board_row_id) {
		this.board_row_id = board_row_id;
	}
	public UUID getUser_id() {
		return user_id;
	}
	public void setUser_id(UUID user_id) {
		this.user_id = user_id;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	@Override
	public String toString() {
		return "VoteUser [board_row_id=" + board_row_id + ", user_id=" + user_id + ", authorName=" + authorName + "]";
	}
}
