package pl.rest.application.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Status {

    CREATED("CREATED"),
    VERIFIED("VERIFIED"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    PUBLISHED("PUBLISHED");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return this.description;
    }

    public static Status getValueFromString(String value) {
        try {
            return valueOf(value);
        } catch (Exception var2) {
            return Arrays.stream(values()).filter((pos) ->
                    pos.getDescription().equalsIgnoreCase(String.valueOf(value)))
                    .findAny().orElseGet(() -> values()[Integer.parseInt(value)]);
        }
    }
}
