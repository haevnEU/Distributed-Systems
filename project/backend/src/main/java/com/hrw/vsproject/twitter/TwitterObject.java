package com.hrw.vsproject.twitter;

import lombok.Data;

/**
 * This container holds an abstract datastructure.<br>
 * This is used by the {@link com.hrw.vsproject.twitter.Twitter#getDataFromTwitter}
 * @param <T> Abstract data
 */
@Data
public class TwitterObject <T>{
    T data;
}
