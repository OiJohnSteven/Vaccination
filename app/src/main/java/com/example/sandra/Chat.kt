package com.example.sandra

class Chat {
    var id:String ?= null
    var message:String ?= null
    var time:String ?= null
    var status:String ?= null

    constructor()
    constructor(id: String?, message: String?, time: String?, status: String?) {
        this.id = id
        this.message = message
        this.time = time
        this.status = status
    }


}