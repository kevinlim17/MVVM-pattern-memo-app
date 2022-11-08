package com.example.demo_memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_memo.adapter.CustomMemoAdapter
import com.example.demo_memo.adapter.VerticalItemDecorator
import com.example.demo_memo.data.MemoData
import com.example.demo_memo.data.MemoDataListViewModel
import com.example.demo_memo.data.MemoDataListViewModelFactory
import com.example.demo_memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var getEditedMemo : ActivityResultLauncher<Intent>
    private lateinit var recyclerAdapter: CustomMemoAdapter

    private lateinit var memoDataListViewModel : MemoDataListViewModel

    private var memoState : Int = 100 /** 새로운 메모 생성 시 : 1, 메모 수정 시 : 0, 메모 삭제 시 -1**/
    private var currentMemoPosition : Int = -1 /**  수정된 메모의 RecyclerView에서의 위치 **/

    private var receivedTitleText : String? = null
    private var receivedMemoText : String? = null

    override fun onRestart() {
        super.onRestart()

        // 메모 최초 생성 시 MemoDataResource에 메모 추가
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        memoDataListViewModel = ViewModelProvider(this, MemoDataListViewModelFactory(this))[MemoDataListViewModel::class.java]

        /** DataList 생성 (Dummy Data)
        val dataArray = mutableListOf<MemoData>()
        for (i in 1..20){
            dataArray.add(MemoData(memoTitle = i.toString(), memoText = "Hello"))
        }
        **/

        recyclerAdapter = CustomMemoAdapter(emptyList<MemoData>().toTypedArray())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = recyclerAdapter
            addItemDecoration(VerticalItemDecorator(5))
        }

        memoDataListViewModel.memoLiveData.observe(this) {
            (binding.recyclerView.adapter as CustomMemoAdapter).setMemoData(
                it,
                memoState,
                currentMemoPosition
            )
        }

        /** 새로 생긴 메모 또는 편집된 메모 내용 가져오기**/
        getEditedMemo = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                receivedTitleText = it.data?.getStringExtra("editedTitleText")
                receivedMemoText = it.data?.getStringExtra("editedMemoText")
            }
            when(memoState){
                1 -> memoDataListViewModel.insertMemo(receivedTitleText, receivedMemoText)
                0 -> memoDataListViewModel.editMemo(currentMemoPosition, receivedTitleText, receivedMemoText)
            }
        }

        /** 최초 메모 생성 시 보내는 Intent**/
        binding.addNote.setOnClickListener {
            memoState = 1
            currentMemoPosition = 0
            Intent(this, MemoActivity::class.java).apply {
                putExtra("newMemoAdded", 1)
            }.run{ getEditedMemo.launch(this) }
        }

        /** 메모 수정 시 보내는 Intent **/
        recyclerAdapter.setOnItemClickListener(object : CustomMemoAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: MemoData, position: Int) {
                memoState = 0
                currentMemoPosition = position
                Intent(this@MainActivity, MemoActivity::class.java).apply {
                    putExtra("savedTitleText", data.memoTitle)
                    putExtra("savedMemoText", data.memoText)
                }.run { getEditedMemo.launch(this) }
            }
        })

        /** 메모 삭제 **/
        recyclerAdapter.setOnItemLongClickListener(object : CustomMemoAdapter.OnItemLongClickListener {
            override fun onItemLongClick(v: View, data: MemoData, position: Int): Boolean {
                memoDataListViewModel.deleteMemo(data)
                memoState = -1
                currentMemoPosition = position
                return true
            }
        })


    }
}