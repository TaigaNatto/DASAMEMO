package natto.com.dasamemo

import io.realm.RealmObject
import java.util.*

open
class MemoModel : RealmObject {
    var title: String?=null
    var date: Date?=null
    var content: String?=null

    constructor(){

    }

    constructor(title:String,date:Date,content:String){
        this.title=title
        this.date=date
        this.content=content
    }
}