package com.reltio.workflow.api.notification.mail;

import java.util.Objects;

/**
 * Email attachment
 */
public class Attachment {
    private String content;
    private byte[] contentBytes;
    private String contentType;
    private String fileName;
    private String disposition;
    private String contentId;

    /**
     * Creates an instance of this class
     *
     * @param fileName file name
     * @param content  email content as string
     */
    public Attachment(String fileName, String content) {
        this.fileName = Objects.requireNonNull(fileName);
        this.content = Objects.requireNonNull(content);
    }

    /**
     * Creates an instance of this class
     *
     * @param fileName     file name
     * @param contentBytes email content as bytes
     */
    public Attachment(String fileName, byte[] contentBytes) {
        this.fileName = Objects.requireNonNull(fileName);
        this.contentBytes = Objects.requireNonNull(contentBytes);
    }

    public String getContent() {
        return content;
    }

    public byte[] getContentBytes() {
        return contentBytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
