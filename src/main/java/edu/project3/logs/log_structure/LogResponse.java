package edu.project3.logs.log_structure;

import lombok.Builder;

/*  $status - код ответа от сервера
    $body_bytes_sent - размер ответа сервера в байтах*/

@Builder
public record LogResponse(int status, int bytesSent) {
}
