package com.ashwin_sudhakar.mycryptoapplication.ui.customViews

import android.content.Context
import android.graphics.Typeface
import com.ashwin_sudhakar.mycryptoapplication.R
import com.marcoscg.dialogsheet.DialogSheet


class MyDialogSheet(context: Context) : DialogSheet(context) {
    init {
        setTitleTypeface(Typeface.createFromAsset(context.assets, "fonts/font_bold.ttf"))
        setMessageTypeface(Typeface.createFromAsset(context.assets, "fonts/font_regular.ttf"))
        setButtonsTypeface(Typeface.createFromAsset(context.assets, "fonts/font_medium.ttf"))
        setButtonsColorRes(R.color.darkGray)
        setRoundedCorners(true)
        setColoredNavigationBar(true)
    }
}