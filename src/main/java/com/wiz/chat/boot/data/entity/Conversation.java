
package com.wiz.chat.boot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wiz.chat.boot.security.entity.User;

@Entity
@Table(name="CONVERSATION")
public class Conversation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id = 0L;

	@Column(name="NAME", length=100)
	@NotNull
	@Size(min=3, max=100)
	private String name = null;

	@ManyToOne
	@JoinColumn(name="PARTICIPANT1", nullable=false)
	private User participant1 = null;

	@ManyToOne
	@JoinColumn(name="PARTICIPANT2", nullable=false)
	private User participant2 = null;

	public Conversation() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getParticipant1() {
		return participant1;
	}

	public void setParticipant1(User participant1) {
		this.participant1 = participant1;
	}

	public User getParticipant2() {
		return participant2;
	}

	public void setParticipant2(User participant2) {
		this.participant2 = participant2;
	}
}