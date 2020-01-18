package com.test.rajat.wikisearch.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity

@Entity(primaryKeys = [("id")])
data class Article(
    val id:Int,
    val pageId:Int,
    val ns:Int,
    val title:String,
    val index:Int,
    var source:String,
    val terms:String): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeInt(id)
        parcel?.writeInt(pageId)
        parcel?.writeInt(ns)
        parcel?.writeString(title)
        parcel?.writeInt(index)
        parcel?.writeString(source)
        parcel?.writeString(terms)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}