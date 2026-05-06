package net.aidencooper.pluton.mediaserver.media.domain.model;

public record DisplayTitle(String value) {
    public static DisplayTitle of(String value) {
        return new DisplayTitle(value);
    }

    public DisplayTitle {
        if(value == null || value.isBlank()) throw new IllegalArgumentException("DisplayTitle cannot be null/blank");
        value = value.trim();
    }
}
