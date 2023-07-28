package com.chocolate.teamix.di

import com.chocolate.repository.channels.ChannelsRepositoryImpl
import com.chocolate.repository.draft.DraftRepositoryImpl
import com.chocolate.repository.messages.MessagesRepositoryImpl
import com.chocolate.repository.secheduled_message.ScheduledMessageRepositoryImpl
import com.chocolate.repository.server_and_organizations.ServerAndOrganizationsRepositoryImpl
import com.chocolate.repository.users.UserRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import repositories.channels.ChannelsRepository
import repositories.draft.DraftRepository
import repositories.messages.MessagesRepository
import repositories.scheduled_message.ScheduledMessageRepository
import repositories.server_and_organizations.ServerAndOrganizationsRepository
import repositories.users.UsersRepositories

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMessagesRepository(messagesRepositoryImpl: MessagesRepositoryImpl): MessagesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindChannelsRepository(channelsRepositoryImpl: ChannelsRepositoryImpl): ChannelsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindUserRepository(userRepositoriesImp: UserRepositoryImp): UsersRepositories

    @Binds
    @ViewModelScoped
    abstract fun bindDraftRepository(draftRepositoryImpl: DraftRepositoryImpl): DraftRepository

    @Binds
    @ViewModelScoped
    abstract fun bindScheduledMessageRepository(scheduledMessageRepositoryImpl: ScheduledMessageRepositoryImpl): ScheduledMessageRepository

    @Binds
    @ViewModelScoped
    abstract fun bindServerAndOrganizationsRepository(serverAndOrganizationsRepositoryImpl: ServerAndOrganizationsRepositoryImpl): ServerAndOrganizationsRepository

}