package edu.project3.input_arguments_parser;

import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record ParsedInput(String path, OffsetDateTime from, OffsetDateTime to, String format) {
}
