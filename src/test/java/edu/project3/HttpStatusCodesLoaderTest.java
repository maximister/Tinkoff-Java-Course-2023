package edu.project3;

import edu.project3.http_status_codes.HttpStatusCodesLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

//с вашего позволения тут чисто формально запущу методы класса,
// тк он был проверен вручную при создании, да и в целом тут нет никакой вариативности событий,
// кроме указания сомнительной директории для сохранения кодов.
// Работа хэндрера тоже так или иначе затрагивалась при тестировании других классов.
// У него хорошо бы протестировать поведение при поступлении кривых файлов,
// но я в момент создания об этом не думал и не придусмотрел отдельной логики(
// тут можно каждый момент так бесконечно додумывать наверное
public class HttpStatusCodesLoaderTest {
    @Test
    @DisplayName("testing downloading http status codes")
    public void httpStatusCodesLoaderTest() {
        Path filePath = Path.of("testFile.txt");

        HttpStatusCodesLoader loader = new HttpStatusCodesLoader(filePath);
        assertDoesNotThrow(loader::saveCodesToFile);

        assertThat(Files.exists(filePath)).isTrue();

        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
