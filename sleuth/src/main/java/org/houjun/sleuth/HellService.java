package org.houjun.sleuth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HellService {

    private static final Log log = LogFactory.getLog(HellService.class);

    @Async
    public String backgroundFun() {
        log.info("backgroundFun");
        return "backgroundFun";
    }

    /**
     * 定时任务每10秒执行一次
     *
     * @return
     */
    @Scheduled(cron = "0/10 * * * *  ?")
    public void Schedule() {
        log.info("start:");
        backgroundFun();
        log.info("end:");
    }
}
