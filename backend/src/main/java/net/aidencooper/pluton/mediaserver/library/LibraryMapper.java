package net.aidencooper.pluton.mediaserver.library;

import net.aidencooper.pluton.mediaserver.library.domain.Library;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryDTO;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryUpdateRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LibraryMapper {
    LibraryDTO toDTO(Library library);

    Library toEntity(LibraryDTO libraryDTO);
    Library toEntity(LibraryCreateRequest request);
    Library toEntity(LibraryUpdateRequest request);
}
