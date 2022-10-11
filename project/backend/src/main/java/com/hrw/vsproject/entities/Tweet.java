package com.hrw.vsproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Representation of a Twitter Tweet.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"created_at"})
public class Tweet {
    private Date created_at;
    private String text;
    private String id;
    private String author_id;
}
