package net.aidencooper.pluton.mediaserver.mapper.adapter;

import net.aidencooper.pluton.mediaserver.domain.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.domain.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.domain.dto.LibraryCreateRequestDTO;
import net.aidencooper.pluton.mediaserver.domain.dto.LibraryDTO;
import net.aidencooper.pluton.mediaserver.domain.dto.LibraryUpdateRequestDTO;
import net.aidencooper.pluton.mediaserver.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.mapper.port.ILibraryMapper;
import org.springframework.stereotype.Component;

@Component
public class LibraryMapper implements ILibraryMapper {
    @Override
    public LibraryDTO toDTO(Library library) {
        return new LibraryDTO(
                library.getId(),
                library.getName(),
                library.getType(),
                library.getFolderPaths(),
                library.isEnabled()
        );
    }

    @Override
    public LibraryCreateRequest fromDTO(LibraryCreateRequestDTO dto) {
        return new LibraryCreateRequest(
                dto.name(),
                dto.type(),
                dto.folderPaths(),
                dto.enabled()
        );
    }

    @Override
    public LibraryUpdateRequest fromDTO(LibraryUpdateRequestDTO dto) {
        return new LibraryUpdateRequest(
                dto.name(),
                dto.type(),
                dto.folderPaths(),
                dto.enabled()
        );
    }
}
