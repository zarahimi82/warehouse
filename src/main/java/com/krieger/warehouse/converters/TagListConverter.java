package com.krieger.warehouse.converters;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class TagListConverter extends AbstractConverter<HashSet<String>, String> {

    @Override
    protected String convert(HashSet<String> tags) {

        return tags
                .stream()
                .collect(Collectors.joining(";"));
    }

}
