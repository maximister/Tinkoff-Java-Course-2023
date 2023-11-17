package edu.project3.logs;

import java.util.List;
import java.util.regex.Pattern;

//TODO: протестировать и закончить класс
// добавить время, рекорды для хрнения семевых штук и тд, паттерны для них
public class NginxLogParser {
    /*$remote_addr – IP с которого был сделан запрос
    $remote_user – Пользователь, аутентифицированный через HTTP аутентификацию, обычно пустое
    [$time_local] – Время посещения в часовом поясе сервера
    “$request” - Тип HTTP-запроса + запрошенный путь без аргументов + версия HTTP
    $status - код ответа от сервера
    $body_bytes_sent - размер ответа сервера в байтах
    “$http_referer” - реферал (если есть)
    “$http_user_agent” - юзер-агент*/

    private final static Pattern NGINX_LOG_PATTERN = Pattern
        .compile("^(<remoteAddr>[^ \\d.]*) - (<remoteUser>[^ ]*) "
            + "\\[(<timeLocal>[^\\]]*)\\] \"(<request>[^\"]*)\" "
            + "(<status>[^ ]*) (<bodyBytesSent>[^ ]*) "
            + "\"(<httpReferer>[^\"]*)\" \"(<httpUserAgent>[^\"]*)\"$");

    public NginxLogParser() {
    }

    public List<NginxLogRecord> parseLogs(List<String> logs) {
        return logs.stream().map(this::parseLog).toList();
    }

    private NginxLogRecord parseLog(String log) {
        //....
        return  null;
    }
}
