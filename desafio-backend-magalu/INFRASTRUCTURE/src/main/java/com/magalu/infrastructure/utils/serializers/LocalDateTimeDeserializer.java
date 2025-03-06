package com.magalu.infrastructure.utils.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.magalu.domain.validation.Error;
import com.magalu.infrastructure.exceptions.FormatException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final Map<Pattern, DateTimeFormatter> FORMATTERS = new LinkedHashMap<>();
    static {
        FORMATTERS.put(
                Pattern.compile("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}$"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        FORMATTERS.put(
                Pattern.compile("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws FormatException {

        List<Error> errors = new ArrayList<>();
        String dateString = "";

        try {
            dateString = p.getText().trim();

            for(final Map.Entry<Pattern, DateTimeFormatter> formatterMap : FORMATTERS.entrySet()){
                final Pattern regexFormat = formatterMap.getKey();
                if(!regexFormat.matcher(dateString).matches()){
                    continue;
                }

                DateTimeFormatter formatter = formatterMap.getValue();
                return LocalDateTime.parse(dateString, formatter);
            }

        }catch (IOException e){
            errors.add(new Error("IOException", e.getMessage()));
            throw new FormatException("IOException", errors);

        }catch (Exception e){
            errors.add(new Error("Exception", e.getMessage()));
            throw new FormatException("Exception", errors);
        }

        errors.add(new Error("scheduledTime field is wrong", "Formato deve ser dd/MM/yyyy HH:mm[:ss]"));
        throw new FormatException("InvalidFormatException", errors);
    }
}
