package com.arishi.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * UpdateStudentRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-30T16:32:04.250040159+05:30[Asia/Kolkata]", comments = "Generator version: 7.13.0")
public class UpdateStudentRequest {

  private String name;

  private String email;

  private String mobileNo;

  public UpdateStudentRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UpdateStudentRequest(String name, String email, String mobileNo) {
    this.name = name;
    this.email = email;
    this.mobileNo = mobileNo;
  }

  public UpdateStudentRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Updated full name.
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "Rohit Kumar Sharma", description = "Updated full name.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UpdateStudentRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Updated email address. Must be unique.
   * @return email
   */
  @NotNull @javax.validation.constraints.Email 
  @Schema(name = "email", example = "rohit.new@example.com", description = "Updated email address. Must be unique.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UpdateStudentRequest mobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
    return this;
  }

  /**
   * Updated 10-digit mobile number.
   * @return mobileNo
   */
  @NotNull @Pattern(regexp = "^\\d{10}$") 
  @Schema(name = "mobileNo", example = "9123456780", description = "Updated 10-digit mobile number.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mobileNo")
  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateStudentRequest updateStudentRequest = (UpdateStudentRequest) o;
    return Objects.equals(this.name, updateStudentRequest.name) &&
        Objects.equals(this.email, updateStudentRequest.email) &&
        Objects.equals(this.mobileNo, updateStudentRequest.mobileNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, email, mobileNo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateStudentRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    mobileNo: ").append(toIndentedString(mobileNo)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

