package app.example.core;

import app.example.Configurer;
import app.example.data.FileType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class GzipDecompressor {

    public static File decompressGzipFile(String gzipFilePath) {
        File tempFile = null;

        try (GZIPInputStream gis = new GZIPInputStream(new FileInputStream(gzipFilePath))) {

            Path downloadsPath = Paths.get(Configurer.getFilePath(FileType.LOG));
            tempFile = downloadsPath.toFile();

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = gis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }

            System.out.println("압축 해제 완료: " + tempFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile;
    }
}
