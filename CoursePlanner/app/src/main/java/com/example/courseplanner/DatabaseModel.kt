package com.example.courseplanner

import kotlinx.android.synthetic.main.course_list.view.*
import java.io.Serializable

class DatabaseModel(var courseID: String,
                    var courseName: String,
                    var instructor: String,
                    var days: String,
                    var hours: String,
                    var credit: String,
                    var ects: String,
                    var syllabus: String,
): Serializable {

    fun fix() {
        courseID = courseID.toString().replace( "-",".")
    }

}