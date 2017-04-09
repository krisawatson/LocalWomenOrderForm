/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kricko.domain.Business;
import com.kricko.service.BusinessService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class BusinessControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @InjectMocks
    private BusinessController controller;
    @Mock
    private BusinessService businessServiceMock;

    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private Business business;
    private List<Business> businessList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();

        mapper = new ObjectMapper();

        businessList = getBusinessList();
        when(businessServiceMock.getBusinesses()).thenReturn(businessList);
        business = getBusiness(1L, "business1", "John", "Smith", "123 Street", null, "Belfast",
                "Antrim", "AB12 3CD", "02890 123456", "business@test.com");
        when(businessServiceMock.getBusinesses()).thenReturn(businessList);
        when(businessServiceMock.getBusiness(1L)).thenReturn(business);
    }

    @Test
    public void getBusiness() throws Exception {
        mockMvc.perform(get("/business/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(business)));
    }

    @Test
    public void getBusinesses() throws Exception {
        mockMvc.perform(get("/business/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(businessList)));
    }

    @Test
    public void updateUserNullContent() throws Exception {
        mockMvc.perform(put("/business/1/update"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserWithContent() throws Exception {
        business.setName("Upadted Business");
        business.setEmail("updatedbusiness@test.com");
        business.setFirstname("Updated");
        mockMvc.perform(put("/business/1/update")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(business)))
                .andExpect(status().isOk());
    }

    private Business getBusiness(Long id, String name, String firstname, String lastname,
                                 String address1, String address2, String city, String county,
                                 String postcode, String tel, String email) {
        Business business = new Business();
        business.setId(id);
        business.setName(name);
        business.setFirstname(firstname);
        business.setLastname(lastname);
        business.setAddress1(address1);
        business.setAddress2(address2);
        business.setCity(city);
        business.setCounty(county);
        business.setPostcode(postcode);
        business.setTel(tel);
        business.setEmail(email);

        return business;
    }

    private List<Business> getBusinessList() {
        Business business1 = getBusiness(1L, "business1", "John", "Smith",
                "123 Street", null, "Belfast", "Antrim", "AB12 3CD",
                "02890 123456", "business1@test.com");
        Business business2 = getBusiness(2L, "business2", "John", "Smith",
                "123 Street", null, "Belfast", "Antrim", "AB12 3CD",
                "02890 123456", "business2@test.com");
        Business business3 = getBusiness(3L, "business3", "John", "Smith",
                "123 Street", null, "Belfast", "Antrim", "AB12 3CD",
                "02890 123456", "business3@test.com");

        return Arrays.asList(business1, business2, business3);
    }
}