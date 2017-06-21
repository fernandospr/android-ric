package ar.com.fufoinc.ric;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    var mUserName: String? = null
    var mChannel: Channel? = null

    var mMessageRef: DatabaseReference? = null

    var mSenderId: String? = null

    var mMessageQuery: Query? = null

    var mEventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mUserName = intent.getStringExtra(ChatActivity.EXTRA_USER_NAME)
                ?: throw IllegalStateException("field ${ChatActivity.EXTRA_USER_NAME} missing in Intent")
        mChannel = intent.getParcelableExtra(ChatActivity.EXTRA_CHANNEL)
                ?: throw IllegalStateException("field ${ChatActivity.EXTRA_CHANNEL} missing in Intent")

        mMessageRef = FirebaseDatabase.getInstance().reference.child("channels").child(mChannel!!.id).child("messages")

        mSenderId = FirebaseAuth.getInstance().currentUser?.uid

        observeMessages()
    }

    override fun onDestroy() {
        if (mEventListener != null) {
            mMessageQuery?.removeEventListener(mEventListener)
        }
        super.onDestroy()
    }

    private fun observeMessages() {
        mMessageQuery = mMessageRef?.limitToLast(25)

        mEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //TODO: Show chat messages
                Log.d(LOG_TAG, dataSnapshot.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(ChatActivity.LOG_TAG, databaseError.message)
            }
        }

        mMessageQuery?.addValueEventListener(mEventListener)
    }

    companion object {
        private val LOG_TAG = "ChatActivity"

        private val EXTRA_USER_NAME = "EXTRA_USER_NAME"
        private val EXTRA_CHANNEL = "EXTRA_CHANNEL"

        fun newIntent(context: Context, userName: String, channel: Channel): Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(EXTRA_USER_NAME, userName)
            intent.putExtra(EXTRA_CHANNEL, channel)
            return intent
        }
    }
}
