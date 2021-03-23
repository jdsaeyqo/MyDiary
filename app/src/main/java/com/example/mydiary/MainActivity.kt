package com.example.mydiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var changeMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPickerSetValue()

        // OpenButton 클릭 시
        openBtn.setOnClickListener {

            if(changeMode){
                Toast.makeText(this,"비밀번호 변경 중입니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences("password",Context.MODE_PRIVATE)

            val passwordFromUser = "${numPicker1.value}${numPicker2.value}${numPicker3.value}"

            //패스워드 일치 시
            if(passwordPreferences.getString("password","000").equals(passwordFromUser)){
                startActivity(Intent(this,DiaryActivity::class.java))
            //패스워드 불일치 시
            }else{
                errorPopup()

            }

        }

        // ChangeButton 클릭 시
        changeBtn.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password",Context.MODE_PRIVATE)
            val passwordFromUser = "${numPicker1.value}${numPicker2.value}${numPicker3.value}"


            if(changeMode){

               passwordPreferences.edit {

                   putString("password", passwordFromUser)

                   commit()//저장 할 때까지 UI 멈춤

               }

                Toast.makeText(this,"비밀번호가 변경되었습니다",Toast.LENGTH_SHORT).show()

                changeMode = false
                changeBtn.setBackgroundColor(Color.BLACK)


            }else{
                //비밀번호 일치 시 changeMode  활성화, 불일치 시 비활성화


                if(passwordPreferences.getString("password","000").equals(passwordFromUser)){

                    changeMode = true
                    Toast.makeText(this,"변경할 패스워드를 입력해주세요" , Toast.LENGTH_SHORT).show()

                    changeBtn.setBackgroundColor(Color.RED)


                    //패스워드 불일치 시
                }else{
                    errorPopup()

                }

            }

        }

    }

    private fun errorPopup() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("패스워드가 잘못되었습니다")
            .setPositiveButton("확인"){ _, _ -> }

            .create()
            .show()
    }

    private fun numberPickerSetValue() {
        numPicker1.minValue = 0
        numPicker1.maxValue = 9
        numPicker2.minValue = 0
        numPicker2.maxValue = 9
        numPicker3.minValue = 0
        numPicker3.maxValue = 9
    }
}