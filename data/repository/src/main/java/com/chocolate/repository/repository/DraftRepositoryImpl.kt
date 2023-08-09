package com.chocolate.repository.repository

import com.chocolate.entities.draft.Draft
import com.chocolate.repository.mappers.draft.toEntity
import com.chocolate.repository.service.remote.DraftMessageRemoteDataSource
import com.google.gson.Gson
import repositories.DraftRepository
import javax.inject.Inject

class DraftRepositoryImpl @Inject constructor(
    private val draftDataSource: DraftMessageRemoteDataSource
) : DraftRepository, BaseRepository() {
    override suspend fun getDrafts():List<Draft> {
        return wrapApiCall { draftDataSource.getDrafts() }.drafts.toEntity()
    }

    override suspend fun createDraft(
        id: Int,
        type: String,
        to: List<Int>,
        topic: String,
        content: String,
        timestamp: Long
    ):List<Int> {

        return wrapApiCall {
            draftDataSource.createDraft(
                id = id,
                type = type,
                content = content,
                topic = topic,
                to = to.toJson(),
                timestamp = timestamp
            )
        }.ids?: emptyList()
    }

    override suspend fun editDraft(
        id: Int,
        type: String,
        to: List<Int>,
        topic: String,
        content: String,
        timestamp: Long
    ) {
        wrapApiCall {
            draftDataSource.editDraft(
                id = id,
                type = type,
                content = content,
                topic = topic,
                to = to.toJson(),
                timestamp = timestamp
            )
        }
    }

    override suspend fun deleteDraft(id: Int) {
        wrapApiCall { draftDataSource.deleteDraft(id) }
    }
}

fun Any.toJson() = Gson().toJson(this)