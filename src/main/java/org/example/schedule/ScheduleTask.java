package org.example.schedule;

import org.example.config.AppConfig;
import org.example.model.UserImp;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class ScheduleTask {

    private final Map<String, Integer> codeVerification;

    public ScheduleTask(AppConfig appConfig) {
        this.codeVerification = appConfig.getCodeVerification();
    }

    @Scheduled(fixedRate = 50000)
    public void performTask() {
        for (String userImp:codeVerification.keySet()) {
            codeVerification.put(userImp, getRandomNumber(999,99999));
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Map<String, Integer> getCodeVerification() {
        return codeVerification;
    }
}
