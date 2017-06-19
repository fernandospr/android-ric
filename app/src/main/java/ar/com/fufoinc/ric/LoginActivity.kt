package ar.com.fufoinc.ric

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener { login() }
    }

    private fun login() {
        val name = nameField.text.toString()
        if (name.isNotBlank()) {
            FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    Log.w(LOG_TAG, task.exception.toString())
                } else {
                    startActivity(ChannelListActivity.newIntent(this, name))
                }
            }
        }
    }

    companion object {
        private val LOG_TAG = "LoginActivity"
    }
}
