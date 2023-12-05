package com.faradhy.storyusersapp.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import com.faradhy.storyusersapp.R

class EmailCustom : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        doOnTextChanged { text, _, _, _ ->
            validateInput(text)
        }
    }

    private fun validateInput(input: CharSequence?) {
        error = when {
            input.isNullOrEmpty() -> context.getString(R.string.if_email_empty)
            !android.util.Patterns.EMAIL_ADDRESS.matcher(input)
                .matches() -> context.getString(R.string.if_email_invalid)

            else -> null
        }
    }
}