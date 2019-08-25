package ru.skillbranch.devintensive.models

import android.text.TextUtils
import java.util.regex.Pattern

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question){
                Question.NAME -> Question.NAME.question
                Question.PROFESSION -> Question.PROFESSION.question
                Question.MATERIAL -> Question.MATERIAL.question
                Question.BDAY -> Question.BDAY.question
                Question.SERIAL -> Question.SERIAL.question
                Question.IDLE -> Question.IDLE.question
    }

    companion object {
        var count =0
    }
    fun listenAnswer(answer: String) : Pair<String, Triple<Int,Int,Int>>{
        if (question.isAnswerValid(answer).isNotEmpty()){
            return "${question.isAnswerValid(answer)}\n${question.question}" to status.color
        }
        return if (question.answer.contains(answer.toLowerCase()) ) {
            question = question.nextQuestion()
            return "Отлично - ты справился\n${question.question}" to status.color
        } else {
            count++
            if (count > 3 && question != Question.IDLE ) {
                status = Status.NORMAL
                question = Question.NAME
                count=0
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            //TODO change status
            if (question == Question.IDLE) {
                return "${question.question}" to status.color
            } else {
                status = status.nextStatus()
                "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }

    enum class Status(val color: Triple<Int,Int,Int>){
        NORMAL(Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        CRITICAL(Triple(255,0,0));

        fun nextStatus(): Status {
            return if (this.ordinal< values().lastIndex){
               values()[this.ordinal +1]

            }else{
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answer: List<String>){
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun isAnswerValid(answer: String): String {
                return if (answer[0].isLowerCase()){
                    "Имя должно начинаться с заглавной буквы"
                } else ""
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun isAnswerValid(answer: String): String {
                return if (answer[0].isUpperCase()){
                    "Профессия должна начинаться со строчной буквы"
                } else ""
            }
        },
        MATERIAL("Из чего я сделан?", listOf("метал", "дерево", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun isAnswerValid(answer: String): String {
                var pattern = Pattern.compile("[0-9]")
                var matcher = pattern.matcher(answer)
                return if (matcher.find()){
                    "Материал не должен содержать цифр"
                } else ""
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun isAnswerValid(answer: String): String {
                return if (!TextUtils.isDigitsOnly(answer)){
                    "Год моего рождения должен содержать только цифры"
                } else ""
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun isAnswerValid(answer: String): String {
                var isDigit = TextUtils.isDigitsOnly(answer)
                return if (answer.count() != 7 || !isDigit){
                    "Серийный номер содержит только цифры, и их 7"
                } else ""
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun isAnswerValid(answer: String): String {
                return ""
            }
        };

        abstract fun nextQuestion(): Question
        abstract fun isAnswerValid(answer: String): String

    }
}