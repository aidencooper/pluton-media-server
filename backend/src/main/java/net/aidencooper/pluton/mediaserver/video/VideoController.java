package net.aidencooper.pluton.mediaserver.video;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/{movieName}/stream")
    public void stream(@PathVariable String movieName) {
        String playlistPath = this.videoService.stream(movieName);
        System.out.println(playlistPath);
    }
}
