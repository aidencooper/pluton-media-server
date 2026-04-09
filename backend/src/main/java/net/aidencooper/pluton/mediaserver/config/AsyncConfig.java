package net.aidencooper.pluton.mediaserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync // Tell spring to scan for @Async functions
public class AsyncConfig {
    @Bean(name = "ffmpegExecutor")
    public Executor ffmpegExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(1); // Initialized amount of threads
        executor.setMaxPoolSize(1); // Max amount of threads
        executor.setQueueCapacity(4); // Active jobs in the core pool size are included in the queue capacity
        executor.setThreadNamePrefix("ffmpeg-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); // Exception if a task is added that is greater than corePoolSize + queueCapacity
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(120);

        executor.initialize();
        return executor;
    }
}
