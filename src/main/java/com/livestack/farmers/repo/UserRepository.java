package com.livestack.farmers.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livestack.farmers.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
