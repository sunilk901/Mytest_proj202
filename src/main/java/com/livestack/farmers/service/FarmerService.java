package com.livestack.farmers.service;


import com.livestack.farmers.domain.Farmer;
import com.livestack.farmers.domain.UserLogin;


public interface FarmerService {
	public Farmer createNewFarmer(Farmer farmer);
	public Iterable<Farmer> getFarmers();
	public Farmer getFarmerDetails(String username);
	public void deleteFarmerById(String id);
	public void updateFarmerPassword(String id, UserLogin userLogin);
}
