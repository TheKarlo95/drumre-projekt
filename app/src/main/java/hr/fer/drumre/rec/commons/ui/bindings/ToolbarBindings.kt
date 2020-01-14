package hr.fer.drumre.rec.commons.ui.bindings

import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("shouldShowCloseIcon")
fun Toolbar.shouldShowCloseIcon(value: Boolean) {
    if (value) {
        navigationIcon = ContextCompat.getDrawable(
            context,
            android.R.drawable.ic_menu_close_clear_cancel
        )
    }
}
