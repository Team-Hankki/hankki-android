package com.hankki.core.common.utill

import java.util.regex.Pattern

private const val KOREAN_NUMBER_ENGLISH_SPECIAL_UNDER20_PATTERN =
    "^\$|^[0-9a-zA-Zㄱ-ㅣ가-힣/[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\$%&\\\\\\=\\(\\'\\\"]/g\\u318D\\u119E\\u11A2\\u2022\\u2025\\u00B7\\uFE55]{0,20}+$"
val KOREAN_NUMBER_ENGLISH_SPECIAL_UNDER20_REGEX: Pattern =
    Pattern.compile(KOREAN_NUMBER_ENGLISH_SPECIAL_UNDER20_PATTERN)

private const val KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER20_PATTERN =
    "^\$|^[0-9a-zA-Zㄱ-ㅣ가-힣/[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\$%&\\\\\\=\\(\\'\\\"#\\s]/g\\u318D\\u119E\\u11A2\\u2022\\u2025\\u00B7\\uFE55]{0,20}+$"
val KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER20_REGEX: Pattern =
    Pattern.compile(KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER20_PATTERN)

private const val VALID_NUMBER_UNDER_100000_PATTERN = "^$|^[1-9][0-9]{0,4}$"
val VALID_NUMBER_UNDER_100000_REGEX: Pattern = Pattern.compile(VALID_NUMBER_UNDER_100000_PATTERN)
