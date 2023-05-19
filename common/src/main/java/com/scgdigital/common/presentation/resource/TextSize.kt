package com.scgdigital.common.presentation.resource

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class TextSize(val value: TextUnit) {
    LINE(0.5.sp),
    XXXS(0.sp),
    XXS(2.sp),
    XS(4.sp),
    S(8.sp),
    M(12.sp),
    L(16.sp),
    XL(20.sp),
    XXL(24.sp),
    XXXL(32.sp),
    XXXXL(64.sp)
}
