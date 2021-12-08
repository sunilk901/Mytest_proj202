package com.livestack.farmers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livestack.farmers.domain.PostalAddress;

@Repository
public interface PostalAddressRepo extends JpaRepository<PostalAddress, Integer> {

}
