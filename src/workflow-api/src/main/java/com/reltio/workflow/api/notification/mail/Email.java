package com.reltio.workflow.api.notification.mail;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Contains information about sending email
 */
public class Email {
    private String to;
    private String subject;
    private String from;
    private String fromName;
    private String cc;
    private String bcc;
    private String replyTo;
    private String inReplyTo;
    private String htmlBody;
    private String textBody;
    private final List<Attachment> attachmentList = new LinkedList<>();

    public Email() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void addAttachment(Attachment attachment) {
        this.attachmentList.add(attachment);
    }
}
