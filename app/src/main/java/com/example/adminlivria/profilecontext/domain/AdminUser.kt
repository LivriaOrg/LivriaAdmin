package com.example.adminlivria.profilecontext.domain


data class AdminUser(
    val id: String,
    override val username: String,
    override val fullName: String,
    override val email: String,
    val capital: Double,
    val adminAccess: Int,
) : User(username, fullName, email) {

    companion object {
        fun mock() = AdminUser(
            id = "ADMIN_001",
            username = "AAAAAAAAAAA",
            fullName = "admin",
            email = "admin@livria.com",
            capital = 4556.37,
            adminAccess = 0
        )
    }
}