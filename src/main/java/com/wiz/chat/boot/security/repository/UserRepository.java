
package com.wiz.chat.boot.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wiz.chat.boot.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}