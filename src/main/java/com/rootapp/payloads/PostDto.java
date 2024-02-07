package com.rootapp.payloads;

import java.util.Date;

import com.rootapp.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private long id;
    private String title;
    private String content;
    private String imageName;
    private Date date;
    private CategoryDto category;
    private User user;

}
