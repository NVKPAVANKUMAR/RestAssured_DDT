package com.restassured.requests.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostActivityRequest {
    int Id;
    String activity;
    String timeStamp;
    boolean completed;
}