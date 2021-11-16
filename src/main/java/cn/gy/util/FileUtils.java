package cn.gy.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Created by cc on 2018/12/10.
 */
@Slf4j
public class FileUtils {
    public static void deleteDirectory(Path directory) {
        if (Files.exists(directory)) {
            if (Files.isDirectory(directory)) {
                try(Stream<Path> stream = Files.walk(directory)) {
                    stream.sorted(Comparator.reverseOrder()).forEach(FileUtils::deleteFile);
                } catch (IOException e) {
                    log.trace("delete directory failed.", e);
                }
            } else {
                deleteFile(directory);
            }
        }
    }

    public static void deleteFile(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            log.trace("delete file failed.", e);
        }
    }

    public static void moveFile(Path src, Path dest) {
        try {
            if (Files.notExists(dest.getParent())) {
                Files.createDirectories(dest.getParent());
            }
            if (Files.exists(dest.getParent())) {
                Files.move(src, dest);
            }
        } catch (IOException e) {
            log.trace("move file failed.", e);
        }
    }
}
