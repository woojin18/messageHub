package kr.co.uplus.cm.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(3);  // 기본 thread pool size
        threadPoolTaskExecutor.setMaxPoolSize(5);  //최대 thread pool size
        threadPoolTaskExecutor.setQueueCapacity(20);  // MaxPoolSize로 동작하는 경우, 대기하는 queue size
        return threadPoolTaskExecutor;
    }

}
