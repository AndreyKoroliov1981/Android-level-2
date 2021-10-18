package com.korol.diapplication.classes

class Bicycle (val logo:String, val frame: Frame, val frontWheel: Wheel, val backWheel: Wheel) {


    fun build(logo: String, color: Int): Bicycle {

        fun getFrame(color: Int): Frame {
            return Frame(color)
        }
        val myLogo = logo
        val myFrame = getFrame(color)
        val wD = WheelDealer
        wD.init("Kama Research")
        val w1 = wD.getWheel()
        val w2 = wD.getWheel()
        return Bicycle(myLogo, myFrame, w1, w2)
    }
}