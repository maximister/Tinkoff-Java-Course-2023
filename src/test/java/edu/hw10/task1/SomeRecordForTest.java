package edu.hw10.task1;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;

public record SomeRecordForTest(@Max(12L) @Min(8) long value, @Max(5) @Min(4) double doubleValue,
                                @Max(24) byte byteValue, @Min(5) short shortValue, @Max(12) float floatValue) {
}
