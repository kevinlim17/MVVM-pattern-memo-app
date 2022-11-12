# MVVM_Pattern_MemoApp
MVVM 아키텍처 패턴을 적용한 안드로이드 간이 메모 애플리케이션 (Kotlin)

##Overview
<img width="1013" alt="image" src="https://user-images.githubusercontent.com/86971052/201022716-e9ffb735-cbc5-47e1-9b69-5c7380f14f41.png">

###**애플리케이션 구동 과정**
>**1. MainActivity에서 CustomMemoAdapter(ListAdapter) Instance 생성**
> 
>**2. MainActivity에서 MemoActivity로 이동 (registerForActivityResult.launch)**
> 
>**3. MemoActivity에서 작성한 메모 내용 Intent로 MainActivity로 전달**
>
>**4. MemoDataListViewModel.addMemo() / EditMemo()를 통해 LiveData 접근**
>
>**5. MemoDataSource Class 메서드를 통해 LiveData<Array<MemoData>> 수정**
> 
>**6. MemoDataSource.getMemoList()를 통해 LiveData 가져오기**
> 
>**7. ViewModel의 LiveData를 Observe한 결과를 Adapter에 적용**
> 
>**8. Update된 Adapter 정보를 MainActivity의 RecyclerView에 적용**
> 
>**Special. 메모 아이템을 길게 누르면(setOnItemLongClickListener) 메모 데이터 삭제**

##🟡 DataSource : Model
>
> ~~~kotlin
> class MemoDataSource {
>       private val initMemoDataArray = memoDataArrayList()
>       private val memoLiveData = MutableLiveData(initMemoDataArray)
> 
>       fun addMemo(newMemo : MemoData) {}
>       fun editMemo(editPos : Int) {}
>       fun deleteMemo(deleted : MemoData) {}
>       fun findIdByPosition(pos : Int) : Int {}
>       
>       fun getMemoList() : LiveData<Array<MemoData>> = memoLiveData
> }
> ~~~

##🟣 ViewModel
>
> ~~~kotlin
> class MemoDataListViewModel(private val dataSource: MemoDataSource) : ViewModel(){
>       val memoLiveData = dataSource.getMemoList()
> 
>       fun insertMemo(memoId : Int?, memoTitle : String?, memoDescription: String?) {}
>       fun editMemo(position : Int, memoTitle: String?, memoDescription: String?) {}
>       fun deleteMemo(memoData : MemoData) {}
> }
> ~~~

##🔴 Activities : View
###🗄 MainActivity.kt
>
> ~~~kotlin
> class MainActivity : AppCompatActivity() {
>       override fun onCreate(savedInstanceState: Bundle?) {
>           /** Initialize Adapter **/
>           recyclerAdapter = CustomMemoAdapter(emptyList<MemoData>().toTypedArray())
>           binding.recyclerView.apply {
>               layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
>               adapter = recyclerAdapter       
>               addItemDecoration(VerticalItemDecorator(5))
>           }
>           /** Observe LiveData and Update Adapter **/
>           memoDataListViewModel.memoLiveData.observe(this) {
>               (binding.recyclerView.adapter as CustomMemoAdapter).updateMemoData(
>                   it,
>                   memoState,
>                   currentMemoPosition
>               )
>           }
>           /** Access LiveData via MemoDataListViewModel **/
>           getEditedMemo = registerForActivityResult(
>           ActivityResultContracts.StartActivityForResult()) {
>               if (it.resultCode == RESULT_OK) {
>                    receivedTitleText = it.data?.getStringExtra("editedTitleText")
>                    receivedMemoText = it.data?.getStringExtra("editedMemoText")
>                    when(memoState){
>                        1 -> memoDataListViewModel.insertMemo(memoAccessId, receivedTitleText, receivedMemoText)
>                        0 -> memoDataListViewModel.editMemo(currentMemoPosition, receivedTitleText, receivedMemoText)
>                   }
>           }
>           /** Delete MemoData **/
>           recyclerAdapter.setOnItemLongClickListener(object : CustomMemoAdapter.OnItemLongClickListener {
>                   override fun onItemLongClick(v: View, data: MemoData, position: Int): Boolean {
>                           memoDataListViewModel.deleteMemo(data)
>                           memoState = -1
>                           currentMemoPosition = position
>                           return true
>                   }
>           })
>       }
>   }
> }
> ~~~
> 

##🟢 Adapter
> **Update Soon**

