package com.example.groceriestracker.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UpcAssociation(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "upc") val upc: String?,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "amount") val amount: Double,// TODO add unit props
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uid)
        parcel.writeString(upc)
        parcel.writeInt(itemId)
        parcel.writeDouble(amount)
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
