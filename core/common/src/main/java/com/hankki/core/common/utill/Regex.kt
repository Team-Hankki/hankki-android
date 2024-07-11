package com.hankki.core.common.utill

import java.util.regex.Pattern

private const val KOREAN_NUMBER_ENGLISH_UNDER20_PATTERN =
    "^\$|^[0-9a-zA-Zㄱ-ㅣ가-힣\\u318D\\u119E\\u11A2\\u2022\\u2025\\u00B7\\uFE55]{0,20}+$"
val KOREAN_NUMBER_ENGLISH_UNDER20_REGEX: Pattern =
    Pattern.compile(KOREAN_NUMBER_ENGLISH_UNDER20_PATTERN)

private const val NUMBER_UNDER5_PATTERN = "^$|^[0-9]{0,5}+$"
val NUMBER_UNDER5_REGEX: Pattern = Pattern.compile(NUMBER_UNDER5_PATTERN)
