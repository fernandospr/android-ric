package ar.com.fufoinc.ric

import android.os.Parcel
import android.os.Parcelable

data class Channel(val id: String, val name: String): Parcelable {
    constructor(source: Parcel): this(source.readString(), source.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(this.id)
        dest?.writeString(this.name)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Channel> = object : Parcelable.Creator<Channel> {
            override fun createFromParcel(source: Parcel): Channel{
                return Channel(source)
            }

            override fun newArray(size: Int): Array<Channel?> {
                return arrayOfNulls(size)
            }
        }
    }
}