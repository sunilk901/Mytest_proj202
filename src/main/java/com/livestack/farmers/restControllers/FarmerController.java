package com.livestack.farmers.restControllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.livestack.farmers.domain.Farmer;
import com.livestack.farmers.domain.UserLogin;
import com.livestack.farmers.service.FarmerService;

@RestController
@CrossOrigin(origins = "http://localhost:8084")
public class FarmerController {
	
	@Autowired
	private FarmerService farmerService;
	
	@PostMapping(path="/farmers", consumes = "application/json", produces = "application/json")
	public Farmer createNewFarmer(@Valid @RequestBody Farmer farmer) {
		return farmerService.createNewFarmer(farmer);
	}
	
	@GetMapping(path="/farmers", produces = "application/json")
	public Iterable<Farmer> getFarmers() {
		return farmerService.getFarmers();
	}
	
	@GetMapping(path="/farmers/{username}", produces = "application/json")
	public Farmer getFarmerDetails(@PathVariable(value = "username") String username) {
		return farmerService.getFarmerDetails(username);
	}
	
	@PutMapping("/farmers/{id}")
	public ResponseEntity<Object> deleteFarmerById(@PathVariable(value = "id") String id) {
		farmerService.deleteFarmerById(id);
		return new ResponseEntity<>("Farmer is deleted successsfully", HttpStatus.OK);
	}
	
	@PutMapping("/farmers/{id}/updatePassword")
	public ResponseEntity<Object> updateFarmerPassword(@PathVariable(value = "id") String id, @RequestBody UserLogin userLogin) {
		farmerService.updateFarmerPassword(id, userLogin);
		return new ResponseEntity<>("New password has been set successsfully", HttpStatus.OK);
	}

}
