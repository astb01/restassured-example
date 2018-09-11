package com.training;

import com.training.models.Posts;
import com.training.util.ApiConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class TestPosts {

  @Before
  public void setUp() {
    RestAssured.baseURI = ApiConstants.BASE_URI;
  }

  @Test
  public void givenPostsWhenGetAllThenAllPostsReturned() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .log().all()
        .when()
        .get(ApiConstants.POSTS_SERVICE_PATH)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK)
        .body("$", Matchers.hasSize(Matchers.greaterThan(0)))
        .body("[0].id", Matchers.equalTo(1));
  }

  @Test
  public void givenPostsWhenCreatingPostThenCorrectPostReturned() {

    Posts newPost = Posts.builder()
        .userId(1)
        .title("My new title")
        .body("This is a test")
        .build();

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .log().all()
        .body(newPost)
        .when()
        .post(ApiConstants.POSTS_SERVICE_PATH)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_CREATED)
        .body("id", Matchers.notNullValue())
        .body("title", Matchers.equalTo(newPost.getTitle()))
        .body("body", Matchers.equalTo(newPost.getBody()))
        .body("userId", Matchers.equalTo(newPost.getUserId()));
  }

  @Test
  public void givenPostsWhenGetOnePostThenCorrectPostReturned() {
    Integer postId = 1;

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .log().all()
        .when()
        .get(ApiConstants.POSTS_SERVICE_PATH + "/{id}", postId)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.equalTo(postId));
  }

  @Test
  public void givenPostsWhenUpdatePostThenPostIsUpdated() {
    Posts updatePostRequest = Posts.builder()
        .id(1)
        .title("My updated title")
        .build();

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .log().all()
        .body(updatePostRequest)
        .when()
        .put(ApiConstants.POSTS_SERVICE_PATH + "/{id}", updatePostRequest.getId())
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.equalTo(updatePostRequest.getId()))
        .body("title", Matchers.equalTo(updatePostRequest.getTitle()));
  }

  @Test
  public void givenPostsWhenDeleteThenCorrectPostDeleted() {
    Integer postId = 1;

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .log().all()
        .when()
        .delete(ApiConstants.POSTS_SERVICE_PATH + "/{id}", postId)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  public void givenPostsWhenRequestMadeThenResponseReceivedWithinAllowedTime() {
    Long time = 2000L;

    RestAssured.given()
        .contentType(ContentType.JSON)
        .log().all()
        .when()
        .get(ApiConstants.POSTS_SERVICE_PATH)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK)
        .time(Matchers.lessThan(time), TimeUnit.MILLISECONDS);
  }
}
