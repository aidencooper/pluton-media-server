package net.aidencooper.pluton.mediaserver.video;

import net.aidencooper.pluton.mediaserver.Constants;
import net.aidencooper.pluton.mediaserver.ffmpeg.FFmpegService;
import net.aidencooper.pluton.mediaserver.transcode.TranscodeManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class VideoServiceTest {
    @Autowired private VideoService videoService;
    @Autowired private FFmpegService fFmpegService;
    @Autowired private TranscodeManager transcodeManager;
    @Autowired private ThreadPoolTaskExecutor ffmpegExecutor;

    @Test
    public void testMultipleStreams() throws InterruptedException {
        List<String> videos = List.of("avatar", "avengers", "mario", "spiderman");
        Map<String, CompletableFuture<Void>> futures = new HashMap<>();

        for(String video : videos) {
            Path inputPath = Paths.get(Constants.MOVIE_PATH, video + ".mkv");
            Path outputDir = Paths.get(Constants.STREAM_PATH, video);

            futures.put(video, this.fFmpegService.transcode(inputPath, outputDir));
        }

        futures.values().forEach(CompletableFuture::join);

        for(String video : videos) {
            Path playlist = Paths.get(Constants.STREAM_PATH, video, "master.m3u8");
            Assertions.assertTrue(Files.exists(playlist), "Playlist should exist for " + video);
        }
    }
}
