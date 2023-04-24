package net.cherrycave.birgid.utils


@MustBeDocumented
@Target(
    AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS,
    AnnotationTarget.TYPEALIAS
)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    message = "This is an internal api used by the library. Don't use it when you don't know what you're doing."
)
public annotation class Internal
