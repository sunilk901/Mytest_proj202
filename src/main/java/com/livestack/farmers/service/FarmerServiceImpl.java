package com.livestack.farmers.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.livestack.farmers.custom.exception.FarmerExistsException;
import com.livestack.farmers.custom.exception.PasswordAndConfirmPasswordNotMatch;
import com.livestack.farmers.domain.Farmer;
import com.livestack.farmers.domain.PostalAddress;
import com.livestack.farmers.domain.User;
import com.livestack.farmers.domain.UserLogin;
import com.livestack.farmers.domain.UserStatus;
import com.livestack.farmers.repo.FarmerRepository;
import com.livestack.farmers.repo.PostalAddressRepo;
import com.livestack.farmers.repo.UserLoginRepo;
import com.livestack.farmers.repo.UserRepository;
import com.livestack.farmers.repo.UserStatusRepo;

@Service
@Transactional
public class FarmerServiceImpl implements FarmerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerServiceImpl.class);
	private FarmerRepository farmerRepository;
	private UserLoginRepo userLoginRepo;
	private UserStatusRepo userStatusRepo;
	private UserRepository userRepository;
	private PostalAddressRepo postalAddressRepo;
	private PostalAddress postalAddress;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public FarmerServiceImpl(FarmerRepository farmerRepository, UserStatusRepo userStatusRepo,
			UserLoginRepo userLoginRepo, PostalAddressRepo postalAddressRepo, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.farmerRepository = farmerRepository;
		this.userLoginRepo = userLoginRepo;
		this.userStatusRepo = userStatusRepo;
		this.postalAddressRepo = postalAddressRepo;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Create a new farmer
	 * 
	 * @param id of the farmer
	 * 
	 * @return new or existing employee
	 */
	@Override
	public Farmer createNewFarmer(Farmer farmer) {
		Optional<Farmer> farmerRecord = farmerRepository.findByAadharNumber(farmer.getAadharNumber());
		if (!farmerRecord.isPresent()) {
			farmer.setName(StringEscapeUtils.escapeHtml(farmer.getName()));

			// make entry to postal address entity
			Set<PostalAddress> addresses = farmer.getPostalAddresses();
			Set<PostalAddress> postalAddresses = new HashSet<PostalAddress>();
			
			User user = new User();
			user.setName(farmer.getName());
			

			if (addresses != null && addresses.size() > 0) {
				for (PostalAddress address : addresses) {
					PostalAddress postalAddress = new PostalAddress();
					postalAddress.setAddress(StringEscapeUtils.escapeHtml(address.getAddress()));
					postalAddress.setState(address.getState());
					postalAddress.setDistrict(address.getDistrict());
					postalAddress.setAddressTypeId(address.getAddressTypeId());
					postalAddresses.add(postalAddress);
				}
			}
			
			user.setPostalAddresses(postalAddresses);
			userRepository.save(user);

			// save farmer data
			farmer.setId(user.getId());
			farmerRepository.save(farmer);

			// make entry to user status entity
			Date date = new Date();
			UserStatus userStatus = new UserStatus();
			userStatus.setUserId(user.getId());
			userStatus.setFromDate(new Timestamp(date.getTime()));
			userStatus.setActive(true);
			user.setUserStatus(userStatus);

			// make entry to user login entity
			UserLogin userLogin = new UserLogin();
			userLogin.setUserId(user.getId());
			userLogin.setUsername(user.getId().toString());
			if (farmer.getDob() != null) {
				Date dob = farmer.getDob();
				String dobStr = new SimpleDateFormat("dd_MM_yyyy").format(dob);
				userLogin.setPassword(passwordEncoder.encode(dobStr));
			}

			userLogin.setFailedAttempts(0);
			user.setUserLogin(userLogin);

			return farmer;
		} else {
			throw new FarmerExistsException();
		}
	}

	/**
	 * List all farmers
	 * 
	 * @return all farmers
	 */
	@Override
	public Iterable<Farmer> getFarmers() {
		List<UserStatus> users = userStatusRepo.getValidUsers();
		List<Integer> farmerIds = new ArrayList<Integer>();
		if (users.size() > 0) {
			for (UserStatus u : users) {
				farmerIds.add(u.getUserId());
			}
			return farmerRepository.findAllById(farmerIds);
		}

		return new ArrayList<>();
	}

	public long getTotalFarmers() {
		return farmerRepository.count();
	}

	/**
	 * Get farmer record for the given username
	 * 
	 * @param id of the employee
	 * 
	 * @return record of employee
	 */
	@Override
	public Farmer getFarmerDetails(String username) {
		Optional<UserLogin> user = userLoginRepo.findByUsername(username);
		if (userLoginRepo.findByUsername(username).isPresent()) {
			Optional<User> farmer = userRepository.findById(user.get().getUserId());
			if (farmer.get().getUserStatus().getThruDate() == null && farmer.get().getUserStatus().isActive()) {
				return farmerRepository.findById(user.get().getUserId())
						.orElseThrow(() -> new NoSuchElementException());
			} else {
				throw new NoSuchElementException();
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * Delete farmer record for the given Id
	 * 
	 * @param id of the farmer
	 * 
	 * @return record of employee
	 */
	@Override
	public void deleteFarmerById(String id) {
		Integer farmerId = Integer.parseInt(id);
		if (farmerRepository.findById(farmerId).isPresent()) {
			Date date = new Date();
			userStatusRepo.disableUser(new Timestamp(date.getTime()), false, farmerId);
		} else {
			throw new NoSuchElementException();
		}
	}
	
	
	/**
	 * Updates Farmer password
	 * 
	 * @param id of the farmer
	 * @param userLogin object
	 */
	@Override
	public void updateFarmerPassword(String id, UserLogin userLogin) {
		Integer farmerId = Integer.parseInt(id);
		String newPassword = userLogin.getPassword();
		String confirmPassword = userLogin.getConfirmPassword();
		String securityQuestion = userLogin.getSecurityQuestion();
		String securityAnswer = userLogin.getSecurityAnswer();
		String encryptPassword = passwordEncoder.encode(newPassword);
		
		if(newPassword.equals(confirmPassword)) {
			userLoginRepo.updateFarmerPassword(encryptPassword, securityQuestion, securityAnswer, farmerId);
		} else {
			// throw error
			throw new PasswordAndConfirmPasswordNotMatch();
		}
		
	}
}
