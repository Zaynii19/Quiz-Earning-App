package com.example.earningquiz.history_rcv

class RvHistoryModel{
    var timeDate:String = ""
    var historyCoins:String = ""
    var isWithdrawl:Boolean = false

    //used by Firebase
    constructor()
    constructor(timeDate: String, historyCoins: String, isWithdrawl: Boolean) {
        this.timeDate = timeDate
        this.historyCoins = historyCoins
        this.isWithdrawl = isWithdrawl
    }
}