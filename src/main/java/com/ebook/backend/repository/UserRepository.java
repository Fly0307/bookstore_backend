package com.ebook.backend.repository;

import com.ebook.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
