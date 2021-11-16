package cn.gy.util;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by cc on 2018/12/6.
 */
public class GZIPUtils {
    private static final Logger log = LoggerFactory.getLogger(GZIPUtils.class);

    public static void inputTar(TarArchiveOutputStream os, File file, String parent) {
        String filename = null == parent || parent.length() == 0 ? file.getName() : (parent + "/" + file.getName());
        try (FileInputStream fis = new FileInputStream(file)) {
            os.putArchiveEntry(new TarArchiveEntry(file, filename));
            IOUtils.copy(fis, os);
            os.closeArchiveEntry();
        } catch (IOException e) {
            log.error("io failed!", e);
        }
    }

    private static void dir2Tar(TarArchiveOutputStream os, File sourceDir, String parent) {
        for (File file : Objects.requireNonNull(sourceDir.listFiles())) {
            if (file.isDirectory()) {
                String newParent = null == parent || parent.length() == 0 ? file.getName() : (parent + "/" + file.getName());
                dir2Tar(os, file, newParent);
            } else {
                inputTar(os, file, parent);
            }
        }
    }

    private static void outputTar(TarArchiveInputStream is, File outputDir) throws IOException {
        createDirectory(outputDir);
        ArchiveEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            File file = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {
                createDirectory(file);
            } else {
                createDirectory(file.getParentFile());
                try (OutputStream os = new FileOutputStream(file)) {
                    IOUtils.copy(is, os);
                } catch (IOException e) {
                    log.error("io failed!", e);
                }
            }
        }
    }

    public static void tar(File[] sources, File target) {
        try (FileOutputStream fos = new FileOutputStream(target);
             TarArchiveOutputStream os = new TarArchiveOutputStream(fos)) {
            for (File file : sources) {
                inputTar(os, file, "");
            }
        } catch (IOException e) {
            log.error("io failed!", e);
        }
    }

    public static void unTar(File source, File outputDir) throws FileNotFoundException {
        if (source == null || !source.exists()) {
            throw new FileNotFoundException();
        }
        try (FileInputStream fis = new FileInputStream(source);
             TarArchiveInputStream is = new TarArchiveInputStream(fis)) {
            outputTar(is, outputDir);
        } catch (IOException e) {
            log.error("io failed!", e);
        }
    }

    public static File gz(File source) {
        File target = new File(source.getAbsolutePath() + ".gz");
        try (FileInputStream is = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(target);
             GZIPOutputStream os = new GZIPOutputStream(fos)) {
            IOUtils.copy(is, os);
        } catch (IOException e) {
            log.error("io failed!", e);
            return null;
        }
        return target;
    }

    public static File unGz(File source) {
        File target = new File(source.getAbsolutePath().replace(".gz", ""));
        try (FileInputStream fis = new FileInputStream(source);
             GZIPInputStream is = new GZIPInputStream(fis);
             FileOutputStream os = new FileOutputStream(target)) {
            IOUtils.copy(is, os);
        } catch (IOException e) {
            log.error("io failed!", e);
            return null;
        }
        return target;
    }

    public static void tarGz(File[] sources, File target) throws FileNotFoundException {
        if (sources == null) {
            throw new FileNotFoundException();
        }
        try (FileOutputStream fos = new FileOutputStream(target);
             TarArchiveOutputStream os = new TarArchiveOutputStream(new GZIPOutputStream(fos))) {
            for (File file : sources) {
                inputTar(os, file, "");
            }
        } catch (IOException e) {
            log.error("io failed!", e);
        }
    }

    public static void tarGz(Map<String, List<File>> sources, File target) throws FileNotFoundException {
        if (sources == null) {
            throw new FileNotFoundException();
        }
        try (FileOutputStream fos = new FileOutputStream(target);
             TarArchiveOutputStream os = new TarArchiveOutputStream(new GZIPOutputStream(fos))) {
            sources.forEach((k, v) -> v.forEach(e -> {
                if (e.isDirectory()) {
                    dir2Tar(os, e, k);
                } else {
                    inputTar(os, e, k);
                }
            }));
        } catch (IOException e) {
            log.error("io failed!", e);
        }
    }

    public static File tarGz(File sourceDir) throws FileNotFoundException {
        if (sourceDir == null || !sourceDir.exists()) {
            throw new FileNotFoundException();
        }
        File target = new File(sourceDir.getAbsolutePath() + ".tar.gz");
        tarGz(sourceDir, target);
        return target;
    }

    public static void tarGz(File sourceDir, File target) throws FileNotFoundException {
        if (sourceDir == null || !sourceDir.exists()) {
            throw new FileNotFoundException();
        }
        try (FileOutputStream fos = new FileOutputStream(target);
             TarArchiveOutputStream os = new TarArchiveOutputStream(new GZIPOutputStream(fos))) {
            dir2Tar(os, sourceDir, "");
        } catch (IOException e) {
            log.error("io failed!", e);
        }
    }

    public static File unTarGz(File source) throws FileNotFoundException {
        if (source == null || !source.exists()) {
            throw new FileNotFoundException();
        }
        File outputDir = new File(source.getAbsolutePath().replace(".tar.gz", ""));
        unTarGz(source, outputDir);
        return outputDir;
    }

    public static void unTarGz(File source, File outputDir) throws FileNotFoundException {
        if (source == null || !source.exists()) {
            throw new FileNotFoundException();
        }
        try (FileInputStream fis = new FileInputStream(source);
             TarArchiveInputStream is = new TarArchiveInputStream(new GZIPInputStream(fis))) {
            outputTar(is, outputDir);
        } catch (IOException e) {
            log.error("io failed!", e);
        }
    }

    public static void createDirectory(File dir) {
        if (!dir.exists()) {
            if (!dir.getParentFile().exists()) {
                createDirectory(dir.getParentFile());
            }
            boolean bool = dir.mkdirs();
            if (!bool) {
                log.info("mkdir failed.");
            }
        }
    }
}
