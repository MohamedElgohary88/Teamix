package com.chocolate.teamix.di

import com.chocolate.local.dao.RoomDataSource
import com.chocolate.local.datastore.DataStoreDataSource
import com.chocolate.remote.data_source.ChannelFireBaseDataSource
import com.chocolate.remote.data_source.MessagesDataSourceImpl
import com.chocolate.remote.data_source.OrganizationDataSourceImpl
import com.chocolate.remote.data_source.UserRetrofitDataSource
import com.chocolate.remote.firebase.TaskFirebase
import com.chocolate.remote.data_source.TopicFireBaseDataSource
import com.chocolate.repository.datastore.local.LocalDataSource
import com.chocolate.repository.datastore.local.PreferencesDataSource
import com.chocolate.repository.datastore.realtime.TopicDataSource
import com.chocolate.repository.datastore.remote.ChannelRemoteDataSource
import com.chocolate.repository.datastore.remote.MessagesRemoteDataSource
import com.chocolate.repository.datastore.remote.OrganizationRemoteDataSource
import com.chocolate.repository.datastore.remote.UserRemoteDataSource
import com.chocolate.repository.datastore.remote.TaskRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindLocalDataSource(
        roomDataSource: RoomDataSource
    ): LocalDataSource

    @Singleton
    @Binds
    abstract fun bindPreferencesDataSource(
        dataStoreDataSource: DataStoreDataSource
    ): PreferencesDataSource

    @Singleton
    @Binds
    abstract fun bindChannelDataSource(channelDataSource: ChannelFireBaseDataSource):
            ChannelRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindMessagesDataSource(messagesDataSource: MessagesDataSourceImpl):
            MessagesRemoteDataSource

//    @Singleton
//    @Binds
//    abstract fun bindOrganizationDataSource(organizationDataSource: OrganizationRetrofitDataSource):
//            OrganizationRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindUserDataSource(userDataSource: UserRetrofitDataSource): UserRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindUserDatabase(taskFirebase: TaskFirebase): TaskRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsTopicDataSource(topicDataSource: TopicFireBaseDataSource): TopicDataSource

    @Singleton
    @Binds
    abstract fun bindsOrganizationDataSource(organization: OrganizationDataSourceImpl): OrganizationRemoteDataSource
}