package com.amazon.ionelement.api

import com.amazon.ion.IonType

/**
 * Indicates the type of the Ion value represented by an instance of [IonElement].
 *
 * [ElementType] has all the same members as `ion-java`'s [IonType] except for [IonType.DATAGRAM] because [IonElement]
 * has no notion of datagrams.  It also exposes [isText], [isContainer] and [isLob] as properties instead of as static
 * functions.
 */
enum class ElementType(
    /** True if the current [ElementType] is [STRING] or [SYMBOL]. */
    val isText: Boolean,

    /**
     * True if the current [ElementType] is [LIST] or [SEXP].
     *
     * TODO: during the implementation of:
     * - https://github.com/amzn/ion-element-kotlin/issues/11
     * - https://github.com/amzn/ion-element-kotlin/issues/12
     * The term "container" may change.
     */
    val isContainer: Boolean,

    /** True if the current [ElementType] is [CLOB] or [BLOB]. */
    val isLob: Boolean
) {
    // Other types
    NULL(false, false, false),
    BOOL(false, false, false),
    INT(false, false, false),
    FLOAT(false, false, false),
    DECIMAL(false, false, false),
    TIMESTAMP(false, false, false),

    // String-valued types
    SYMBOL(true, false, false),
    STRING(true, false, false),

    // Binary-valued types
    CLOB(false, false, true),
    BLOB(false, false, true),

    // Container types
    LIST(false, true, false),
    SEXP(false, true, false),

    // TODO: with https://github.com/amzn/ion-element-kotlin/issues/12 it may be appropriate to consider structs to be containers.
    STRUCT(false, false, false);

    /** Converts this [ElementType] to [IonType]. */
    fun toIonType() = when(this) {
        NULL -> IonType.NULL
        BOOL -> IonType.BOOL
        INT -> IonType.INT
        FLOAT -> IonType.FLOAT
        DECIMAL -> IonType.DECIMAL
        TIMESTAMP -> IonType.TIMESTAMP
        SYMBOL -> IonType.SYMBOL
        STRING -> IonType.STRING
        CLOB -> IonType.CLOB
        BLOB -> IonType.BLOB
        LIST -> IonType.LIST
        SEXP -> IonType.SEXP
        STRUCT -> IonType.STRUCT
    }
}

/**
 * Converts the receiver [IonType] to [ElementType].
 *
 * @throws [IllegalStateException] if the receiver is [IonType.DATAGRAM] because [IonElement] has no notion of
 * datagrams.
 */
fun IonType.toElementType() = when(this) {
    IonType.NULL -> ElementType.NULL
    IonType.BOOL -> ElementType.BOOL
    IonType.INT -> ElementType.INT
    IonType.FLOAT -> ElementType.FLOAT
    IonType.DECIMAL -> ElementType.DECIMAL
    IonType.TIMESTAMP -> ElementType.TIMESTAMP
    IonType.SYMBOL -> ElementType.SYMBOL
    IonType.STRING -> ElementType.STRING
    IonType.CLOB -> ElementType.CLOB
    IonType.BLOB -> ElementType.BLOB
    IonType.LIST -> ElementType.LIST
    IonType.SEXP -> ElementType.SEXP
    IonType.STRUCT -> ElementType.STRUCT
    IonType.DATAGRAM -> error("IonType.DATAGRAM has no ElementType equivalent")
}