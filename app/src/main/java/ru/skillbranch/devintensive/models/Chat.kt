package ru.skillbranch.devintensive.models

class Chat (
    val id: String,
    var members: MutableList<User> = mutableListOf(),
    val message: MutableList<BaseMessage> = mutableListOf()
) {
}