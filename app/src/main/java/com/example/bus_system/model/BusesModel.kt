package com.example.bus_system.model

import kotlinx.android.synthetic.main.activity_admin_main.*

class BusesModel {
    var busName:String?= null
    var from:String?=null
    var to:String?=null
    var date:String?=null
    var seats:String?=null
    var price:String?=null

    constructor(){

    }

    constructor(busName:String?,from:String?, to:String?,date:String?,seats:String?,price:String?){
        this.busName=busName
        this.from=from
        this.to=to
        this.date=date
        this.seats=seats
        this.price=price
    }

}