package app.example.core;


import app.example.Configurer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContentHandler {

    public static ArrayList<ArrayList<String>> handle(File file) {
        ArrayList<String> lineList = fileToLineList(file);
        ArrayList<ArrayList<String>> sheet = initializeSheet();
        processEachCell(lineList, sheet);
        return sheet;
    }

    private static ArrayList<String> fileToLineList(File file) {
        ArrayList<String> lineList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineList;
    }

    private static ArrayList<ArrayList<String>> initializeSheet() {
        ArrayList<ArrayList<String>> sheet = new ArrayList<>();
        sheet.add(new ArrayList<>());

        IntStream.range(0, Configurer.COLUMNS.size())
                .forEach(i -> sheet.get(0).add(Configurer.COLUMNS.get(i).getName()));

        return sheet;
    }

    private static void processEachCell(ArrayList<String> lineList, ArrayList<ArrayList<String>> sheet) {
        for (int i = 0; i < lineList.size(); i++) {
            List<String> line = Arrays.stream(
                    lineList.get(i)
                            .replaceAll("\\s+", " ")
                            .split(" ")
                    )
                    .collect(Collectors.toList()
            );
            ArrayList<String> row = new ArrayList<>();

            row.add(line.get(0));
            row.add(line.get(1).replace("(", "").replace(")", ""));
            row.add(line.get(4).replace("[", ""));
            row.add(strToDateTime(line.get(4).replace("[", "")));
            row.add(line.get(6).replace("\"", "").replace(",", ""));
            row.add(line.get(7).replace(",", ""));
            row.add(line.get(8).replace("\"", ""));
            row.add(line.get(9));
            row.add(line.get(10));
            row.add(line.get(12));

            sheet.add(row);
        }
    }

    private static String  strToDateTime(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        ZonedDateTime utcDateTime = dateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime koreaDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        return koreaDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
