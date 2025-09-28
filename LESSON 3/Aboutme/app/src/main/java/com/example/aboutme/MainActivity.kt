package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

//        val on_done:Button = findViewById(R.id.done_button)
//        on_done.setOnClickListener { addNickname(it) }
        binding.doneButton.setOnClickListener{
            addNickname(it)
        }

    }

    private fun addNickname(view: View){
////        val text1:EditText = findViewById(R.id.editText)
////        val text2:TextView = findViewById(R.id.text_after_done)
////        text2.text=text1.text
//        binding.textAfterDone.text=binding.editText.text
////        text1.visibility = View.GONE
//        binding.editText.visibility=View.GONE
////        view.visibility=View.GONE
//        binding.doneButton.visibility=View.GONE
////        text2.visibility = View.VISIBLE
//        binding.textAfterDone.visibility=View.VISIBLE
        binding.apply{
            textAfterDone.text=editText.text
            invalidateAll()
            editText.visibility=View.GONE
            doneButton.visibility=View.GONE
            textAfterDone.visibility=View.VISIBLE
        }

        // Hide the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }
}
