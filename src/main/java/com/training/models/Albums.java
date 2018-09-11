package com.training.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Albums {
  private Integer userId;
  private Integer id;
  private String title;
}
