package eac.qloga.android.data.qbe

import android.os.Build
import androidx.annotation.RequiresApi
import eac.qloga.android.data.shared.utils.dateToString
import eac.qloga.android.di.QLOGAApiService
import eac.qloga.bare.dto.events.Event
import eac.qloga.bare.enums.events.EventNotificationType
import eac.qloga.bare.enums.events.EventPartyStatus
import java.time.ZonedDateTime


@RequiresApi(Build.VERSION_CODES.O)
class EventsRepository(@QLOGAApiService private val apiService: EventsApi) {
    //events
    suspend fun get(from: ZonedDateTime, to: ZonedDateTime) =
        apiService.get(from.dateToString(), to.dateToString())

    suspend fun add(event: Event) = apiService.add(event)
    suspend fun update(event: Event, eid: Long, date: ZonedDateTime?) =
        apiService.update(event, eid, date?.dateToString())

    suspend fun getEvent(eid: Long) = apiService.getEvent(eid)
    suspend fun deleteEvent(eid: Long, date: ZonedDateTime?) =
        apiService.deleteEvent(eid, date?.dateToString())

    suspend fun setStatus(eid: Long, status: EventPartyStatus, date: ZonedDateTime?) =
        apiService.setStatus(eid, status, date?.dateToString())

    suspend fun setNotification(
        eid: Long,
        ntfType: EventNotificationType,
        ntfWhen: Long, date: ZonedDateTime?
    ) = apiService.setNotification(eid, ntfType, ntfWhen, date?.dateToString())

    suspend fun updateParties(
        eid: Long,
        parties: ArrayList<Int>,
        date: ZonedDateTime?
    ) = apiService.updateParties(eid, parties, date?.dateToString())

}