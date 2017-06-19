package ar.com.fufoinc.ric

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ChannelListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_list)

        val name = intent.getStringExtra(EXTRA_NAME)
                ?: throw IllegalStateException("field $EXTRA_NAME missing in Intent")

        
    }

    companion object {
        private val EXTRA_NAME = "EXTRA_NAME"

        fun newIntent(context: Context, name: String): Intent {
            val intent = Intent(context, ChannelListActivity::class.java)
            intent.putExtra(EXTRA_NAME, name)
            return intent
        }
    }
}
