package com.ebook.backend.repository;

import com.ebook.backend.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface UserAuthRepository extends JpaRepository<UserAuthority,Integer>{

    @Query(value = "from UserAuthority where username = :username and password = :password")
    UserAuthority checkUser(@Param("username") String username, @Param("password") String password);

}
