package com.abinbev.b2b.promotion.config;

import com.abinbev.b2b.promotion.properties.AsyncProperties;
import java.util.Map;
import java.util.concurrent.Executor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

  private AsyncProperties asyncProperties;

  @Autowired
  public AsyncConfig(AsyncProperties asyncProperties) {
    this.asyncProperties = asyncProperties;
  }

  @Bean(name = "asyncExecutor")
  public Executor promotionExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setThreadNamePrefix("Async-");
    threadPoolTaskExecutor.setCorePoolSize(asyncProperties.getCorePoolSize());
    threadPoolTaskExecutor.setMaxPoolSize(asyncProperties.getMaxPoolSize());
    threadPoolTaskExecutor.setQueueCapacity(asyncProperties.getQueueCapacity());
    threadPoolTaskExecutor.setTaskDecorator(new AsyncExecutorTaskDecorator());
    threadPoolTaskExecutor.afterPropertiesSet();
    threadPoolTaskExecutor.initialize();
    return threadPoolTaskExecutor;
  }

  static class AsyncExecutorTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
      final Map<String, String> contextMap = MDC.getCopyOfContextMap();
      return () -> {
        MDC.setContextMap(contextMap);
        runnable.run();
        MDC.clear();
      };
    }
  }
}
