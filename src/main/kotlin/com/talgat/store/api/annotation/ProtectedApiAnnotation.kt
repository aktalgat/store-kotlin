package com.talgat.store.api.annotation

import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.RequestMapping

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RequestMapping("api/protected")
@Secured("ROLE_ADMIN")
annotation class ProtectedApiAnnotation
