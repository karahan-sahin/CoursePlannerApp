package com.example.courseplanner

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.course_list.view.*
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.net.URL

class DataAdapter(var list: ArrayList<DatabaseModel>): RecyclerView.Adapter<DataAdapter.ViewHolder>() {
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
        holder.courseID.text = list[position].courseID
        holder.courseName.text = list[position].courseName
        holder.instructor.text = list[position].instructor
        holder.days.text = "Days: ${ list[position].days }"
        holder.hours.text = "Hours: ${ list[position].hours }"
        holder.credit.text = "Credit: ${ list[position].credit }"
        holder.ects.text = "ECTS: ${ list[position].ects }"

        holder.addCourse.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            val bundle = Bundle()
            bundle.putSerializable("course", list[position])

            intent.putExtra("course", bundle)
            intent.setResult(RESULT_OK,intent)


        })
        holder.goSyllabus.setOnClickListener(View.OnClickListener {
            if (list[position].syllabus != "_"){
                var request = DownloadManager.Request(Uri.parse(list[position].syllabus))
                request.setTitle("${list[position].courseID} Syllabus")
                request.setDescription("${list[position].courseID} Syllabus Downloading")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                request.setAllowedOverMetered(true)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,list[position].courseID)

                // Fix context
                var dm: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)

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