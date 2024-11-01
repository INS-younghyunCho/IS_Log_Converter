package app.example;

import app.example.data.Column;
import app.example.data.FileType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Configurer {

    public static final ArrayList<Column> COLUMNS = initializeColumnMap();
    public static final short           TOP_CELL_COLOR = IndexedColors.GREY_25_PERCENT.getIndex();
    public static final FillPatternType TOP_CELL_PATTERN = FillPatternType.SOLID_FOREGROUND;

    public static String getFilePath(FileType fileType) {
        return new StringBuilder()
                .append(System.getenv("USERPROFILE"))
                .append("\\Downloads\\")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")))
                .append(fileType.getExtension())
                .toString();
    }

    private static ArrayList<Column> initializeColumnMap() {
        return new ArrayList<>(
                Arrays.asList(
                        new Column("IP", 10),
                        new Column("REDACTED", 15),
                        new Column("DATETIME (ORIGINAL)", 20),
                        new Column("DATETIME (KOREA)", 20),
                        new Column("HTTP_METHOD", 15),
                        new Column("URL", 20),
                        new Column("PROTOCOL", 15),
                        new Column("STATUS_CODE", 15),
                        new Column("DURATION", 20),
                        new Column("ID", 50)
                )
        );
    }
}
