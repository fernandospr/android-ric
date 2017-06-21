package ar.com.fufoinc.ric

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

data class Message(val messageId: String,
                   val senderId: String,
                   val senderName: String,
                   val message: String): IMessage {
    override fun getId(): String = this.messageId

    override fun getCreatedAt(): Date = Date() //TODO: Fix with real date

    override fun getUser(): IUser {
        return object : IUser {
            override fun getName(): String = senderName

            override fun getId(): String = senderId

            override fun getAvatar(): String = ""
        }
    }

    override fun getText(): String = message

}