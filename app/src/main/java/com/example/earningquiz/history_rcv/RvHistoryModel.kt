package com.example.earningquiz.history_rcv

import java.util.Date

class RvHistoryModel{
    var timeDate:Date = Date()
    var historyCoins:Int = 0
    var isWithdrawl:Boolean = false

    //used by Firebase
    constructor()
    constructor(timeDate: Date, historyCoins: Int, isWithdrawl: Boolean) {
        this.timeDate = timeDate
        this.historyCoins = historyCoins
        this.isWithdrawl = isWithdrawl
    }
}