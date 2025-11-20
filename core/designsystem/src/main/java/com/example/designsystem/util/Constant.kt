package com.example.designsystem.util

import kotlinx.collections.immutable.persistentListOf

// TextField
object TextFieldConstant {
    const val MIN_NICKNAME_LENGTH = 2
    const val MAX_NICKNAME_LENGTH = 10

    const val MAX_COMMENT_LENGTH = 150
}

// BottomSheet
object DPlayReportReasons {
    const val INAPPROPRIATE_CONTENT = "부적절한 내용을 포함하고 있어요."
    const val OFFENSIVE_EXPRESSION = "불쾌한 표현이 포함되어 있어요."
    const val SUSPICIOUS_OR_SPAM = "의심스럽거나 스팸이에요."
    const val COPYRIGHT_VIOLATION = "저작권을 침해하고 있어요."

    val all =
        persistentListOf(
            INAPPROPRIATE_CONTENT,
            OFFENSIVE_EXPRESSION,
            SUSPICIOUS_OR_SPAM,
            COPYRIGHT_VIOLATION,
        )
}
