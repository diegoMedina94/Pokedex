package com.example.pokedex.ui.navigation

abstract class NestedDestination {

    abstract val route: String

    open fun getRouteWithArgs(
        vararg arguments: Pair<String,String>,
    ) : String {
        var newRoute = route.substringBefore("/")
        for(argument in arguments){
            newRoute +="/${argument.second}"
        }
        return newRoute
    }
}