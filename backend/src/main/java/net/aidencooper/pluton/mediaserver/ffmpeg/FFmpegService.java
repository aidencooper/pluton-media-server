package net.aidencooper.pluton.mediaserver.ffmpeg;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import net.aidencooper.pluton.mediaserver.transcode.TranscodeManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class FFmpegService {
    private final TranscodeManager transcodeManager;

    public FFmpegService(TranscodeManager transcodeManager) {
        this.transcodeManager = transcodeManager;
    }

    @Async
    public void transcode(Path filePath, Path streamDir, String movieName) {
        try {
            FFmpeg.atPath() // Need to have ffmpeg installed
                    .addInput(UrlInput.fromPath(filePath))
                    .addOutput(
                            UrlOutput.toPath(streamDir.resolve("master.m3u8"))
                                    .setFormat("hls")
                                    .addArguments("-hls_time", "6") // Every .ts segment is 6 seconds long
                                    .addArguments("-hls_list_size", "0") // Keep all .ts segments
                                    .addArguments("-hls_playlist_type", "vod") // video on demand
                                    .addArguments("-hls_segment_filename", // segment_000.ts segment_001.ts
                                            streamDir.resolve("segment_%03d.ts").toString())
                                    .addArguments("-c:v", "libx264") // H.264 video codec
                                    .addArguments("-preset", "veryfast") // encoding speed to compression efficiency ratio
                                    .addArguments("-crf", "23") // video bitrate
                                    .addArguments("-c:a", "aac") // AAC audio codec
                                    .addArguments("-b:a", "128k") // audio bitrate
                    ).execute();
        } finally {
            this.transcodeManager.finishJob(movieName);
        }
    }
}
