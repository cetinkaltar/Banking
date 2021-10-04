package com.bo.banking.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@ToString()
@AllArgsConstructor()
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class NotFoundException extends RuntimeException {
    @Getter
    private final String resource;
    @Getter
    private Map<String, Object> fieldToId;


    public NotFoundException(Class clazz, Object id) {
        this(clazz, "id", id);
    }

    public NotFoundException(Class clazz, String field, Object id) {
        this(clazz, Collections.singletonMap(field, id));
    }

    private NotFoundException(Class clazz, Map<String, Object> fieldToId) {
        this.resource = clazz.getSimpleName();
        this.fieldToId = fieldToId;
    }

    @Override
    public String getMessage() {
        String reference = fieldToId.entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.joining(", "));
        return resource + ":" + reference;
    }
}
