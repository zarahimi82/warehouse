package com.krieger.warehouse.converters;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TagStringConverter extends AbstractConverter<String, HashSet<String>> {

    @Override
    protected HashSet<String> convert(String tags) {
        Set<String> hashSet = new HashSet<>();

        if (tags != null && !tags.isEmpty()) {
            String[] substrings = tags.split(";");
            for (String substring : substrings) {
                hashSet.add(substring);
            }
        }
        return (HashSet<String>) hashSet;
    }
}
