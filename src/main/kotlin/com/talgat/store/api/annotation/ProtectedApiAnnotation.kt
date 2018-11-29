package com.talgat.store.api.annotation

import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
//@Repeatable
@RequestMapping("api/protected")
@RestController
@Secured("ROLE_ADMIN")
annotation class ProtectedApiAnnotation
