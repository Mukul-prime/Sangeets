package com.example.SanGeets.Exceptions;

public class SongalreadyExistinPlaylist extends RuntimeException {
  public SongalreadyExistinPlaylist(String message) {
    super(message);
  }
}
