package com.kricko.service;

import com.kricko.constants.EmailType;
import com.kricko.domain.Business;
import com.kricko.domain.Orders;

public interface MailTemplateService {

    String buildTemplate(EmailType type, Business business, Orders orderId);

    String buildTemplate(EmailType type, Business business, Orders orderId, Long pubId);
}
