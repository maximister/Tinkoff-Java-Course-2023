package edu.project3.logs;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        .compile("^(?<remoteAddr>.*) - (?<remoteUser>.*) \\[(?<timeLocal>.*)] "
            + "\"(?<request>.+)\" (?<status>.*) (?<bodyBytesSent>.*) \"(?<httpReferer>.*)\" "
            + "\"(?<httpUserAgent>.*)\"$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER
        = DateTimeFormatter.ofPattern("dd/LLL/yyyy:HH:mm:ss ZZ", Locale.US);

    public NginxLogParser() {
    }

    public List<NginxLogRecord> parseLogs(List<String> logs) {

        return logs.stream().map(this::parseLog).toList();
    }

    public NginxLogRecord parseLog(String log) {
        Matcher matcher = NGINX_LOG_PATTERN.matcher(log);
        if (!matcher.matches()) {
            throw new IllegalStateException("Invalid log format:\n" + log);
        }

        String address = matcher.group("remoteAddr");
        String user = matcher.group("remoteUser");
        String time = matcher.group("timeLocal");
        String request = matcher.group("request");
        String status = matcher.group("status");
        String bytesSent = matcher.group("bodyBytesSent");
        String referer = matcher.group("httpReferer");
        String httpAgent = matcher.group("httpUserAgent");

        return NginxLogRecord.builder()
            .address(address)
            .user(user)
            .localTime(OffsetDateTime.parse(time, DATE_TIME_FORMATTER))
            .referer(referer)
            .request(parseRequest(request, httpAgent))
            .response(parseResponse(status, bytesSent))
            .build();
    }

    private LogRequest parseRequest(String request, String agent) {
        Pattern requestPattern
            = Pattern.compile("(?<method>.*) (?<url>.*) (?<protocol>.*)");
        Matcher matcher = requestPattern.matcher(request);
        if (!matcher.matches()) {
            throw new IllegalStateException("Invalid request format:\n" + request);
        }

        return LogRequest.builder()
            .method(matcher.group("method"))
            .url(matcher.group("url"))
            .protocol(matcher.group("protocol"))
            .agent(agent)
            .build();
    }

    private LogResponse parseResponse(String status, String bytesSent) {
        return LogResponse.builder()
            .status(Integer.parseInt(status))
            .bytesSent(Integer.parseInt(bytesSent))
            .build();
    }
}
