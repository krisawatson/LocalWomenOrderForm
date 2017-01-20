package com.kricko.service;

import com.kricko.constants.EmailType;
import com.kricko.domain.Business;
import com.kricko.domain.Orders;

public interface MailTemplateService {

	public String buildTemplate(EmailType type, Business business, Orders orderId);
	
	public String buildTemplate(EmailType type, Business business, Orders orderId, Long pubId);
}
