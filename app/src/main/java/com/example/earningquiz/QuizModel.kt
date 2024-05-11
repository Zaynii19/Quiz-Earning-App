package com.example.earningquiz

class QuizModel {
    var question = ""
    var opt1 = ""
    var opt2 = ""
    var opt3 = ""
    var opt4 = ""
    var ans = ""

    //used by Firebase
    constructor()

    constructor(
        question: String,
        opt1: String,
        opt2: String,
        opt3: String,
        opt4: String,
        ans: String
    ) {
        this.question = question
        this.opt1 = opt1
        this.opt2 = opt2
        this.opt3 = opt3
        this.opt4 = opt4
        this.ans = ans
    }


}