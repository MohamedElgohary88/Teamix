package com.chocolate.entities.member

data class Member (
    val id: String,
    val name: String,
    val email: String,
    val imageUrl: String,
    val isActive: Boolean,
    val role: UserRole,
    val status: String,
    val channelsId: List<String>
)