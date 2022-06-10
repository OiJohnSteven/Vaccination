package com.example.sandra

class Data {
    var id:String ?= null
    var text:String ?= null

    constructor()
    constructor(id:String, text: String?) {
        this.id = id
        this.text = text
    }
}


