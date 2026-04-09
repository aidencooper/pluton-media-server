package net.aidencooper.pluton.mediaserver.ffmpeg;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import net.aidencooper.pluton.mediaserver.transcode.TranscodeManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

// https://trac.ffmpeg.org/wiki

// https://trac.ffmpeg.org/wiki/Encode/H.264
// https://trac.ffmpeg.org/wiki/Encode/H.265

@Service
public class FFmpegService {
    private final TranscodeManager transcodeManager;

    public FFmpegService(TranscodeManager transcodeManager) {
        this.transcodeManager = transcodeManager;
    }

    @Async("ffmpegExecutor")
    public CompletableFuture<Void> transcode(Path filePath, Path streamDir) {
        return CompletableFuture.runAsync(() -> {
            FFmpeg.atPath() // Need to have ffmpeg installed
                    .addInput(UrlInput.fromPath(filePath))
                    .addOutput(
                            UrlOutput.toPath(streamDir.resolve("master.m3u8"))
                                    .setFormat("hls")
                                    .addArguments("-hls_time", "6") // Every .ts segment is 6 seconds long
                                    .addArguments("-hls_list_size", "6") // Amount of segments in the playlist (0 = all)
                                    .addArguments("-hls_playlist_type", "event") // VOD (Immutable) vs Event (Mutable)
                                    .addArguments("-hls_segment_filename", // segment_000.ts segment_001.ts
                                            streamDir.resolve("segment_%03d.ts").toString())
                                    // Video settings
                                    .addArguments("-c:v", "libx264") // H.264 video codec
                                    .addArguments("-preset", "veryfast") // Higher encoding speed : less compression
                                    .addArguments("-crf", "30") // Video bitrate
                                    .addArguments("-profile:v", "baseline")
                                    .addArguments("-level", "3.0") // Compatibility level
                                    .addArguments("-pix_fmt", "yuv420p") // Pixel format
                                    // Audio settings
                                    .addArguments("-c:a", "aac") // AAC audio codec
                                    .addArguments("-b:a", "128k") // Audio bitrate
                                    .addArguments("-ar", "48000") // Standard sample rate
                                    .addArguments("-ac", "2") // Stereo audio
                                    // Segment settings
                                    .addArguments("-f", "hls")
                                    .addArguments("-bsf:v", "h264_mp4toannexb")
                                    .addArgument("-sn")
                    ).execute();
        });
    }
}
