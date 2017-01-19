package com.kricko.service;

import com.kricko.constants.EmailType;
import com.kricko.domain.Orders;

public interface MailTemplateService {

	public String buildTemplate(EmailType type, Orders orders);
	
	public String buildPublicationTemplate(EmailType type, Orders orders, Long pubId);
}
