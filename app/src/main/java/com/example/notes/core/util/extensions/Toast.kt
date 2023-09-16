package com.example.notes.core.util.extensions

import android.content.Context
import android.widget.Toast

fun toast(value: String, context: Context) {
    Toast.makeText(context, value, Toast.LENGTH_LONG).show()
}