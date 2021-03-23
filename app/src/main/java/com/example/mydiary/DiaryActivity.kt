package com.example.mydiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_diary.*

class DiaryActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)


        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        editDiary.setText(detailPreferences.getString("detail", ""))

        val r = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", editDiary.text.toString())

            }

        }

        editDiary.addTextChangedListener {
            //이전에 runnable이 있을 시 지움
            handler.removeCallbacks(r)
            //0.5초 뒤에 러너블 실행
            handler.postDelayed(r,500)

        }


    }
}