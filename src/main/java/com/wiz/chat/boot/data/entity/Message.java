
package com.wiz.chat.boot.data.entity;

import java.util.Date;

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
@Table(name="MESSAGE")
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id = 0L;

	@Column(name="CREATED")
	@NotNull
	private Date created = null;

	@ManyToOne
	@JoinColumn(name="FROM_USER_ID", nullable=false)
	private User from = null;

	@Column(name="TEXT", length=200)
	@NotNull
	@Size(min=1, max=200)
	private String text = null;

	@ManyToOne
	@JoinColumn(name="CONVERSATION_ID", nullable=false)
	private Conversation conversation = null;

	@Column(name="IN_CHAT_ROOM")
	@NotNull
	private Boolean inChatRoom = false;

	public Message() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public Boolean getInChatRoom() {
		return inChatRoom;
	}

	public void setInChatRoom(Boolean inChatRoom) {
		this.inChatRoom = inChatRoom;
	}
}