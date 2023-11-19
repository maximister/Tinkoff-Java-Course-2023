package edu.project3.http_status_codes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class HttpStatusCodesHandler {
    private final Path pathToCodes;
    private Map<Integer, String> httpStatusMap;

    private static final String DELIMITER = ":";

    public HttpStatusCodesHandler(Path pathToCodes) {
        this.pathToCodes = pathToCodes;

        if (!Files.exists(pathToCodes)) {
            HttpStatusCodesLoader  loader = new HttpStatusCodesLoader(pathToCodes);
            loader.saveCodesToFile();
        }
    }

    public String getHttpStatusDescription(Integer status) {
        if (httpStatusMap == null) {
            createHttpStatusMap();
        }
        return httpStatusMap.get(status);
    }

    private void createHttpStatusMap() {
        httpStatusMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(pathToCodes.toString()))) {
            reader.lines()
                .map(line -> line.split(DELIMITER))
                .forEach(status -> {
                    httpStatusMap.put(Integer.valueOf(status[0]), status[1]);
                });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
