package com.training.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Comments {
  private Integer postId;
  private Integer id;
  private String name;
  private String email;
  private String body;
}
