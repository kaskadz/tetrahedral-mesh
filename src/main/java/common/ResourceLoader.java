package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class ResourceLoader {
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

    public static URL getUpIconPath() {
        return getIconUrlOrThrow("triangle_up.png");
    }

    public static URL getDownIconPath() {
        return getIconUrlOrThrow("triangle_down.png");
    }

    public static URL getUpDownIconPath() {
        return getIconUrlOrThrow("triangle_updown.png");
    }

    private static URL getIconUrlOrThrow(String iconFileName) {
        URL iconPathUrl = ClassLoader.getSystemResource(String.format("icons/%s", iconFileName));

        if (iconPathUrl == null) {
            throw new RuntimeException(String.format("Couldn't find the up icon file: %s", iconFileName));
        }

        return iconPathUrl;
    }
}
