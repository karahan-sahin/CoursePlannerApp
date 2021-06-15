package com.example.courseplanner

import android.app.Activity
import android.app.ActivityManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Response
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search_course.*
import kotlinx.android.synthetic.main.course_list.*

class MainActivity : Activity() {

    // Hold for every session
    private lateinit var recyclerView: RecyclerView
    private lateinit var current_courses: ArrayList<DatabaseModel>
    private lateinit var course_names: ArrayList<String>

    var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        current_courses = arrayListOf<DatabaseModel>()
        course_names = arrayListOf<String>()


    }

    fun search_intent(view: View) {
        val intent = Intent(this@MainActivity,SearchCourse::class.java)
        startActivityForResult(intent,1)
        // send current course to search
        //
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                for (day in arrayOf("M","T","W","Th","F")) {
                    for (hour in 1..9){
                        var id = resources.getIdentifier("text${day}_${hour}", "id", packageName)
                        val slot = findViewById<TextView>(id) as TextView
                        slot.text = ""
                    }
                }

                var result = data!!.getSerializableExtra("Course") as DatabaseModel

                if (!course_names.filter { it == result.course_id.toString() }.any()) {
                    current_courses.add(result)
                    course_names.add(result.course_id.toString().replace("-","."))
                    total = 0
                }
                match_table(current_courses)

                recyclerView.adapter = ListAdapter(current_courses)

            }
        }


        super.onActivityResult(requestCode, resultCode, data)
    }

    fun match_days_and_hours(days: String, hours: String): MutableList<String> {
        var hours_idx = 0
        val match_list : MutableList<String> = ArrayList()
        if (days != "_" && hours != "_") {
            for (i in days.indices) {

                try {
                    if (days.slice(i..i + 1) == "Th") {
                        match_list.add("${days.slice(i..i + 1)}_${hours[hours_idx]}")
                        print("${days.slice(i..i + 1)}_${hours[hours_idx]}")
                    } else if (days[i] == 'h') {
                        hours_idx += 1
                        continue
                    } else {
                        match_list.add("${days[i]}_${hours[hours_idx]}")
                        print("${days[i]}_${hours[hours_idx]}")
                        hours_idx += 1
                    }
                } catch (e: Exception) {
                    match_list.add("${days[i]}_${hours[hours_idx]}")
                    break
                }
            }
        }

        return match_list
    }

    fun match_table(current_courses: ArrayList<DatabaseModel>){
        for (course in current_courses) {
            if (course.days == "_" && course.hours == "_"){
                total += course.credit.toString().toInt()
                totalCreditText.text = "$total"
            }
            else {
               for (match in match_days_and_hours(course.days.toString(), course.hours.toString())) {
                   var id = resources.getIdentifier("text${match}", "id", packageName)
                   val slot = findViewById<TextView>(id) as TextView

                   if (slot.text.toString().replace("-",".") != "" && slot.text.toString().replace("-",".") != course.course_id.toString().replace("-",".")) {
                       slot.text = slot.text.toString().replace("-",".") + "\n" + course.course_id.toString().replace("-",".")
                       slot.setBackgroundColor(Color.parseColor("#E62525"))
                   }
                   else{
                       slot.text = course.course_id.toString().replace("-",".")
                   }
               }

                total += course.credit.toString().toInt()
                totalCreditText.text = "$total"

            }
        }
    }

}