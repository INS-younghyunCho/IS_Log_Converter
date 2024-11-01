package app.example.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Column {
    private String name;
    private Integer width;
}
