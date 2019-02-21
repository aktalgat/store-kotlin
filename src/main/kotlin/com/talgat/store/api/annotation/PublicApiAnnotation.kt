package com.talgat.store.api.annotation

import org.springframework.web.bind.annotation.RequestMapping

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RequestMapping("api/public")
annotation class PublicApiAnnotation