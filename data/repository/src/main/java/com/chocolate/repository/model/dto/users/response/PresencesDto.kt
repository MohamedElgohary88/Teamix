package com.chocolate.repository.model.dto.users.response

import com.google.gson.annotations.SerializedName

data class PresencesDto(
    @SerializedName("iago@zulip.com")
    val iagoZulipComDto: IagoZulipComDto?
)