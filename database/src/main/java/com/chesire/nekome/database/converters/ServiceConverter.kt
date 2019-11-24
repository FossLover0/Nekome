package com.chesire.nekome.database.converters

import androidx.room.TypeConverter
import com.chesire.nekome.core.flags.Service

class ServiceConverter {
    @TypeConverter
    fun fromService(service: Service): String = service.name

    @TypeConverter
    fun toService(service: String): Service = Service.valueOf(service)
}