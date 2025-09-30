package com.example.stocks.repository;

import com.example.stocks.entity.UserActEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActRe extends JpaRepository<UserActEn, String> { // <UserAct, String(uuid)>
}
