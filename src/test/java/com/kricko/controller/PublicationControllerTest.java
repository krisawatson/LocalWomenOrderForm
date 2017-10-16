/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kricko.domain.Publication;
import com.kricko.service.PublicationService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class PublicationControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));
    @InjectMocks
    private PublicationController controller;
    @Mock
    private PublicationService publicationServiceMock;
    private ObjectMapper mapper;
    private MockMvc mockMvc;
    private List<Publication> publicationList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();

        mapper = new ObjectMapper();

        publicationList = getPublicationList();
        when(publicationServiceMock.getPublications()).thenReturn(publicationList);
    }

    @Test
    public void getPublications() throws Exception {
        mockMvc.perform(get("/publication/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(publicationList)));
    }

    @Test
    public void createPublicationNoContent() throws Exception {
        mockMvc.perform(post("/publication/create"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createPublicationWithContent() throws Exception {
        mockMvc.perform(post("/publication/create")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(new Publication())))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePublicationNullContent() throws Exception {
        mockMvc.perform(put("/publication/1/update"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updatePublicationWithContent() throws Exception {
        mockMvc.perform(put("/publication/1/update")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(new Publication())))
                .andExpect(status().isOk());
    }

    private List<Publication> getPublicationList() {
        Publication publication1 = new Publication();
        publication1.setId(1L);
        publication1.setName("Test 1");
        publication1.setEmail("publication1@test.com");
        publication1.setEnabled(true);

        Publication publication2 = new Publication();
        publication2.setId(2L);
        publication2.setName("Test 2");
        publication2.setEmail("publication2@test.com");
        publication2.setEnabled(true);

        Publication publication3 = new Publication();
        publication3.setId(3L);
        publication3.setName("Test 3");
        publication3.setEmail("publication3@test.com");
        publication3.setEnabled(true);

        return Arrays.asList(publication1, publication2, publication3);
    }
}