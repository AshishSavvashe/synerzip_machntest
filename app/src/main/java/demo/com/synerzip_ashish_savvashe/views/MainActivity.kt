package demo.com.synerzip_ashish_savvashe.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import demo.com.synerzip_ashish_savvashe.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        //Check for instance and then add main fragment
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, HomeFragment.newInstance())
                .commit()
        }
    }

    fun setToolBarTitle(title: String) {
        //To set toolbar title from the fragment classes
        supportActionBar?.let {
            it.title = title
        }
    }
}

