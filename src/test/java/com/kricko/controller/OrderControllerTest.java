/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kricko.domain.Orders;
import com.kricko.domain.User;
import com.kricko.model.WebOrder;
import com.kricko.service.OrderService;
import com.kricko.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class OrderControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));
    @InjectMocks
    private OrderController controller;
    @Mock
    private OrderService orderServiceMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private Authentication mockAuthentication;
    @Mock
    private SecurityContext mockSecurityContext;

    private ObjectMapper mapper;
    private MockMvc mockMvc;
    private Orders order;
    private List<Orders> orderList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller)
                .build();

        User user = new User();
        user.setUsername("test");
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(userServiceMock.getUserByUsername(any(String.class))).thenReturn(user);
        mapper = new ObjectMapper();

        order = getOrder(1L);
        when(orderServiceMock.getOrder(1L)).thenReturn(order);
        orderList = getOrderList();
        when(orderServiceMock.getOrders()).thenReturn(orderList);
    }

    @Test
    public void getOrder() throws Exception {
        mockMvc.perform(get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(order)));
    }

    @Test
    public void getOrderNoId() throws Exception {
        // Get order without id does not exist
        mockMvc.perform(get("/order"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createOrder() throws Exception {
        WebOrder webOrder = new WebOrder();
        mockMvc.perform(post("/order/create")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(webOrder)))
                .andExpect(status().isOk());
    }

    @Test
    public void createOrderNoContent() throws Exception {
        mockMvc.perform(post("/order/create"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getOrders() throws Exception {
        mockMvc.perform(get("/order/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(orderList)));
    }

    @Test
    public void updateOrderNullContent() throws Exception {
        mockMvc.perform(put("/order/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrderWithContent() throws Exception {
        Orders order = getOrder(1L);
        order.setDeposit(100);
        mockMvc.perform(put("/order/1")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(order)))
                .andExpect(status().isOk());
    }

    @Test
    public void removeOrderNoOrderPartId() throws Exception {
        mockMvc.perform(delete("/order/1/orderpart"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeOrder() throws Exception {
        mockMvc.perform(delete("/order/1/orderpart/1"))
                .andExpect(status().isOk());
    }

    private Orders getOrder(Long id) {
        Orders order = new Orders();
        order.setId(1L);
        order.setBusinessId(1L);
        order.setUserId(1L);

        return order;
    }

    private List<Orders> getOrderList() {
        Orders order1 = getOrder(1L);
        Orders order2 = getOrder(2L);
        Orders order3 = getOrder(3L);

        return Arrays.asList(order1, order2, order3);
    }
}