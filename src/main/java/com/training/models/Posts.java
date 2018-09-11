package com.training.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Posts {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
