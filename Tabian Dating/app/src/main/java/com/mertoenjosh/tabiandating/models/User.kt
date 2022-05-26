package com.mertoenjosh.tabiandating.models

import android.os.Parcel
import android.os.Parcelable

data class User(
    val profile_image: Int,
    val name: String,
    val gender: String,
    val interested_in: String,
    val status: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(profile_image)
        parcel.writeString(name)
        parcel.writeString(gender)
        parcel.writeString(interested_in)
        parcel.writeString(status)

    }
    override fun describeContents(): Int {
        return 0
    }



    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
