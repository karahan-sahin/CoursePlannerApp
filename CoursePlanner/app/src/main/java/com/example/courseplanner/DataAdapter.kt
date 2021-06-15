package com.example.courseplanner

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.course_list.view.*


private val context: Context? = null

class DataAdapter(var list: ArrayList<DatabaseModel>, var activity: Activity): RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView){

        var courseID = itemView.courseIdText
        var courseName = itemView.courseNameText
        var instructor = itemView.instructorText

        var days = itemView.daysText
        var hours = itemView.hoursText

        var credit = itemView.creditText
        var ects = itemView.ectsText

        val addCourse = itemView.addCourseButton
        val goSyllabus = itemView.syllabusButton

        val context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.course_list,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.courseID.text = list[position].course_id.toString().replace("-",".")
        holder.courseName.text = list[position].course_name.toString()
        holder.instructor.text = list[position].instructor.toString()
        holder.days.text = "Days: ${ list[position].days.toString() }"
        holder.hours.text = "Hours: ${ list[position].hours.toString() }"
        holder.credit.text = "Credit: ${ list[position].credit.toString()}"
        holder.ects.text = "ECTS: ${ list[position].ects.toString() }"

        holder.addCourse.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra("Course",list[position])
            activity.setResult(Activity.RESULT_OK,intent)
            activity.finish()
        })
        holder.goSyllabus.setOnClickListener(View.OnClickListener {
            if (list[position].syllabus != "_"){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(list[position].syllabus.toString()))
                activity.startActivity(browserIntent)
                activity.finish()
            }
            else {
                // Fix Toast in recycler view
                Toast.makeText(holder.context,"No Syllabus Found", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}