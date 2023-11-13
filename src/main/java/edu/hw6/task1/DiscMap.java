package edu.hw6.task1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscMap extends HashMap<String, String> implements Map<String, String> {
    private final String fileName;
    private final static String DELIMITER = ":";

    public DiscMap(String fileName) {
        this.fileName = fileName;
        File file = new File(fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            //если в файле есть записи, они загружаются в новую мапу
            load();
        }
    }

    @Override public String put(String key, String value) {
        if (!this.containsKey(key)) {
            writeEntry(key, value);
        }
        return super.put(key, value);
    }

    @Override public void putAll(Map<? extends String, ? extends String> m) {
        m.forEach((key, value) -> {
            if (!this.containsKey(key)) {
                writeEntry(key, value);
            }
        });

        super.putAll(m);
    }

    @Override
    public String remove(Object key) {
         var res = super.remove(key);
         save();
         return res;
    }

    //метод для сохранения содержимого мапы в любой файл (с перезаписью)
    public void save(String fileName) {

        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(
                this.entrySet().stream()
                    .map(entry -> entry.getKey() + DELIMITER + entry.getValue())
                    .collect(Collectors.joining("\n"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        save(fileName);
    }

    //добавление отдельной записи в файл
    private void writeEntry(String key, String value) {

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(key + DELIMITER + value + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        load(this.fileName);
    }

    //выгрузка данных из любого файла в мапу
    public void load(String fileName) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.lines().map(line -> line.split(DELIMITER))
                .forEach(splittedLine -> {
                    if (!this.containsKey(splittedLine[0])) {
                        if (fileName.equals(this.fileName)) {
                            this.putForLoading(splittedLine[0], splittedLine[1]);
                        } else {
                            this.put(splittedLine[0], splittedLine[1]);
                        }
                    }
                });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String putForLoading(String key, String value) {
        return super.put(key, value);
    }
}
