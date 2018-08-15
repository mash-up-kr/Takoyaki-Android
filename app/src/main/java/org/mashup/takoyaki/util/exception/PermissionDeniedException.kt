package org.mashup.takoyaki.util.exception


/**
 * Created by jonghunlee on 2018-08-01.
 */
class PermissionDeniedException : RuntimeException() {
    override val message: String = "User doesn't accept permission"
}