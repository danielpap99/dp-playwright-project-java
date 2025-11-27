package Playwright.project.websites;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

public class ClearFolderExtension implements BeforeAllCallback {

    private static final Path FOLDER = Paths.get("target/allure-results");
    private static boolean cleared = false;

    @Override
    public void beforeAll(ExtensionContext context) throws IOException {
        if (cleared) return;

        if (Files.exists(FOLDER)) {
            try (var paths = Files.walk(FOLDER)) {
                paths.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                throw new RuntimeException("Failed to delete: " + path, e);
                            }
                        });
            }
        }

        Files.createDirectories(FOLDER);
        cleared = true;
    }
}



