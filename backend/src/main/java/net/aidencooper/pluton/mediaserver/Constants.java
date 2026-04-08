package net.aidencooper.pluton.mediaserver;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final Path BASE_DIR = Paths.get(System.getProperty("user.dir"));

    public static final String MOVIE_PATH = System.getProperty("user.home") + "/Documents/Media/Movies";
    public static final String STREAM_PATH = System.getProperty("user.home") + "/Documents/Media/Streams";
}
