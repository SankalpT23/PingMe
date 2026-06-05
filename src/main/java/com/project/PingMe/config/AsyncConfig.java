package com.project.PingMe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); //Core Workers - Always Ready Threads
        executor.setQueueCapacity(25); // Waiting Room - If 5 workers are busy, 25 req wil be in line
        executor.setMaxPoolSize(10);//Max Workers - If Queue is Full then Total 10 threads spin-up

        executor.setThreadNamePrefix("PingMe-Async-");
        executor.initialize();
        return executor;
    }
}
