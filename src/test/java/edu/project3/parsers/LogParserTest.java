package edu.project3.parsers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import edu.project3.logs.NginxLogParser;
import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogParserTest {
    @ParameterizedTest
    @MethodSource("getCorrectLogsList")
    @DisplayName("parsing correct logs")
    public void parseLogsTest_WhenGivenCorrectLogs_shouldReturnParsedLogs(
        List<String> logs,
        List<NginxLogRecord> parsedLogs
    ) {
        NginxLogParser parser = new NginxLogParser();
        assertThat(parser.parseLogs(logs)).isEqualTo(parsedLogs);
    }

    @ParameterizedTest
    @MethodSource("getInvalidLogs")
    @DisplayName("Parsing invalid Logs")
    public void parseLogs_whenGivenInvalidLog_ShouldThrowIllegalArgumentException(String log) {
        NginxLogParser parser = new NginxLogParser();
        assertThrows(
            IllegalArgumentException.class,
            () -> parser.parseLog(log)
        );
    }

    private static Stream<Arguments> getInvalidLogs() {
        String invalidOrder
            =
            "\"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" 93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";
        String invalidRequest
            = "217.168.17.5 - - [17/May/2015:08:05:12 +0000] \"GOTO ivanZolo2004\" 200 3316 \"-\" \"-\"";
        return Stream.of(
            Arguments.of(invalidOrder),
            Arguments.of(invalidRequest)
        );
    }

    private static Stream<Arguments> getCorrectLogsList() {
        List<String> logs = List.of(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"");
        DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("dd/LLL/yyyy:HH:mm:ss ZZ", Locale.US);

        NginxLogRecord parsedLog = NginxLogRecord.builder()
            .address("93.180.71.3")
            .user("-")
            .localTime(OffsetDateTime.parse("17/May/2015:08:05:32 +0000", formatter))
            .request(LogRequest.builder()
                .url("/downloads/product_1")
                .agent("Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)")
                .method("GET")
                .protocol("HTTP/1.1")
                .build())
            .response(LogResponse.builder()
                .status(304)
                .bytesSent(0)
                .build())
            .referer("-")
            .build();

        return Stream.of(
            Arguments.of(logs, List.of(parsedLog))
        );
    }
}
