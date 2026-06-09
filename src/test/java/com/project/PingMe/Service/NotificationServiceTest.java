package com.project.PingMe.Service;

import com.project.PingMe.dto.request.NotificationReq;
import com.project.PingMe.dto.response.NotificationRes;
import com.project.PingMe.enums.Channel;
import com.project.PingMe.enums.NotificationStatus;
import com.project.PingMe.model.NotificationLog;
import com.project.PingMe.repository.NotificationLogRepo;
import com.project.PingMe.service.AIEnhancerService;
import com.project.PingMe.service.AsyncNotificationWorker;
import com.project.PingMe.service.NotificationService;
import com.project.PingMe.service.RateLimitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    //Preparing Props : Whatever I used In the actual NotificationService
    @Mock
    private NotificationLogRepo notificationLogRepo;

    @Mock
    private AsyncNotificationWorker asyncNotificationWorker;

    @Mock
    private RateLimitService rateLimitService;

    @Mock
    private AIEnhancerService aiEnhancerService;

    //What I want to Test
    @InjectMocks
    private NotificationService notificationService;

    private NotificationReq sampleReq;
    private NotificationLog sampleLog;

    //Writing an Assertion and Taking care of Pre Requisites
    @BeforeEach
    public void setUp() {
        sampleReq = new NotificationReq();
        sampleReq.setRecipient("test@user.com");
        sampleReq.setChannel(Channel.EMAIL);
        sampleReq.setMessage("test message");

        sampleLog = new NotificationLog();
        sampleLog.setId(100L);
        sampleLog.setStatus(NotificationStatus.PENDING);
        sampleLog.setRecipient("test@user.com");
    }


    //We will Follow The Given-When-Then Pattern
    @Test
    void testProcessNotification() {
        //Given - Trying to make sure what the mock learns

        //Redis:OFF
        doNothing().when(rateLimitService).checkRateLimit(anyString());

        //Wheneever a call made then just Give this message instead
        when(aiEnhancerService.enhanceMessage("test message")).thenReturn("Corporate Level Alert");
        //Dont save in DB just give me the sampleLog
        when(notificationLogRepo.save(any(NotificationLog.class))).thenReturn(sampleLog);
        //Async : OFF
        doNothing().when(asyncNotificationWorker).sendAsync(any(),any(),anyMap());

        //When - Triggering the Actual Action
        NotificationRes res = notificationService.processNotification(sampleReq);

        //Then - Verifying The Results with Assertions
        //Check if Response is Null or not?
        assertNotNull(res);
        assertEquals(NotificationStatus.PENDING,res.getStatus()); //Did we get Response status PENDING?
        assertEquals(100L,res.getNotificationId());//Did we get what we have given, from the DB

        //Verify - Did we really call AsyncWorker and AIService
        verify(rateLimitService, times(1)).checkRateLimit(anyString());
        verify(aiEnhancerService, times(1)).enhanceMessage(anyString());
        verify(asyncNotificationWorker, times(1)).sendAsync(any(), any(), anyMap());
        verify(notificationLogRepo, times(1)).save(any(NotificationLog.class));
    }

}
