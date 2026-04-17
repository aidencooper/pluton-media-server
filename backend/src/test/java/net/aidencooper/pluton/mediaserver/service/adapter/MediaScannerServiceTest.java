package net.aidencooper.pluton.mediaserver.service.adapter;

import jakarta.persistence.criteria.CriteriaBuilder;
import net.aidencooper.pluton.mediaserver.Constants;
import net.aidencooper.pluton.mediaserver.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.domain.entity.LibraryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

@SpringBootTest
public class MediaScannerServiceTest {
    @Autowired
    private MediaScannerService mediaScannerService;

    private final Library movieLibrary;
    private final Library tvLibrary;

    public MediaScannerServiceTest() {
        Instant now = Instant.now();

        this.movieLibrary = new Library(
                0L,
                now,
                now,
                "Movie Library",
                LibraryType.MOVIES,
                List.of(Constants.MOVIE_PATH),
                true
        );

        this.tvLibrary = new Library(
                0L,
                now,
                now,
                "TV Library",
                LibraryType.TV,
                List.of(Constants.MOVIE_PATH),
                true
        );
    }


    @Test
    public void testScan() {
        System.out.println("TEST 1:");
        this.mediaScannerService.scanLibrary(this.movieLibrary);

        System.out.println("TEST 2:");
        this.mediaScannerService.scanLibrary(this.tvLibrary);
    }
}
