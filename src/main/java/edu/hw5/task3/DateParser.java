package edu.hw5.task3;

import edu.hw5.task3.date_parsers.AbstractDateParser;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DateParser {
    private List<AbstractDateParser> dateParsers;

    public DateParser() {
    }

    public void setDateParsers(List<AbstractDateParser> dateParsers) {
        this.dateParsers = dateParsers;
        if (dateParsers != null && !dateParsers.isEmpty()) {
            makeChain();
        }
    }

    public void addDateParser(AbstractDateParser parser) {
        if (parser != null) {
            if (dateParsers == null) {
                dateParsers = new ArrayList<>();
            }
            dateParsers.add(parser);
            makeChain();
        }
    }

    private void makeChain() {
        //
        for (int i = 0; i < dateParsers.size() - 1; i++) {
            dateParsers.get(i).setNextParser(dateParsers.get(i + 1));
        }
    }

    public Optional<LocalDate> parseDate(String date) {
        if (dateParsers != null && !dateParsers.isEmpty()) {
            return dateParsers.get(0).parseDate(date.strip());
        }

        return Optional.empty();
    }
}
