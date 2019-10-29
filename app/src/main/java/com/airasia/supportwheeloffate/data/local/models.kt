package com.airasia.supportwheeloffate.data.local

data class NavigationItem(var id: Int, var title: String, var menuIcon: Int)

data class HomeItem(var name: String, var issueList : ArrayList<Issue>)

data class Issue(var title : String, var upVote : String, var downVote : String, var time :String)