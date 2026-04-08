package net.aidencooper.pluton.mediaserver.video;

import net.aidencooper.pluton.mediaserver.Constants;
import net.aidencooper.pluton.mediaserver.ffmpeg.FFmpegService;
import net.aidencooper.pluton.mediaserver.transcode.TranscodeManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoService {
    private final FFmpegService ffmpegService;
    private final TranscodeManager transcodeManager;

    public VideoService(FFmpegService ffmpegService, TranscodeManager transcodeManager) {
        this.ffmpegService = ffmpegService;
        this.transcodeManager = transcodeManager;
    }

    public String stream(String movieName) {
        Path inputPath = Paths.get(Constants.MOVIE_PATH, movieName + ".mp4");
        Path outputDir = Paths.get(Constants.STREAM_PATH, movieName);

        if(!Files.exists(inputPath)) throw new RuntimeException("File not found: " + inputPath);

        try { Files.createDirectories(outputDir); }
        catch (IOException exception) { throw new RuntimeException("Failed to create stream directory", exception); }

        Path playlist = outputDir.resolve("master.m3u8");

        if(!Files.exists(playlist) && this.transcodeManager.startJob(movieName)) this.ffmpegService.transcode(inputPath, outputDir, movieName);

        return "/streams/" + movieName + "/master.m3u8";
    }
}
