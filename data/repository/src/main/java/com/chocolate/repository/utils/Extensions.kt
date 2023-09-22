package com.chocolate.repository.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

fun Any.toJson() = Gson().toJson(this)


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentTime(): Date {
    val instant = Instant.now()
    return Timestamp.from(instant)

}
