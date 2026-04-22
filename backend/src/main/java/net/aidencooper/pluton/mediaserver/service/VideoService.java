package net.aidencooper.pluton.mediaserver.service;

import lombok.Getter;
import net.aidencooper.pluton.mediaserver.config.PlutonProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Getter
public class VideoService {
    private final FFmpegService ffmpegService;
    private final PlutonProperties plutonProperties;
    private final TranscodeManager transcodeManager;

    public VideoService(FFmpegService ffmpegService, PlutonProperties plutonProperties, TranscodeManager transcodeManager) {
        this.ffmpegService = ffmpegService;
        this.plutonProperties = plutonProperties;
        this.transcodeManager = transcodeManager;
    }

    public String stream(String movieName) {
//        Path inputPath = this.plutonProperties.getMediaPath().resolve("Movies").resolve(movieName + ".mkv");
//        Path outputDir = this.plutonProperties.getMediaPath().resolve("Streams").resolve(movieName);
//
//        if(!Files.exists(inputPath)) throw new RuntimeException("File not found: " + inputPath);
//
//        try { Files.createDirectories(outputDir); }
//        catch (IOException exception) { throw new RuntimeException("Failed to create stream directory", exception); }
//
//        Path playlist = outputDir.resolve("master.m3u8");
//
//        if(!Files.exists(playlist) && this.transcodeManager.startJob(movieName)) this.ffmpegService.transcode(inputPath, outputDir).thenRun(() -> this.transcodeManager.finishJob(movieName));
//
        return "/streams/" + movieName + "/master.m3u8";
    }
}
