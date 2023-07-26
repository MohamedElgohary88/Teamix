package com.chocolate.remote.messages.service

import com.chocolate.remote.messages.request.EditMessageRequest
import com.chocolate.remote.messages.request.MatchNarrowRequest
import com.chocolate.remote.messages.request.SendMessageRequest
import com.chocolate.remote.messages.request.UpdateMessageFlagsNarrowRequest
import com.chocolate.remote.messages.request.UpdateMessageFlagsRequest
import com.chocolate.remote.messages.response.dto.send_message.DefaultMessageRemoteDto
import com.chocolate.remote.messages.response.dto.send_message.MessagesRemoteDto
import com.chocolate.remote.messages.response.dto.send_message.SingleMessageRemoteDto
import com.chocolate.remote.messages.response.dto.send_message.MarkAsReadResponse
import com.chocolate.remote.messages.response.dto.send_message.MatchNarrowRemoteDto
import com.chocolate.remote.messages.response.dto.send_message.MessageEditHistoryRemoteDto
import com.chocolate.remote.messages.response.dto.send_message.MessageReadReceiptsRemoteDto
import com.chocolate.remote.messages.response.dto.send_message.PersonalMessageFlags
import com.chocolate.remote.messages.response.dto.send_message.PersonalMessageForNarrowRemoteDto
import com.chocolate.remote.messages.response.dto.send_message.RenderMessageRemoteDto
import com.chocolate.remote.messages.response.dto.send_message.SendMessageRemoteDto
import com.chocolate.remote.messages.response.dto.send_message.FileRemoteDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MessageService {

    @POST("messages")
    suspend fun sendStreamMessage(@Body sendMessageRequest: SendMessageRequest): Response<SendMessageRemoteDto>

    @POST("messages")
    suspend fun sendDirectMessage(@Body sendMessageRequest: SendMessageRequest): Response<SendMessageRemoteDto>

    @POST("user_uploads")
    suspend fun uploadFile(@Body sendMessageRequest: SendMessageRequest): Response<FileRemoteDto>

    @PATCH("messages/{message_id}")
    suspend fun editMessage(
        @Body editMessageRequest: EditMessageRequest,
        @Query("topic") topic: String = "",
        @Query("propagate_mode") propagateMode: String = "change_one",
        @Query("send_notification_to_old_thread") sendNotificationToOldThread: Boolean = false,
        @Query("send_notification_to_new_thread") sendNotificationToNewThread: Boolean = true
    ): Response<DefaultMessageRemoteDto>

    @DELETE("messages/{message_id}")
    suspend fun deleteMessage(
        @Query("message_id") message_id: Int
    ): Response<DefaultMessageRemoteDto>

    @GET("messages")
    suspend fun getMessages(
        @Query("anchor") anchor: String?,
        @Query("include_anchor") includeAnchor: Boolean = true,
        @Query("num_before") numBefore: Int,
        @Query("num_after") numAfter: Int,
        @Query("narrow") narrow: List<String>?,
        @Query("client_gravatar") clientGravatar: Boolean = true,
        @Query("apply_markdown") applyMarkdown: Boolean = true
    ): Response<MessagesRemoteDto>

    @POST("messages/{message_id}/reactions")
    suspend fun addEmojiReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String?,
        @Query("reaction_type") reactionType: String?
    ): Response<DefaultMessageRemoteDto>

    @DELETE("messages/{message_id}/reactions")
    suspend fun deleteEmojiReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String?,
        @Query("reaction_type") reactionType: String?
    ): Response<DefaultMessageRemoteDto>

    @POST("messages/render")
    suspend fun renderMessage(
        @Query("content") content: String,
    ): Response<RenderMessageRemoteDto>

    @GET("messages/{message_id}")
    suspend fun fetchSingleMessage(
        @Path("message_id") messageId: Int
    ): Response<SingleMessageRemoteDto>

    @GET("messages/matches_narrow")
    suspend fun checkIfMessagesMatchNarrow(
        @Body matchNarrowRequest: MatchNarrowRequest
    ): Response<MatchNarrowRemoteDto>

    @GET("messages/{message_id}/history")
    suspend fun getMessagesEditHistory(
        @Path("message_id") messageId: Int
    ): Response<MessageEditHistoryRemoteDto>

    @POST("messages/flags")
    suspend fun updateMessageFlags(
        @Body updateMessageFlagsRequest: UpdateMessageFlagsRequest
    ): Response<PersonalMessageFlags>

    @POST("messages/flags/narrow")
    suspend fun updatePersonalMessageFlagsForNarrow(
        @Query("anchor") anchor: String,
        @Query("num_before") numBefore: Int,
        @Query("num_after") numAfter: Int,
        @Body updateMessageFlagsNarrowRequest: UpdateMessageFlagsNarrowRequest
    ): Response<PersonalMessageForNarrowRemoteDto>

    @POST("mark_all_as_read")
    suspend fun markAllMessagesAsRead(): Response<DefaultMessageRemoteDto>

    @POST("mark_stream_as_read")
    suspend fun markStreamAsRead(
        @Query("stream_id") steamId: Int
    ): Response<MarkAsReadResponse>

    @POST("mark_topic_as_read")
    suspend fun markTopicAsRead(
        @Query("stream_id") steamId: Int,
        @Query("topic_name") topicName: String
    ): Response<MarkAsReadResponse>

    @GET("messages/{message_id}/read_receipts")
    suspend fun getMessageReadReceipts(
        @Path("message_id") messageId: Int
    ): Response<MessageReadReceiptsRemoteDto>



}