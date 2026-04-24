package net.aidencooper.pluton.mediaserver.library.mapper.port;

import net.aidencooper.pluton.mediaserver.library.domain.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.dto.LibraryCreateRequestDTO;
import net.aidencooper.pluton.mediaserver.library.domain.dto.LibraryDTO;
import net.aidencooper.pluton.mediaserver.library.domain.dto.LibraryUpdateRequestDTO;
import net.aidencooper.pluton.mediaserver.library.domain.entity.Library;

public interface ILibraryMapper {
    LibraryDTO toDTO(Library library);

    LibraryCreateRequest fromDTO(LibraryCreateRequestDTO dto);
    LibraryUpdateRequest fromDTO(LibraryUpdateRequestDTO dto);
}
