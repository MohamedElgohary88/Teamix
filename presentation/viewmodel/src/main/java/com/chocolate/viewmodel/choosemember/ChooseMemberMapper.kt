package com.chocolate.viewmodel.choosemember

import com.chocolate.entities.member.Member

fun List<Member>.toUsersUiState(): List<ChooseMembersUiState>{
    return this.map { it.toUserUiState()}
}

fun Member.toUserUiState(): ChooseMembersUiState{
    return ChooseMembersUiState(
        userId = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
    )
}