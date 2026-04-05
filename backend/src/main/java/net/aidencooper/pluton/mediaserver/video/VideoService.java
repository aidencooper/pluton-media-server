package net.aidencooper.pluton.mediaserver.video;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoService {
    // temp placeholder
    private static final String MOVIE_PATH = System.getProperty("user.home") + "/Documents/Media/Movies";
    private static final String STREAM_PATH = System.getProperty("user.home") + "/Documents/Media/Streams";

    public String stream(String movieName) {
        Path inputPath = Paths.get(MOVIE_PATH, movieName + ".mp4");
        Path outputDir = Paths.get(STREAM_PATH, movieName);

        try { Files.createDirectories(outputDir); }
        catch (IOException exception) { throw new RuntimeException("Failed to create stream directory"); }

        Path playlist = outputDir.resolve("master.m3u8");

        if(!Files.exists(playlist)) new Thread(() -> ffmpeg(inputPath, outputDir)).start();

        return playlist.toString();
    }

    // inputPath = ~/Documents/Media/Movies/toystory.mp4
    // outputDir = ~/Documents/Media/Streams/toystory
    private void ffmpeg(Path inputPath, Path outputDir) {
        FFmpeg.atPath(Paths.get("/opt/homebrew/bin"))
                .addInput(UrlInput.fromPath(inputPath))
                .addOutput(
                        UrlOutput.toPath(outputDir.resolve("master.m3u8"))
                                .setFormat("hls")
                                .addArguments("-hls_time", "6") // Every .ts segment is 6 seconds long
                                .addArguments("-hls_list_size", "0") // Keep all .ts segments
                                .addArguments("-hls_playlist_type", "vod") // video on demand
                                .addArguments("-hls_segment_filename", // segment_000.ts segment_001.ts
                                        outputDir.resolve("segment_%03d.ts").toString())
                                .addArguments("-c:v", "libx264") // H.264 video codec
                                .addArguments("-preset", "veryfast") // encoding speed to compression efficiency ratio
                                .addArguments("-crf", "23") // video bitrate
                                .addArguments("-c:a", "aac") // AAC audio codec
                                .addArguments("-b:a", "128k") // audio bitrate
                ).execute();
    }
}
