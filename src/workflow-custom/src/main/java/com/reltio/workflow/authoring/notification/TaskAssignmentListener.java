package com.reltio.workflow.authoring.notification;

import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.listeners.WorkflowTaskListener;
import com.reltio.workflow.api.notification.mail.Notification;
import com.reltio.workflow.api.notification.mail.NotificationSenderService;
import com.reltio.workflow.api.services.AuthService;
import com.reltio.workflow.api.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskAssignmentListener implements WorkflowTaskListener {

    private static final String TEMPLATES_PATH = "/templates/notifications/CompleteCreationTask.html";
    private static final Pattern EXTRACT_CHANGE_REQUEST_PATTERN = Pattern.compile("^(changeRequests/[A-Za-z0-9]*).*");

    @WorkflowService
    NotificationSenderService notificationSenderService;
    @WorkflowService
    AuthService authService;

    private final String notificationTemplate;

    public TaskAssignmentListener() throws IOException {
        this.notificationTemplate = getTemplate();
    }

    @Override
    public void notify(Task task) {
        Notification notification = new Notification(task.getTenantId());
        String title = String.format("[Reltio Cloud] %s assigned an authoring review task to you", authService.getActiveUsername());
        notification.setSubject(title);
        notification.addTo(notificationSenderService.resolveTaskAssigneeEmail(task));
        notification.setBody(createBody(task));
        notificationSenderService.sendNotification(notification);
    }

    private String getTemplate() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(TEMPLATES_PATH)) {
            Objects.requireNonNull(inputStream, String.format("Template file %s not found", TEMPLATES_PATH));
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private String createBody(Task task) {
        String changeRequestUri = getChangeRequestUri(task.getObjectUris());
        String uri = String.format("%s/nui/%s/authoring?entityUri=%s", task.getEnvironmentUrl(), task.getTenantId(), changeRequestUri);
        return notificationTemplate
                .replace("${assignee}", task.getAssignee())
                .replace("${performer}", authService.getActiveUsername())
                .replace("${changeRequestUri}", uri);
    }

    private static String getChangeRequestUri(List<String> objectUris) {
        for (String objectUri: objectUris) {
            Matcher matcher = EXTRACT_CHANGE_REQUEST_PATTERN.matcher(objectUri);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }
}
