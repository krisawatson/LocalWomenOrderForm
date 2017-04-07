/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.service;

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
import com.kricko.repository.PublicationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.nio.charset.StandardCharsets;
import java.text.DateFormatSymbols;
import java.util.*;

@Service("mailTemplateService")
public class MailTemplateServiceImpl implements MailTemplateService {

    private final VelocityEngine velocityEngine;
    private final AdvertSizeRepository adSizeRepo;
    private final AdvertTypeRepository adTypeRepo;
    private final PublicationRepository pubRepo;

    private final Logger LOGGER = LogManager.getLogger();

    private Long pubId;

    @Autowired
    public MailTemplateServiceImpl(VelocityEngine velocityEngine, AdvertSizeRepository adSizeRepo,
                                   AdvertTypeRepository adTypeRepo, PublicationRepository pubRepo) {
        this.velocityEngine = velocityEngine;
        this.adSizeRepo = adSizeRepo;
        this.adTypeRepo = adTypeRepo;
        this.pubRepo = pubRepo;
    }

    @Override
    public String buildTemplate(EmailType type, Business business, Orders orders) {
        return buildTemplate(type, business, orders, null);
    }

    @Override
    public String buildTemplate(EmailType type, Business business, Orders orders, Long pubId) {
        LOGGER.info("Building template for publication " + pubId);
        this.pubId = pubId;
        Map<String, Object> model = new HashMap<>();
        model.put("orderNumber", orders.getId());
        LOGGER.info("Business ID is " + business.getId());
        model.put("business", business);
        model.put("customerSignature", orders.getCustomerSignature());
        model.put("userSignature", orders.getUserSignature());
        TemplateOrder order = buildEmailOrder(type, orders);
        if (null == order) {
            return null;
        }
        model.put("orders", order);

        String template = MailTemplating.TMPL_ORDER_FOLDER + type.getValue()
                + "/" + MailTemplating.TMPL_ORDER_CONFIRMATION;
        StringBuilder body = new StringBuilder();
        body.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                template, StandardCharsets.UTF_8.toString(), model));
        appendCommonContent(body, model);
        return body.toString();
    }

    private void appendCommonContent(StringBuilder body, Map<String, Object> model) {
        String footerTemplate = MailTemplating.TMPL_FOLDER + MailTemplating.TMPL_FOOTER;
        body.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                footerTemplate, StandardCharsets.UTF_8.toString(), model));
        String signatureTemplate = MailTemplating.TMPL_FOLDER + MailTemplating.TMPL_SIGNATURE;
        body.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                signatureTemplate, StandardCharsets.UTF_8.toString(), model));
        String termsAndConditionsTemplate = MailTemplating.TMPL_FOLDER + MailTemplating.TMPL_TERMS_AND_CONDITIONS;
        body.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                termsAndConditionsTemplate, StandardCharsets.UTF_8.toString(), model));
    }

    private TemplateOrder buildEmailOrder(EmailType type, Orders orders) {
        LOGGER.info("Building Email Order");
        TemplateOrder order = new TemplateOrder();
        List<OrderPart> orderParts = orders.getOrderParts();
        List<TemplateOrderPart> tmplOrderParts = convertOrderParts(orderParts);
        if (!tmplOrderParts.isEmpty()) {
            LOGGER.debug("Template Order Parts is not empty");
            order.setOrderParts(tmplOrderParts);
            return order;
        }
        return null;
    }

    private List<TemplateOrderPart> convertOrderParts(List<OrderPart> orderParts) {
        LOGGER.info("Converting Order Parts");
        LOGGER.debug("Parts size is " + orderParts.size());
        List<TemplateOrderPart> tmplOrderParts = new ArrayList<>(0);
        for (OrderPart orderPart : orderParts) {
            TemplateOrderPart tmplOrderPart = new TemplateOrderPart();
            String month = new DateFormatSymbols().getMonths()[orderPart.getMonth() - 1];
            tmplOrderPart.setMonth(month);
            tmplOrderPart.setYear(orderPart.getYear());
            List<TemplateOrderPublication> tmplOrderPubs = convertOrderPublications(orderPart.getPublications());
            if (!tmplOrderPubs.isEmpty()) {
                LOGGER.debug("Template Order Publication is not empty");
                tmplOrderPart.setPublications(tmplOrderPubs);
                tmplOrderParts.add(tmplOrderPart);
            }
        }
        return tmplOrderParts;
    }

    private List<TemplateOrderPublication> convertOrderPublications(List<OrderPublication> orderPublications) {
        LOGGER.info("Converting Order Publications");
        LOGGER.debug("Publications size is " + orderPublications.size());
        List<TemplateOrderPublication> tmplOrderPubs = new ArrayList<>(0);
        for (OrderPublication orderPub : orderPublications) {
            if (null == pubId || Objects.equals(pubId, orderPub.getPublicationId())) {
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
