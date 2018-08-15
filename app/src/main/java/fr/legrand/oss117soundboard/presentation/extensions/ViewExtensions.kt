package fr.legrand.oss117soundboard.presentation.extensions

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView

fun ImageView.setToGrayScale() {
    val matrix = ColorMatrix()
    matrix.setSaturation(0f)
    val cf = ColorMatrixColorFilter(matrix)
    colorFilter = cf
    imageAlpha = 128
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}