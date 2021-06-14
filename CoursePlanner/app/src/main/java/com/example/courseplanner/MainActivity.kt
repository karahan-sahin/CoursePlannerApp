package com.example.courseplanner

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.google.android.gms.common.api.Response
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    // 2. Hold list of current course
    val current_courses = ArrayList<DatabaseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    // TO-DO LIST


    // 1. Go to search intent function

    // 1. Go to search intent function
    // 1. Go to search intent function

    fun search_intent(view: View) {

        val intent = Intent(this@MainActivity,SearchCourse::class.java)
        startActivityForResult(intent,1)
        // send current course to search
        //
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                var result = data!!.getBundleExtra("course")
                
            }
        }


        super.onActivityResult(requestCode, resultCode, data)
    }


}