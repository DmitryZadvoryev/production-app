package ru.zadvoryev.productionapp.util;

import ru.zadvoryev.productionapp.data.Record;

import java.util.function.Function;

public class ReportForTimeConverter extends Converter<ReportForTimeConverter, Record> {
    public ReportForTimeConverter(Function<ReportForTimeConverter, Record> fromDto, Function<Record, ReportForTimeConverter> fromEntity) {
        super(fromDto, fromEntity);
    }
}
