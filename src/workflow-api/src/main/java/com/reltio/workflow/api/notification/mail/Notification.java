package com.reltio.workflow.api.notification.mail;

import java.util.LinkedList;
import java.util.List;

public class Notification {
    private final String tenantId;
    private final List<String> toList = new LinkedList<>();
    private String subject;
    private String body;
    private final List<Attachment> attachmentList = new LinkedList<>();

    public Notification(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void addTo(String to) {toList.add(to);}

    public List<String> getTo() {
        return toList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void addAttachment(Attachment attachment) {
        this.attachmentList.add(attachment);
    }
}
