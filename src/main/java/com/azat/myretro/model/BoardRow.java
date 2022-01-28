package com.azat.myretro.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.azat.myretro.model.base.BaseModelAudit;

@Entity
public class BoardRow extends BaseModelAudit {
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "author_name")
	private String authorName;
	
	
	@Column(name = "column_id")
	private UUID column_id;
	
	@Column(name = "board_id")
	private UUID board_id;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "vote_user", joinColumns = {
			@JoinColumn(name = "board_row_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id", referencedColumnName = "id") })
	private Set<VoteUser> votes;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public UUID getColumn_id() {
		return column_id;
	}

	public void setColumn_id(UUID column_id) {
		this.column_id = column_id;
	}

	public UUID getBoard_id() {
		return board_id;
	}

	public void setBoard_id(UUID board_id) {
		this.board_id = board_id;
	}

	public Set<VoteUser> getVotes() {
		return votes;
	}

	public void setVotes(Set<VoteUser> votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "BoardRow [description=" + description + ", authorName=" + authorName + ", column_id=" + column_id
				+ ", board_id=" + board_id + "]";
	}
}
