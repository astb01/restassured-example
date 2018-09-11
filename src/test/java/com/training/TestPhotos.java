package com.training;

import com.training.models.Photos;
import com.training.util.ApiConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class TestPhotos {

  @Before
  public void setUp() {
    RestAssured.baseURI = ApiConstants.BASE_URI;
  }

  @Test
  public void givenPhotosWhenGetAllPhotosThenAllReturned() {
    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .when()
        .get(ApiConstants.PHOTOS_SERVICE_PATH)
        .then()
        .log().all()
        .statusCode(HttpStatus.SC_OK)
        .body("$", Matchers.hasSize(Matchers.greaterThan(0)))
        .body("id", Matchers.hasItems(1, 2, 6));
  }

  @Test
  public void givenPhotosWhenCreatePhotosThenPhotoCreated() {
    Photos newPhoto = Photos.builder()
        .thumbnailUrl("http://someurl.com")
        .title("My photo")
        .albumId(1)
        .url("http://someurl.com")
        .build();

    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .body(newPhoto)
        .when()
        .post(ApiConstants.PHOTOS_SERVICE_PATH)
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .body("id", Matchers.notNullValue())
        .body("albumId", Matchers.equalTo(newPhoto.getAlbumId()))
        .body("title", Matchers.equalTo(newPhoto.getTitle()))
        .body("url", Matchers.equalTo(newPhoto.getUrl()))
        .body("thumbnailUrl", Matchers.equalTo(newPhoto.getThumbnailUrl()));
  }

  @Test
  public void givenPhotosWhenGetPhotoByIdThenPhotoReturned() {
    Integer photoId = 1;

    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .when()
        .get(ApiConstants.PHOTOS_SERVICE_PATH + "/{id}", photoId)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.equalTo(photoId));
  }

  @Test
  public void givenPhotosWhenUpdateThenPhotoUpdated() {
    Photos updateRequest = Photos.builder().albumId(1).url("http://updated.com").thumbnailUrl("http://thumb.com")
        .title("new title").id(1).build();

    RestAssured
        .given()
        .log().all()
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put(ApiConstants.PHOTOS_SERVICE_PATH + "/{id}", updateRequest.getId())
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("title", Matchers.equalTo(updateRequest.getTitle()))
        .body("url", Matchers.equalTo(updateRequest.getUrl()))
        .body("thumbnailUrl", Matchers.equalTo(updateRequest.getThumbnailUrl()))
        .body("id", Matchers.equalTo(updateRequest.getId()));
  }
}
