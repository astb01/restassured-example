package com.training.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Photos {
  private Integer albumId;
  private Integer id;
  private String title;
  private String url;
  private String thumbnailUrl;
}
