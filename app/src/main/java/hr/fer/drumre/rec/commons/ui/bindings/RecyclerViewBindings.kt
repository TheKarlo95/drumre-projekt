package hr.fer.drumre.rec.commons.ui.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.fer.drumre.rec.commons.ui.recyclerview.RecyclerViewItemDecoration

/**
 * Add an [RecyclerViewItemDecoration] to this RecyclerView. Item decorations can
 * affect both measurement and drawing of individual item views.
 *
 * @param spacingPx Spacing in pixels to set as a item margin.
 * @see RecyclerView.addItemDecoration
 */
@BindingAdapter("itemDecorationSpacing")
fun RecyclerView.setItemDecorationSpacing(spacingPx: Float) {
    addItemDecoration(
        RecyclerViewItemDecoration(spacingPx.toInt())
    )
}
