package com.example.demo_memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo_memo.databinding.ActivityMemoBinding

import androidx.activity.viewModels
import com.example.demo_memo.data.MemoDataListViewModel


class MemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemoBinding
    //private val memoDataListViewModel : MemoDataListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이미 저장된 메모 가져오기
        if(intent.hasExtra("savedTitleText")){
            binding.InputTitle.setText(intent.getStringExtra("savedTitleText"))
            binding.InputText.setText(intent.getStringExtra("savedMemoText"))
        }

        // 편집된 내용 MainActivity로 보내기
        binding.addToRecyclerView.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                putExtra("editedTitleText", binding.InputTitle.text.toString())
                putExtra("editedMemoText", binding.InputText.text.toString())
            }.run { setResult(RESULT_OK, this) }

            if (!isFinishing) finish()
        }
    }
}