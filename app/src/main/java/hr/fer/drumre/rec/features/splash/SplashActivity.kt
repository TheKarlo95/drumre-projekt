package hr.fer.drumre.rec.features.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import hr.fer.drumre.rec.MainActivity
import hr.fer.drumre.rec.core.utils.UserPreferencesImpl
import hr.fer.drumre.rec.features.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            val userPreferences = UserPreferencesImpl(applicationContext)
            if(true) {
                MainActivity.startWith(this)
            } else {
                LoginActivity.startWith(this)
            }
        }, DELAY)
    }

    companion object {
        private const val DELAY = 250L
    }
}
