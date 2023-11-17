package edu.project3.logs.log_structure;

import java.time.OffsetDateTime;
import lombok.Builder;

//Надеюсь, использование бомбока не осуждается

@Builder
public record NginxLogRecord(
    String address,
    String user,
    OffsetDateTime localTime,
    String referer,
    LogRequest request,
    LogResponse response) {
}
