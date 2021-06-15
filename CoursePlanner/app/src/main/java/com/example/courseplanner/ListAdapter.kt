package com.example.courseplanner

import android.app.Activity
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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.added_course.view.*
import kotlinx.android.synthetic.main.course_list.view.*
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.net.URL
import java.util.jar.Manifest

class ListAdapter(var list: ArrayList<DatabaseModel>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView){

        var courseID = itemView.addedCourseRecyclerText
        var deleteButton = itemView.deleteCourseButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ListAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.added_course, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.courseID.text = list[position].course_id.toString().replace("-",".")
        holder.deleteButton.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,list.size)


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}