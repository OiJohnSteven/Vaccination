package com.example.sandra

class Vaccine{
    var id: String = ""
    var polio1: String = ""
    var polio2: String = ""
    var polio3: String = ""
    var measles: String = ""

    constructor()
    constructor(id: String, polio1: String, polio2: String, polio3: String, measles: String) {
        this.id = id
        this.polio1 = polio1
        this.polio2 = polio2
        this.polio3 = polio3
        this.measles = measles
    }


}
