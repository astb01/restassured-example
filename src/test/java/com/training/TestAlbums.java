package com.training;

import com.training.models.Albums;
import com.training.util.ApiConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class TestAlbums {

  @Before
  public void setUp() {
    RestAssured.baseURI = ApiConstants.BASE_URI;
  }

  @Test
  public void givenAlbumsWhenGetAllThenCorrectResponseReturned() {
    RestAssured
          .given()
          .log().all()
          .contentType(ContentType.JSON)
          .when()
          .get(ApiConstants.ALBUMS_SERVICE_PATH)
          .then()
          .log().all()
          .statusCode(HttpStatus.SC_OK)
          .body("$", Matchers.hasSize(Matchers.greaterThan(0)));
  }

  @Test
  public void givenAlbumsWhenCreateAlbumThenAlbumCreated() {
    Albums newAlbum = Albums.builder()
        .userId(2)
        .title("Rolling Stones").build();

    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .body(newAlbum)
        .when()
        .post(ApiConstants.ALBUMS_SERVICE_PATH)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_CREATED)
        .body("id", Matchers.notNullValue())
        .body("title", Matchers.equalTo(newAlbum.getTitle()));
  }

  @Test
  public void givenAlbumsWhenGetSingleAlbumByIdThenAlbumReturned() {
    Integer albumId = 1;

    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .when()
        .get(ApiConstants.ALBUMS_SERVICE_PATH + "/{id}", albumId)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.equalTo(albumId));
  }

  @Test
  public void givenAlbumsWhenUpdateThenAlbumIsUpdated() {
    Albums updateAlbumRequest = Albums.builder().id(1).title("New album title").build();

    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .body(updateAlbumRequest)
        .when()
        .put(ApiConstants.ALBUMS_SERVICE_PATH + "/{id}", updateAlbumRequest.getId())
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.equalTo(updateAlbumRequest.getId()))
        .body("title", Matchers.equalTo(updateAlbumRequest.getTitle()))
        .body("userId", Matchers.equalTo(updateAlbumRequest.getUserId()));

  }

  @Test
  public void givenAlbumsWhenDeleteThenAlbumIsRemoved() {
    Integer albumId = 1;

    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .when()
        .delete(ApiConstants.ALBUMS_SERVICE_PATH + "/{id}", albumId)
        .then()
        .statusCode(HttpStatus.SC_OK);
  }
}
