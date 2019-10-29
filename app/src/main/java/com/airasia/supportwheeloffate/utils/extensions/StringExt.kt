package com.airasia.supportwheeloffate.utils.extensions

fun String?.isNotEmptyorNull(): Boolean {
    return this?.isNullOrEmpty() == false
}