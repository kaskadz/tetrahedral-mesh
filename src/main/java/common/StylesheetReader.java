package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class StylesheetReader {
    public static String readStyleSheet() {
        InputStream stream = ClassLoader.getSystemResourceAsStream("styles.css");
        if (stream == null) {
            throw new RuntimeException("Couldn't find the styles file");
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            return br
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Reading the styles failed", e);
        }
    }
}
