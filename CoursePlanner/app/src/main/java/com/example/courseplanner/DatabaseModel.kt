package com.example.courseplanner

import java.io.Serializable

data class DatabaseModel (var course_id: String? = null,
                    var course_name: String? = null,
                    var credit: String? = null,
                    var days: String? = null,
                    var ects: String? = null,
                    var hours: String? = null,
                    var instructor: String? = null,
                    var syllabus: String? = null
) : Serializable
