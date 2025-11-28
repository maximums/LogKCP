@file:Suppress("Unused")

package com.maximums.annotations

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR
) annotation class LogKCP
