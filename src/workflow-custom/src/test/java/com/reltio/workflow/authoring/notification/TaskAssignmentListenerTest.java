package com.reltio.workflow.authoring.notification;

import com.reltio.workflow.api.notification.mail.Notification;
import com.reltio.workflow.api.notification.mail.NotificationSenderService;
import com.reltio.workflow.api.services.AuthService;
import com.reltio.workflow.api.tasks.Task;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class TaskAssignmentListenerTest {

    private static final String TENANT_ID = "TenantId";
    private static final String ASSIGNEE_NAME = "assignee_test";
    private static final String USER_NAME = "user";
    private static final String OWNER_TASK_NAME = "owner_task";
    private static final String ENVIRONMENT_URL = "https://example.com";
    private static final String ASSIGNEE_EMAIL = "assignee@example.com";
    private static final String CHANGE_REQUEST_URI = "changeRequests/00000001";

    
    @Test
    public void trueTest() throws Exception {
        NotificationSenderServiceStub notificationSenderServiceStub = new NotificationSenderServiceStub();
        TaskAssignmentListener notificationService = new TaskAssignmentListener();
        notificationService.notificationSenderService = notificationSenderServiceStub;
        AuthService authService = Mockito.mock(AuthService.class);
        doReturn(USER_NAME).when(authService).getActiveUsername();
        notificationService.authService = authService;

        Task task = Mockito.mock(Task.class);
        when(task.getReltioUser()).thenReturn(OWNER_TASK_NAME);
        when(task.getAssignee()).thenReturn(ASSIGNEE_NAME);
        when(task.getEnvironmentUrl()).thenReturn(ENVIRONMENT_URL);
        when(task.getTenantId()).thenReturn(TENANT_ID);
        when(task.getObjectUris()).thenReturn(List.of("entities/objectUri1", CHANGE_REQUEST_URI));

        notificationService.notify(task);

        Notification notification = notificationSenderServiceStub.getNotification();
        assertNotNull(notification);
        assertEquals(TENANT_ID, notification.getTenantId());
        assertEquals("[Reltio Cloud] " + USER_NAME + " assigned an authoring review task to you", notification.getSubject());
        assertNotNull(notification.getTo());
        assertEquals(1, notification.getTo().size());
        assertEquals(ASSIGNEE_EMAIL, notification.getTo().get(0));
        assertNotNull(notification.getBody());
        assertTrue(notification.getBody().contains(ASSIGNEE_NAME) && notification.getBody().contains(USER_NAME));
        String uri = String.format("%s/nui/%s/authoring?entityUri=%s", ENVIRONMENT_URL, TENANT_ID, CHANGE_REQUEST_URI);
        assertTrue(notification.getBody().contains(uri));
    }

    @Test
    public void emptyChangeRequestUriTest() throws Exception {
        NotificationSenderServiceStub notificationSenderServiceStub = new NotificationSenderServiceStub();
        TaskAssignmentListener notificationService = new TaskAssignmentListener();
        notificationService.notificationSenderService = notificationSenderServiceStub;
        AuthService authService = Mockito.mock(AuthService.class);
        doReturn(USER_NAME).when(authService).getActiveUsername();
        notificationService.authService = authService;

        Task task = Mockito.mock(Task.class);
        when(task.getReltioUser()).thenReturn(OWNER_TASK_NAME);
        when(task.getAssignee()).thenReturn(ASSIGNEE_NAME);
        when(task.getObjectUris()).thenReturn(List.of());

        notificationService.notify(task);

        Notification notification = notificationSenderServiceStub.getNotification();
        assertNotNull(notification);
        assertNull(notification.getTenantId());
        assertEquals("[Reltio Cloud] " + USER_NAME + " assigned an authoring review task to you", notification.getSubject());
        assertNotNull(notification.getTo());
        assertEquals(1, notification.getTo().size());
        assertEquals(ASSIGNEE_EMAIL, notification.getTo().get(0));
        assertNotNull(notification.getBody());
        assertTrue(notification.getBody().contains(ASSIGNEE_NAME) && notification.getBody().contains(USER_NAME));
        assertTrue(notification.getBody().contains("null/nui/null/authoring?entityUri=null"));
    }

    private static class NotificationSenderServiceStub implements NotificationSenderService {
        private Notification notification;

        public void sendNotification(Notification notification) {
            this.notification = notification;
        }

        public String resolveTaskOwnerEmail(Task task) {
            return "owner@example.com";
        }

        public String resolveTaskAssigneeEmail(Task task) {
            return ASSIGNEE_EMAIL;
        }

        public Notification getNotification() {
            return notification;
        }
    }
}
