package app.example.core;

import app.example.Configurer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class LogFileWriter {

    public static void write(ArrayList<ArrayList<String>> data, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");
            CellStyle style = defineCellStyle(workbook);
            defineCellWidth(sheet);

            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i);
                ArrayList<String> rowData = data.get(i);
                for (int j = 0; j < rowData.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(rowData.get(j));

                    if (i == 0) cell.setCellStyle(style);
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Excel 파일 작성 완료: " + filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void defineCellWidth(Sheet sheet) {
        IntStream.range(0, Configurer.COLUMNS.size())
                .forEach(i -> sheet.setColumnWidth(i, Configurer.COLUMNS.get(i).getWidth() * 256));
    }

    private static CellStyle defineCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(Configurer.TOP_CELL_COLOR);
        style.setFillPattern(Configurer.TOP_CELL_PATTERN);
        return style;
    }
}
