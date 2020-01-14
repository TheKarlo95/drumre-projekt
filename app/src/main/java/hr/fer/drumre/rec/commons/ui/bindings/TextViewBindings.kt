package hr.fer.drumre.rec.commons.ui.bindings

import android.text.format.DateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.facebook.FacebookSdk.getApplicationContext
import hr.fer.drumre.rec.R
import java.util.Date

@BindingAdapter("ratingText")
fun TextView.ratingText(rating: Double) {
    text = context.getString(R.string.rating_format, rating)
}

@BindingAdapter("dateText")
fun TextView.dateText(date: Date?) {
    if (date != null) {
        val dateFormat = DateFormat.getDateFormat(getApplicationContext())
        text = dateFormat.format(date)
    }
}

