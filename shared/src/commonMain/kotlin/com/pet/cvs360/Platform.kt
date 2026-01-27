package com.pet.cvs360

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform