package app.example.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
    XLSX(".xlsx"),
    LOG(".log");

    private final String extension;
}