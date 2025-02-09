package com.org.childmonitorparent.parent.models

import java.io.Serializable

data class AddChildInfo(
    var name: String = "",
    var location: String = "",
    var selectedAge: String = "",
    var selectedGender: String = "",
    var selectedHeight: String = "",
    var selectedWeight: String = "",
    var schoolName: String = "",
    var schoolLocation: String = "",
    var parentId: String = ""
) : Serializable {
    constructor() : this("", "", "", "", "", "", "", "", "")
}
