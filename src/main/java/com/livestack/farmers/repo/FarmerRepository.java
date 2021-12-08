package com.livestack.farmers.repo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.livestack.farmers.domain.Farmer;
import com.livestack.farmers.domain.UserStatus;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Integer> {
	Optional<Farmer> findByAadharNumber(String aadharNumber);
	List<Farmer> findByName(@Param("name") String name);
    
    @Query("SELECT u FROM Farmer u WHERE (u.id = ?1)")
    public List<Farmer> getFarmers();
}
