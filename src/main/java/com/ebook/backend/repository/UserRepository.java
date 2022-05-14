package com.ebook.backend.repository;

import com.ebook.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User,String> {

    @Query(value = "from User where userId = :userId")
    User getUserById(@Param("userId") Integer userId);

    @Query(value ="from User where username = ?1")
    User checkUserDup(String username);

}
