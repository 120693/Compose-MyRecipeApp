package com.example.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ViewModel은 안드로이드 앱의 UI 관련 데이터를 관리하고 화면 회전 및 구성 변경과 같은 구성 변경 시에 데이터를 유지하기 위한 클래스
class MainViewModel: ViewModel() {

    private val _categoriesState = mutableStateOf(RecipeState())
    // mutableStateOf : 모든 타입 가능!, 변경 가능!
    // RecipeState 도 클래스임

    val categoriesState: State<RecipeState> = _categoriesState
    // State : 변경 불가능, 읽기만 가능

    init {
        // 뷰모델을 사용하면 바로 데이터를 불러올 수 있도록
        fetchCategories()
    }

    private fun fetchCategories() {
        // suspend fun 함수가 백그라운드에서 처리되도록 하기 위해서 코루틴 안에서 실행
        viewModelScope.launch {
            // 인터넷이 끊기거나 했을 때도 앱이 죽지 않도록 try-catch 사용
            try {
                // api 값 가져오기
                val response = recipeService.getCagegories()

                // 가져온 값 넣어주기
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    list = response.categories,
                    error = null
                )
            } catch (e: Exception) {
                // 안전하게 넣기....
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error fetching Categories ${e.message}"
                )
            }
        }

    }

    // Api의 상태를 관리
    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}