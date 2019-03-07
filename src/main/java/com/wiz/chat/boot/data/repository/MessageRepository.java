
package com.wiz.chat.boot.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wiz.chat.boot.data.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}