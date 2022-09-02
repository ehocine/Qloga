package eac.qloga.android.data.shared.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eac.qloga.p4p.prv.enums.PrvFields
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun List<PrvFields>.listToString(): List<String> {
    var listOfPrvFielsd = arrayListOf<String>()

    this.forEach {
        listOfPrvFielsd.add(it.name)
    }
    return listOfPrvFielsd
}

fun Any.toJsonString(): String {
    val mapper = jacksonObjectMapper()
    return mapper.writeValueAsString(this)
}

fun Any.toJsonStringWithDate(): String {
    val objectMapper = ObjectMapper()
    objectMapper.findAndRegisterModules()
    objectMapper.registerModule(JavaTimeModule())
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    return objectMapper.writeValueAsString(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun ZonedDateTime.dateToString(): String {
    return this.format(DateTimeFormatter.ISO_INSTANT)
}

