/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kricko.domain.Role;
import com.kricko.domain.User;
import com.kricko.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationTestContext.xml"})
@WebAppConfiguration
public class UserControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));
    @InjectMocks
    private UserController controller;
    @Mock
    private UserService userServiceMock;
    private ObjectMapper mapper;
    private MockMvc mockMvc;
    private List<User> userList;
    private List<Role> roleList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();

        mapper = new ObjectMapper();

        userList = getUserList();
        when(userServiceMock.getUsers()).thenReturn(userList);
        roleList = getRoleList();
        when(userServiceMock.getRoles()).thenReturn(roleList);
    }

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(userList)));

    }

    @Test
    public void getRoles() throws Exception {
        mockMvc.perform(get("/user/roles"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(roleList)));
    }

    @Test
    public void createUserNullContent() throws Exception {
        mockMvc.perform(post("/user/create"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithContent() throws Exception {
        User user = getTestUser(1L, "admin", 1L, "Admin");
        mockMvc.perform(post("/user/create")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserNullContent() throws Exception {
        mockMvc.perform(put("/user/1/update"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserWithContent() throws Exception {
        User user = getTestUser(1L, "admin", 1L, "Admin");
        user.setUsername("test");
        mockMvc.perform(put("/user/1/update")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    private List<User> getUserList() {
        User admin = getTestUser(1L, "admin", 1L, "Admin");
        User user = getTestUser(2L, "test", 2L, "Test");

        return Arrays.asList(admin, user);
    }

    private List<Role> getRoleList() {
        Role admin = new Role();
        admin.setId(1L);
        admin.setName("admin");

        Role user = new Role();
        user.setId(2L);
        user.setName("user");

        return Arrays.asList(admin, user);
    }

    private User getTestUser(Long id, String username, Long roleId, String firstname) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setRoleId(roleId);
        user.setEmail("test@test.com");
        user.setFirstname(firstname);
        user.setLastname("User");
        user.setPassword("pass");

        return user;
    }
}