package net.aidencooper.pluton.mediaserver.media.domain.model;

public record Title(String value) {
    private static String normalize(String value) {
        return value
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", " ");
    }

    public static Title of(String value) {
        return new Title(value);
    }

    public Title {
        if(value == null || value.isBlank()) throw new IllegalArgumentException("Title cannot be null/blank");
        value = normalize(value);
    }
}
