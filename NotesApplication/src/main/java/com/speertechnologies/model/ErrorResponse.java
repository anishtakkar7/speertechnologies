package com.speertechnologies.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
///@Data
public record ErrorResponse(String errorCode, String errorMessage) {

}
