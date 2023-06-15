package com.example.apitest

// User Model
data class User (
    val userId: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val dateOfBirth: String,
    val createdAt: String
)

// User DataTransferObject Model
data class UserDto (
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val dateOfBirth: String
)

// User creation model
data class CreateUserRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val dateOfBirth: String
)

// Task score update model
data class UpdateScoreboardInfinitev1(
    val taskAndNumber: String,
    val solved: Boolean,
    val userId: Int
)