package com.talgat.store.api.payload

import org.springframework.http.HttpStatus

abstract class AbstractApiResponse {
    val code = 200
    val status = HttpStatus.OK
}
