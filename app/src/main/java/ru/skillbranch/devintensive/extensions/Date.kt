package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND *60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)

}

fun Date.add(value: Int, units:TimeUnits = TimeUnits.SECOND): Date{
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

 fun Date?.humanizeDiff(): String {
     var curDate = Date();
     var dif = curDate.time - this!!.time

     when (true) {
         dif < 59 * SECOND -> return "только что"
         dif < 2 * MINUTE -> return  "минуту назад"
         dif < 60 * MINUTE -> {
             var i = (dif / MINUTE).toInt()
             when (true) {
                 i in 2..4 -> return "$i минуты назад"
                 i in 5 ..59 -> return "$i минут назад"
             }
         }
         dif < 2 * HOUR -> return  "час назад"
         dif < 24 * HOUR -> {
             var i = (dif / HOUR).toInt()
             when (true) {
                 i in 2..4 -> return "$i часа назад"
                 i in 5..20 -> return "$i часов назад"
                 i == 21 -> return "$i час назад"
                 i in 22..23 -> return "$i часа назад"
             }
         }
         dif < 2 * DAY -> return  "день назад"
         dif < 366 * DAY -> {
             var day = (dif / DAY).toInt()
             var temp = day
             if (temp>20) temp%=10
             when (true) {
                 temp === 1 -> return "$day день назад"
                 temp in 2..4 -> return "$day дня назад"
                 temp in 5.. 20 -> return "$day дней назад"
             }

         }


     }
     return "больше года назад"
 }

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}