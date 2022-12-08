package com.gwidgets;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.jpa.entities.UserAttributeEntity;
import org.keycloak.models.jpa.entities.UserEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

public class FeelingSurveyRequiredActionProvider implements RequiredActionProvider {

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        var now = LocalDate.now();
        if (now.getDayOfWeek() == DayOfWeek.MONDAY && !now.toString().equals(context.getUser().getFirstAttribute("last_triggered_date"))) {
            context.getUser().addRequiredAction("feeling-survey");
            // I had to add this, otherwise the action will be triggered indefinitely on Mondays
            // and the user will not be able to use the app, we need a condition to escape after the first add
            addAttribute(context, "last_triggered_date", LocalDate.now().toString());
        }
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        context.challenge(context.form().createForm("how-do-you-feel.ftl"));
    }

    @Override
    public void processAction(RequiredActionContext context) {
        var skip = context.getHttpRequest().getFormParameters().getFirst("skip");
        if (skip != null) {
            context.success();
            return;
        }
        var feeling = context.getHttpRequest().getFormParameters().getFirst("feeling");
        addAttribute(context, "feeling", feeling);
        context.success();
    }

    @Override
    public void close() {

    }

    private void addAttribute(RequiredActionContext context, String attributeName, String attributeValue) {
        var entityManager = context.getSession().getProvider(JpaConnectionProvider.class).getEntityManager();
        var lastSurveyDate = new UserAttributeEntity();
        lastSurveyDate.setName(attributeName);
        lastSurveyDate.setValue(attributeValue);
        var userEntity = new UserEntity();
        userEntity.setId(context.getUser().getId());
        lastSurveyDate.setUser(userEntity);
        lastSurveyDate.setId(UUID.randomUUID().toString());
        entityManager.persist(lastSurveyDate);
    }
}
