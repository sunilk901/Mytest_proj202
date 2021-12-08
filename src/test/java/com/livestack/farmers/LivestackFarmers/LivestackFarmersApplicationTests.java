package com.livestack.farmers.LivestackFarmers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.livestack.farmers.domain.Farmer;
import com.livestack.farmers.domain.PostalAddress;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LivestackFarmersApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getFarmers() throws Exception {
		mockMvc.perform(get("/farmers")).andExpect(status().isOk());
	}

	@Test
	public void createFarmer() throws Exception {
		Set<PostalAddress> addresses = new HashSet<PostalAddress>();
		PostalAddress addressObj = new PostalAddress();
		addressObj.setState("UK");
		addressObj.setDistrict("UK-09");
		addressObj.setAddress("NG Road");
		addressObj.setAddressTypeId("HOME_ADDRESS");
		addresses.add(addressObj);
		mockMvc.perform(post("/farmers")
				.content(asJsonString(new Farmer("Vikram Gill", "male", "General", "9876567876",
						new SimpleDateFormat("yyyy-MM-dd").parse("1980-10-06"), "123456789123", addresses, 5)))
				.contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
