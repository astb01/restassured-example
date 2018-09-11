package com.training;

import com.training.models.Comments;
import com.training.util.ApiConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class TestComments {
  private String servicePath;

  /**
   * Check system properties if set, if not provide defaults.
   */
  @Before
  public void setUp() {
    String baseUri = System.getProperty("baseUri");

    RestAssured.baseURI = StringUtils.isEmpty(baseUri) ? ApiConstants.BASE_URI : baseUri;

    String servicePathProp = System.getProperty("servicePath");

    servicePath = StringUtils.isEmpty(servicePathProp)
        ? ApiConstants.COMMENTS_SERVICE_PATH : servicePathProp;

    String portStr = System.getProperty("port");

    if (StringUtils
        .isNotEmpty(portStr)) {
      RestAssured.port = Integer.parseInt(portStr);
    }
  }

  @Test
  public void givenCommentsWhenGetAllThenAllCommentsReturned() {
    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .log().all()
        .when()
        .get(servicePath)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK)
        .body("$", Matchers.hasSize(Matchers.greaterThan(0)))
        .body("id", Matchers.hasItems(1, 2, 3));
  }

  @Test
  public void givenCommentsWhenCreateThenCommentAdded() {
    Comments newComment = Comments.builder()
        .body("This is a test")
        .email("test@some.com")
        .name("Tester")
        .postId(Integer.MAX_VALUE)
        .build();

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .log().all()
        .body(newComment)
        .when()
        .post(servicePath)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_CREATED)
        .body("id", Matchers.notNullValue())
        .body("postId", Matchers.equalTo(newComment.getPostId()))
        .body("name", Matchers.equalTo(newComment.getName()))
        .body("email", Matchers.equalTo(newComment.getEmail()))
        .body("body", Matchers.equalTo(newComment.getBody()));
  }

  @Test
  public void givenCommentsWhenDeleteThenCommentRemoved() {
    Integer commentId = 1;

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .log().all()
        .when()
        .delete(servicePath + "/{id}", commentId)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  public void givenCommentsWhenGetOneCommentByIdThenCommentReturned() {
    Integer commentId = 2;

    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .when()
        .get(servicePath + "/{id}", commentId)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.equalTo(commentId))
        .body("email", Matchers.equalTo("Jayne_Kuhic@sydney.com"));
  }

  @Test
  public void givenCommentsWhenUpdateThenCommentUpdated() {
    Comments updateRequest = Comments.builder()
        .name("tester")
        .email("new@test.com")
        .body("update")
        .postId(1)
        .id(1)
        .build();

    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put(servicePath + "/{id}", updateRequest.getId())
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.equalTo(updateRequest.getId()))
        .body("email", Matchers.equalTo(updateRequest.getEmail()))
        .body("body", Matchers.equalTo(updateRequest.getBody()))
        .body("postId", Matchers.equalTo(updateRequest.getPostId()));
  }
}
