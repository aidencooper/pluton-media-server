package net.aidencooper.pluton.mediaserver.media.filesystem.port;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface IFileSystemService {
    Stream<Path> walk(Path root, int depth) throws IOException;
    Stream<Path> list(Path path) throws IOException;
    boolean exists(Path path);
    boolean isDirectory(Path path);
}
