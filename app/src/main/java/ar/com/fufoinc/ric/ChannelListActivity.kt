package ar.com.fufoinc.ric

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_channel_list.*

class ChannelListActivity : AppCompatActivity() {

    val mChannelRef by lazy {
        FirebaseDatabase.getInstance().reference.child("channels")
    }

    var mEventListener: ValueEventListener? = null
    var mName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_list)

        mName = intent.getStringExtra(EXTRA_NAME)
                ?: throw IllegalStateException("field $EXTRA_NAME missing in Intent")

        createChannelButton.setOnClickListener { createChannel() }

        observeChannels()
    }

    private fun createChannel() {
        val name = channelName.text.toString()
        if (name.isNotBlank()) {
            val newChannelRef = mChannelRef.push()
            val channelItem = hashMapOf("name" to name)
            newChannelRef.setValue(channelItem)
        }
    }

    override fun onDestroy() {
        if (mEventListener != null) {
            mChannelRef.removeEventListener(mEventListener)
        }
        super.onDestroy()
    }

    private fun observeChannels() {
        mEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val channels = dataSnapshot.children.map { Channel(it.key, it.child("name").value.toString()) }
                channelList.adapter = ChannelListAdapter(channels) {
                    startActivity(ChatActivity.newIntent(this@ChannelListActivity, mName!!, it))
                }
                channelList.layoutManager = LinearLayoutManager(this@ChannelListActivity, LinearLayoutManager.VERTICAL, false)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(LOG_TAG, databaseError.message)
            }
        }

        mChannelRef.addValueEventListener(mEventListener)
    }

    companion object {
        private val LOG_TAG = "ChannelListActivity"

        private val EXTRA_NAME = "EXTRA_NAME"

        fun newIntent(context: Context, name: String): Intent {
            val intent = Intent(context, ChannelListActivity::class.java)
            intent.putExtra(EXTRA_NAME, name)
            return intent
        }
    }
}


