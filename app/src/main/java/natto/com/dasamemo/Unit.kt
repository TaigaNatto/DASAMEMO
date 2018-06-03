package natto.com.dasamemo

import java.util.*

class Unit {
    fun generateDateToStringType(date: Date):String{
        return "${date?.year?.plus(1900)}/${date?.month?.plus(1)}/${date?.date} | ${date?.hours}:${date?.minutes}-${date?.seconds}s"
    }
}