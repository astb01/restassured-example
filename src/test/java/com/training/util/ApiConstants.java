package com.training.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiConstants {
  /**
   * Base URI for the all services.
   */
  public static final String BASE_URI = "http://jsonplaceholder.typicode.com";

  /**
   * Base path for the Posts Service.
   */
  public static final String POSTS_SERVICE_PATH = "/posts";

  /**
   * Base path for the Albums Service.
   */
  public static final String ALBUMS_SERVICE_PATH = "/albums";

  /**
   * Base path for the Comments Service.
   */
  public static final String COMMENTS_SERVICE_PATH = "/comments";

  /**
   * Base path for the Photos Service.
   */
  public static final String PHOTOS_SERVICE_PATH = "/photos";
}
