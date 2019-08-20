package ru.skillbranch.devintensive.utils

object Utils{
    fun parseFullName(fullName: String?) : Pair<String?, String?> {
        //TODO FIX ME!!!
        val parts : List<String>? = fullName?.split(" ")
        if (parts != null) {
            if (parts.contains("") || parts.contains(" "))
                return Pair(null, null)
        }
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        println("/n TEST!!! $firstName $lastName")
        return Pair(firstName,lastName)
    }

    fun transliteration(payload: String, divider:String =" "): String {
        //TODO
        return ""
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return ""
    }
}