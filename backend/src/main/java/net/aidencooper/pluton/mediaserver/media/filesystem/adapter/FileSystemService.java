package net.aidencooper.pluton.mediaserver.media.filesystem.adapter;

import net.aidencooper.pluton.mediaserver.media.filesystem.port.IFileSystemService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FileSystemService implements IFileSystemService {
    @Override
    public Stream<Path> walk(Path root, int depth) throws IOException {
        return Files.walk(root, depth);
    }

    @Override
    public Stream<Path> list(Path path) throws IOException {
        return Files.list(path);
    }

    @Override
    public boolean exists(Path path) {
        return Files.exists(path);
    }

    @Override
    public boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }
}
