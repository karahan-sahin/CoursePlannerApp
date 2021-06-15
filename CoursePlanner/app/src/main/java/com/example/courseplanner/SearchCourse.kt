package com.example.courseplanner

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search_course.*

private val context: Context? = null

class SearchCourse : Activity() {

    // Hold for every session
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var recyclerView: RecyclerView

    private lateinit var course_list: ArrayList<DatabaseModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_course)

        database = FirebaseDatabase.getInstance()
        reference = database.reference

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        course_list = arrayListOf<DatabaseModel>()

        getAllCourse()

        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                var query = s.toString()
                getQueryCourses(query)
            }
        })
    }

    fun getAllCourse() {

        // Get it by textview query
        reference.orderByKey().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(courses: DataSnapshot) {

                // Iterate over snapshot data
                for (data in courses.children) {
                    val model = data.getValue(DatabaseModel::class.java)
                    course_list.add(model!!)
                    println(data)
                }

                recyclerView.adapter = DataAdapter(course_list, this@SearchCourse)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase Cancelled")
                Log.e("cancel", "Cancelled")
            }
        })
    }

    fun getQueryCourses(query: String) {

        // Get it by textview query
        reference.orderByKey().startAt(query.uppercase())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(courses: DataSnapshot) {

                    var list: ArrayList<DatabaseModel> = arrayListOf()

                    // Iterate over snapshot data
                    for (data in courses.children) {
                        val model = data.getValue(DatabaseModel::class.java)
                        list.add(model!!)
                        println(data)
                    }

                    recyclerView.adapter = DataAdapter(list, this@SearchCourse)
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Firebase Cancelled")
                    Log.e("cancel", "Cancelled")
                }
            })

    }
}