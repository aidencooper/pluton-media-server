package net.aidencooper.pluton.mediaserver.video;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import net.aidencooper.pluton.mediaserver.ffmpeg.FFmpegService;
import net.aidencooper.pluton.mediaserver.ffmpeg.FFmpegUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@EnableAsync
public class VideoService {
    private FFmpegService ffmpegService;

    public VideoService(FFmpegService ffmpegService) {
        this.ffmpegService = ffmpegService;
    }

    // temp placeholder
    private static final String MOVIE_PATH = System.getProperty("user.home") + "/Documents/Media/Movies";
    private static final String STREAM_PATH = System.getProperty("user.home") + "/Documents/Media/Streams";

    public String stream(String movieName) {
        Path inputPath = Paths.get(MOVIE_PATH, movieName + ".mp4");
        Path outputDir = Paths.get(STREAM_PATH, movieName);

        try { Files.createDirectories(outputDir); }
        catch (IOException exception) { throw new RuntimeException("Failed to create stream directory"); }

        Path playlist = outputDir.resolve("master.m3u8");

        if(!Files.exists(playlist)) this.ffmpegService.transcode(inputPath, outputDir);

        return playlist.toString();
    }
}
