package com.korol.diapplication.classes

import com.korol.diapplication.koin.RepositoryBicycle

class MySimplePresenter(val repo: RepositoryBicycle) {
    fun sayHello():Bicycle=repo.giveBicycle()
}