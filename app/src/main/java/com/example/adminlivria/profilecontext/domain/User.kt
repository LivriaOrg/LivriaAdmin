package com.example.adminlivria.profilecontext.domain

// Clase abstracta o abierta para que otras entidades hereden de ella
open class User(
    open val username: String, // users.Username
    open val fullName: String, // users.Display
    open val email: String // users.Email

)