package net.aidencooper.pluton.mediaserver.library.mapper;

import net.aidencooper.pluton.mediaserver.library.domain.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.dto.LibraryCreateRequestDTO;
import net.aidencooper.pluton.mediaserver.library.domain.dto.LibraryDTO;
import net.aidencooper.pluton.mediaserver.library.domain.dto.LibraryUpdateRequestDTO;
import net.aidencooper.pluton.mediaserver.library.domain.entity.Library;
import org.springframework.stereotype.Component;

@Component
public class LibraryMapper {
    public LibraryDTO toDTO(Library library) {
        return new LibraryDTO(
                library.getId(),
                library.getName(),
                library.getType(),
                library.getFolderPaths(),
                library.isEnabled()
        );
    }

    public LibraryCreateRequest fromDTO(LibraryCreateRequestDTO dto) {
        return new LibraryCreateRequest(
                dto.name(),
                dto.type(),
                dto.folderPaths(),
                dto.enabled()
        );
    }

    public LibraryUpdateRequest fromDTO(LibraryUpdateRequestDTO dto) {
        return new LibraryUpdateRequest(
                dto.name(),
                dto.type(),
                dto.folderPaths(),
                dto.enabled()
        );
    }
}
