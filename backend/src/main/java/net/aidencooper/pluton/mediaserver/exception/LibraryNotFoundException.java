package net.aidencooper.pluton.mediaserver.exception;

import lombok.Getter;

@Getter
public class LibraryNotFoundException extends RuntimeException {
  private final Long id;

  public LibraryNotFoundException(Long id) {
    super("Library with ID '" + id + "' does not exist.");

    this.id = id;
  }
}
