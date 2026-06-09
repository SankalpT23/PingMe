package com.project.PingMe.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.PingMe.controller.NotificationController;
import com.project.PingMe.dto.request.NotificationReq;
import com.project.PingMe.dto.response.NotificationRes;
import com.project.PingMe.enums.Channel;
import com.project.PingMe.enums.NotificationStatus;
import com.project.PingMe.service.JwtService;
import com.project.PingMe.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class controllerTest {

    @Autowired
    private MockMvc mockMvc; //programmatical PostMan

    @Autowired
    private ObjectMapper objectMapper; // JAVA Object to JSON

    @MockitoBean
    private NotificationService notificationService;

    @MockitoBean
    private JwtService jwtService;

    private NotificationReq sampleReq;
    private NotificationRes sampleRes;

    @BeforeEach
    public void setup() {
        sampleReq = new NotificationReq();
        sampleReq.setRecipient("sanku@techie.com");
        sampleReq.setChannel(Channel.EMAIL);
        sampleReq.setMessage("Hello World");

        sampleRes = new NotificationRes(1L, NotificationStatus.PENDING);
    }

    @Test
    public void sendNotification() throws Exception {
        //Given - When Controller Calls The Service Then return this sample Response
        when(notificationService.processNotification(any(NotificationReq.class))).thenReturn(sampleRes);

        //When & Then - perform the Post request and assert deliverables
        mockMvc.perform(MockMvcRequestBuilders.post("/notify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleReq)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId").value(1L))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
}
