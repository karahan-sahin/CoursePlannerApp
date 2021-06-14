package com.example.courseplanner

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search_course.*
import java.text.FieldPosition

class SearchCourse : AppCompatActivity() {

    // Hold for every session
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    lateinit var course_list: ArrayList<DatabaseModel>

    var adapter: DataAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_course)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Courses")

        getAllCourse()

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = DataAdapter(course_list)
        recyclerView.adapter = adapter

        val input = searchText
        input.addTextChangedListener(textWatcher)

    }

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            TODO("Not yet implemented")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            TODO("Not yet implemented")
        }

        override fun afterTextChanged(s: Editable?) {
            val query = s.toString()
            // Recycler view of courses
            getCourse(query)
            recyclerView.adapter = DataAdapter(course_list)
        }

    }

    fun getAllCourse() {

        // Get it by textview query
        reference.orderByKey().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(courses: DataSnapshot) {
                // Iterate over snapshot data
                for (data in courses.children) {
                    var model = data.getValue(DatabaseModel::class.java)
                    course_list.add(model as DatabaseModel)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel", "Cancelled")
            }
        })

    }
    fun getCourse (query: String) {
        // Get it by textview query
        reference.orderByKey().startAt(query.uppercase()).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(courses: DataSnapshot) {

                    // Hold the course list
                    var list = ArrayList<DatabaseModel>()

                    // Iterate over snapshot data
                    for (data in courses.children) {
                        var model = data.getValue(DatabaseModel::class.java)
                        list.add(model as DatabaseModel)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("cancel", "Cancelled")
                }
            })
    }

}