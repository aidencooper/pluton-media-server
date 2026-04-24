package net.aidencooper.pluton.mediaserver.library.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import net.aidencooper.pluton.mediaserver.library.domain.entity.LibraryType;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

public record LibraryCreateRequestDTO(
        @NotBlank(message = NAME_LENGTH_ERROR_MESSAGE)
        @Length(max = 32, message = NAME_LENGTH_ERROR_MESSAGE)
        String name,
        @NotNull(message = TYPE_ERROR_MESSAGE)
        LibraryType type,
        @NotEmpty(message = FOLDER_PATHS_SIZE_ERROR_MESSAGE)
        Set<String> folderPaths,
        boolean enabled
) {
    private static final String NAME_LENGTH_ERROR_MESSAGE = "Name must be between 1 and 32 characters";
    private static final String TYPE_ERROR_MESSAGE = "Library type must be provided";
    private static final String FOLDER_PATHS_SIZE_ERROR_MESSAGE = "There must be at least 1 folder path";
}
