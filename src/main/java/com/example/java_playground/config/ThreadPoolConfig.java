import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // Number of core threads
        executor.setMaxPoolSize(10);  // Maximum number of threads
        executor.setQueueCapacity(25); // Queue size for tasks
        executor.setThreadNamePrefix("ThreadPool-"); // Thread name prefix
        executor.initialize();
        return executor;
    }
}
