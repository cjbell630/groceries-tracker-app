package com.example.groceriestracker.database

import android.os.Parcel
import android.os.Parcelable

data class ItemStatus(
    val time: Long,
    val amount: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(time)
        parcel.writeDouble(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemStatus> {
        override fun createFromParcel(parcel: Parcel): ItemStatus {
            return ItemStatus(parcel)
        }

        override fun newArray(size: Int): Array<ItemStatus?> {
            return arrayOfNulls(size)
        }
    }
}
