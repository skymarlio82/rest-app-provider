
package com.wiz.chat.boot.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wiz.chat.boot.data.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

}