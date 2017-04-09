/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kricko.domain.Orders;
import com.kricko.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class OrderControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));
    @InjectMocks
    private OrderController controller;
    @Mock
    private OrderService orderServiceMock;
    private ObjectMapper mapper;
    private MockMvc mockMvc;
    private Orders order;
    private List<Orders> orderList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();

        mapper = new ObjectMapper();

        order = getOrder(1L);
        when(orderServiceMock.getOrder(1L)).thenReturn(order);
        orderList = getOrderList();
        when(orderServiceMock.getOrders()).thenReturn(orderList);
    }

    @Test
    public void getOrder() throws Exception {
    }

    @Test
    public void createOrder() throws Exception {
    }

    @Test
    public void getOrders() throws Exception {
    }

    @Test
    public void updateOrder() throws Exception {
    }

    @Test
    public void getOrder1() throws Exception {
    }

    private Orders getOrder(Long id) {
        Orders order = new Orders();
        order.setId(1L);
        order.setBusinessId(1L);
        order.setCreated(new Date(System.currentTimeMillis()));

        return order;
    }

    private List<Orders> getOrderList() {
        Orders order1 = getOrder(1L);
        Orders order2 = getOrder(2L);
        Orders order3 = getOrder(3L);

        return Arrays.asList(order1, order2, order3);
    }
}