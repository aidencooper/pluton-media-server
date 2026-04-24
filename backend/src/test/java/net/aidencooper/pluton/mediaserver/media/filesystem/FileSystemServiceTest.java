package net.aidencooper.pluton.mediaserver.media.filesystem;

import net.aidencooper.pluton.mediaserver.media.filesystem.port.IFileSystemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
public class FileSystemServiceTest {
    @Mock IFileSystemService fileSystemService;

    @Test
    void detects_existing_path() {
        Path path = Path.of("Movies");

        Mockito.when(this.fileSystemService.exists(path)).thenReturn(true);

        Assertions.assertTrue(this.fileSystemService.exists(path));
    }
}
