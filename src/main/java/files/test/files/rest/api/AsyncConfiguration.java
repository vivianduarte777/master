package files.test.files.rest.api;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class AsyncConfiguration {
    @Qualifier
    private String urlAddress  = "https://github.com/vivianduarte777/filesTest/";


//@Bean (name = "urlAddress")
    public Executor taskExecutor() {
         final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("ReadFilesThread-");
         // executor.setThreadNamePrefix(urlAddress);
        executor.initialize();
        return executor;
    }

}
