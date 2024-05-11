package com.example.earningquiz

class UserModel {
    var uName = ""
    var uAge = 0
    var uEmail = ""
    var uPass = ""

    //Used by Firebase
    constructor()

    constructor(uName: String,uAge: Int, uEmail: String, uPass: String) {
        this.uName = uName
        this.uAge = uAge
        this.uEmail = uEmail
        this.uPass = uPass
    }


}