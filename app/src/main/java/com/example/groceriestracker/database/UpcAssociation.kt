package com.example.groceriestracker.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class UpcAssociation(
    @PrimaryKey val upc: Long = 0,
    @ColumnInfo(name = "item_id") val itemId: Int
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(upc)
        parcel.writeInt(itemId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UpcAssociation> {
        override fun createFromParcel(parcel: Parcel): UpcAssociation {
            return UpcAssociation(parcel)
        }

        override fun newArray(size: Int): Array<UpcAssociation?> {
            return arrayOfNulls(size)
        }
    }

}
