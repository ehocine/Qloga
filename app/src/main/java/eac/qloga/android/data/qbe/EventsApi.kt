package eac.qloga.android.data.qbe

import eac.qloga.bare.dto.events.Event
import eac.qloga.bare.enums.events.EventNotificationType
import eac.qloga.bare.enums.events.EventPartyStatus
import retrofit2.Response
import retrofit2.http.*

interface EventsApi {

    @GET("qbe/events")
    suspend fun get(
        @Query("from") from: String,
        @Query("to") to: String
    ): List<Event>

    //0
    @POST("qbe/events")
    suspend fun add(
        @Body event: Event
    ): Event

    //0
    @PATCH("qbe/events/{eid}")
    suspend fun update(
        @Body event: Event,
        @Path("eid") eid: Long,
        @Query("date") date: String?
    ): Event

    @GET("qbe/events/{eid}")
    suspend fun getEvent(
        @Path("eid") eid: Long
    ): Event

    @DELETE("qbe/events/{eid}")
    suspend fun deleteEvent(
        @Path("eid") eid: Long,
        @Query("date") date: String?
    ): Response<Unit>

    //0
    @PUT("qbe/events/{eid}")
    suspend fun setStatus(
        @Path("eid") eid: Long,
        @Body status: EventPartyStatus,
        @Query("date") date: String?
    ): Response<Unit>

    //0
    @PATCH("qbe/events/{eid}")
    suspend fun setNotification(
        @Path("eid") eid: Long,
        @Path("ntfType") ntfType: EventNotificationType,
        @Path("ntfWhen") ntfWhen: Long,
        @Path("date") date: String?
    ): Any

    //0
    @PATCH("qbe/events/{eid}")
    suspend fun updateParties(
        @Path("eid") eid: Long,
        @Path("parties") parties: ArrayList<Int>,
        @Path("date") date: String?
    ): Response<Unit>

}