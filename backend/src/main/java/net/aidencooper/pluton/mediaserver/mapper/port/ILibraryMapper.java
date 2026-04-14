package net.aidencooper.pluton.mediaserver.mapper.port;

import net.aidencooper.pluton.mediaserver.domain.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.domain.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.domain.dto.LibraryCreateRequestDTO;
import net.aidencooper.pluton.mediaserver.domain.dto.LibraryDTO;
import net.aidencooper.pluton.mediaserver.domain.dto.LibraryUpdateRequestDTO;
import net.aidencooper.pluton.mediaserver.domain.entity.Library;

public interface ILibraryMapper {
    LibraryDTO toDTO(Library library);

    LibraryCreateRequest fromDTO(LibraryCreateRequestDTO dto);
    LibraryUpdateRequest fromDTO(LibraryUpdateRequestDTO dto);
}
