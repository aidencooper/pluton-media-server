package net.aidencooper.pluton.mediaserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // Tell spring to scan for @Async functions
public class AsyncConfig {
    @Bean(name = "ffmpegExecutor")
    public Executor ffmpegExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(2); // Amount of threads ready for one job
        executor.setMaxPoolSize(4); // Max threads is 4 (2 jobs)
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("ffmpeg-");

        executor.initialize();
        return executor;
    }
}
