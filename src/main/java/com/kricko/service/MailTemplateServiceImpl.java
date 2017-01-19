package com.kricko.service;

import java.nio.charset.StandardCharsets;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.kricko.constants.EmailType;
import com.kricko.constants.MailTemplating;
import com.kricko.domain.Business;
import com.kricko.domain.OrderPart;
import com.kricko.domain.OrderPublication;
import com.kricko.domain.Orders;
import com.kricko.model.email.TemplateOrder;
import com.kricko.model.email.TemplateOrderPart;
import com.kricko.model.email.TemplateOrderPublication;
import com.kricko.repository.AdvertSizeRepository;
import com.kricko.repository.AdvertTypeRepository;
import com.kricko.repository.BusinessRepository;
import com.kricko.repository.PublicationRepository;

@Service("mailTemplateService")
public class MailTemplateServiceImpl implements MailTemplateService {

	@Autowired
    private VelocityEngine velocityEngine;
	@Autowired
	private AdvertSizeRepository adSizeRepo;
	@Autowired
	private AdvertTypeRepository adTypeRepo;
	@Autowired
	private PublicationRepository pubRepo;
	@Autowired
	private BusinessRepository businessRepo;

	private final String FOOTER_TEMPLATE = MailTemplating.TMPL_FOLDER + MailTemplating.TMPL_FOOTER;
	private final String TERMS_AND_CONDITIONS_TEMPLATE = MailTemplating.TMPL_FOLDER + MailTemplating.TMPL_TERMS_AND_CONDITIONS;
	
	private Long pubId;

	@Override
	public String buildTemplate(EmailType type, Orders orders) {
		return buildPublicationTemplate(type, orders, null);
	}
	
	@Override
	public String buildPublicationTemplate(EmailType type, Orders orders, Long pubId) {
		this.pubId = pubId;
		Map<String, Object> model = new HashMap<>();
        model.put("orderNumber", orders.getId());
        Business business = businessRepo.getOne(orders.getBusinessId());
        model.put("business", business);
        TemplateOrder order = buildEmailOrder(type, orders);
        if(null == order) {
        	return null;
        }
        model.put("orders", order);
        
        
        String template = MailTemplating.TMPL_ORDER_FOLDER + type.getValue()
            	+ "/" + MailTemplating.TMPL_ORDER_CONFIRMATION;
        StringBuilder body = new StringBuilder();
        body.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
                template, StandardCharsets.UTF_8.toString(), model));

        System.out.println(body.toString());
        appendCommonContent(body, model);
        System.out.println(body.toString());
        return body.toString();
	}
	
	private void appendCommonContent(StringBuilder body, Map<String, Object> model) {
		body.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
        		FOOTER_TEMPLATE, StandardCharsets.UTF_8.toString(), model));
        body.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
        		TERMS_AND_CONDITIONS_TEMPLATE, StandardCharsets.UTF_8.toString(), model));
    }
	
	private TemplateOrder buildEmailOrder(EmailType type, Orders orders) {
		TemplateOrder order = new TemplateOrder();
		List<OrderPart> orderParts = orders.getOrderParts();
		List<TemplateOrderPart> tmplOrderParts = convertOrderParts(orderParts);
		if(!tmplOrderParts.isEmpty()) {
			order.setOrderParts(tmplOrderParts);
			return order;
		}
		return null;
	}
	
	private List<TemplateOrderPart> convertOrderParts(List<OrderPart> orderParts) {
		List<TemplateOrderPart> tmplOrderParts = new ArrayList<>(0);
		for(OrderPart orderPart : orderParts) {
			TemplateOrderPart tmplOrderPart = new TemplateOrderPart();
			String month = new DateFormatSymbols().getMonths()[orderPart.getMonth() - 1];
			tmplOrderPart.setMonth(month);
			tmplOrderPart.setYear(orderPart.getYear());
			List<TemplateOrderPublication> tmplOrderPubs = convertOrderPublications(orderPart.getPublications());
			if(!tmplOrderPubs.isEmpty()) {
				tmplOrderPart.setPublications(tmplOrderPubs);
				tmplOrderParts.add(tmplOrderPart);
			}
		}
		return tmplOrderParts;
	}
	
	private List<TemplateOrderPublication> convertOrderPublications(List<OrderPublication> orderPublications) {
		List<TemplateOrderPublication> tmplOrderPubs = new ArrayList<>(0);
		for(OrderPublication orderPub : orderPublications) {
			System.out.println("Pub ID: " + pubId + "   Order Pub ID: " + orderPub.getPublicationId());
			if(null == pubId || pubId == orderPub.getPublicationId()) {
				TemplateOrderPublication tmplOrderPub = new TemplateOrderPublication();
				tmplOrderPub.setAdSize(adSizeRepo.findOne(orderPub.getAdSize()).getName());
				tmplOrderPub.setAdType(adTypeRepo.findOne(orderPub.getAdType()).getName());
				tmplOrderPub.setNote(orderPub.getNote());
				tmplOrderPub.setPublication(pubRepo.findOne(orderPub.getPublicationId()));
				tmplOrderPubs.add(tmplOrderPub);
			}
		}
		return tmplOrderPubs;
	}
}
