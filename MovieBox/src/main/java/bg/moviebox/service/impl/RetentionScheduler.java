package bg.moviebox.service.impl;

import bg.moviebox.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RetentionScheduler {

    private final Logger LOGGER = LoggerFactory.getLogger(RetentionScheduler.class);
    private final NewsService newsService;


    public RetentionScheduler(NewsService newsService) {
        this.newsService = newsService;
    }

    @Scheduled(cron = "0 0 0 * * *") //Every day at midnight
    public void deleteOldNews() {
        LOGGER.info("Start deletion of old news.");
        newsService.cleanupOldNews();
        LOGGER.info("Start deletion finished.");
    }
}
