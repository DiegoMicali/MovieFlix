package com.movieflix.request;

import lombok.Builder;

public record UserRequest(String name, String email, String password) {

}
