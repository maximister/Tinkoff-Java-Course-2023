package edu.project3.logs.log_structure;

import lombok.Builder;

/*  “$request” - Тип HTTP-запроса + запрошенный путь без аргументов + версия HTTP
    “$http_user_agent” - юзер-агент*/

@Builder
public record LogRequest(
    String method,
    String url,
    String protocol,
    String agent) {
}
