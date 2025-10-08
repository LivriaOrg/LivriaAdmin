package com.example.adminlivria.domain.profilecontext.entity

// AdminUser hereda de User y añade sus propiedades únicas.
data class AdminUser(
    val id: String, // Clave única
    override val username: String,
    override val fullName: String,
    override val email: String,
    val capital: Double,
    val adminAccess: Int,
) : User(username, fullName, email) {

    companion object {
        fun mock() = AdminUser(
            id = "ADMIN_001",
            username = "admin_livria",
            fullName = "Alex Tomio Nakamurakare",
            email = "admin@livria.com",
            capital = 4556.37,
            adminAccess = 0
        )
    }
}