package com.talgat.store.api.annotation

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
//@Inherited
@RequestMapping("api/public")
@RestController
annotation class PublicApiAnnotation