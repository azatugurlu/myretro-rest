package com.azat.myretro.model;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.azat.myretro.enums.BoardType;
import com.azat.myretro.enums.RetroType;
import com.azat.myretro.model.base.BaseModelAudit;

@Entity
public class Board extends BaseModelAudit {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "goal_achieved")
	private int goalAchieved;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "board_type")
	private BoardType boardType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "retro_type")
	private RetroType retroType;
	
	@Column(name = "team_id")
	private UUID team_id;
	
	@Column(name = "retro_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date retroDate;
	
	@Column(name = "count_down_timer_start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date countDownTimerStartDate;
	
	@Column(name = "count_down_timer_end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date countDownTimerEndDate;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "board_column", joinColumns = {
			@JoinColumn(name = "board_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id", referencedColumnName = "id") })
	private Set<BoardColumn> columns;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getTeam_id() {
		return team_id;
	}

	public void setTeam_id(UUID team_id) {
		this.team_id = team_id;
	}

	public int getGoalAchieved() {
		return goalAchieved;
	}

	public void setGoalAchieved(int goalAchieved) {
		this.goalAchieved = goalAchieved;
	}

	public BoardType getBoardType() {
		return boardType;
	}

	public void setBoardType(BoardType boardType) {
		this.boardType = boardType;
	}

	public RetroType getRetroType() {
		return retroType;
	}

	public void setRetroType(RetroType retroType) {
		this.retroType = retroType;
	}

	public Set<BoardColumn> getColumns() {
		return columns;
	}

	public void setColumns(Set<BoardColumn> columns) {
		this.columns = columns;
	}

	public Date getRetroDate() {
		return retroDate;
	}

	public void setRetroDate(Date retroDate) {
		this.retroDate = retroDate;
	}

	public Date getCountDownTimerStartDate() {
		return countDownTimerStartDate;
	}

	public void setCountDownTimerStartDate(Date countDownTimerStartDate) {
		this.countDownTimerStartDate = countDownTimerStartDate;
	}

	public Date getCountDownTimerEndDate() {
		return countDownTimerEndDate;
	}

	public void setCountDownTimerEndDate(Date countDownTimerEndDate) {
		this.countDownTimerEndDate = countDownTimerEndDate;
	}

	@Override
	public String toString() {
		return "Board [name=" + name + ", description=" + description + ", goalAchieved=" + goalAchieved
				+ ", boardType=" + boardType + ", retroType=" + retroType + ", team_id=" + team_id + ", retroDate="
				+ retroDate + ", countDownTimerStartDate=" + countDownTimerStartDate + ", countDownTimerEndDate="
				+ countDownTimerEndDate + ", columns=" + columns + "]";
	}
}
