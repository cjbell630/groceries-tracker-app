package com.example.groceriestracker.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.groceriestracker.database.ItemStatusTypeConverter

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "unit") val unit: String?,
    @ColumnInfo(name = "icon_id") val iconId: String?,
    // TODO add "needs update" boolean field
    @ColumnInfo(name = "status_events") @TypeConverters(ItemStatusTypeConverter::class) val statusEvents: List<ItemStatus>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(ItemStatus) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uid)
        parcel.writeString(name)
        parcel.writeString(unit)
        parcel.writeString(iconId)
        parcel.writeTypedList(statusEvents)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
