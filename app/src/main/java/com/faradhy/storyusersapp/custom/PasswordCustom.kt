package com.faradhy.storyusersapp.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import com.faradhy.storyusersapp.R

class PasswordCustom : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        doOnTextChanged { text, _, _, _ ->
            validateInput(text)
        }
    }

    private fun validateInput(p0: CharSequence?) {
        error = when {
            p0.isNullOrEmpty() -> context.getString(R.string.if_password_empty)
            p0.length < 8 -> context.getString(R.string.if_password_invalid)
            else -> null
        }
    }
}