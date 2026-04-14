package net.aidencooper.pluton.mediaserver.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TranscodeManager {
    private final Set<String> activeJobs = ConcurrentHashMap.newKeySet();

    public boolean startJob(String video) {
        return this.activeJobs.add(video);
    }

    public void finishJob(String video) {
        this.activeJobs.remove(video);
    }

    public boolean isRunning(String video) {
        return this.activeJobs.contains(video);
    }
}
