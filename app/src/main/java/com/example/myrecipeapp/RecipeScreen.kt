package com.example.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Category) -> Unit
) {
    // 데이터 받아오기
    // viewModel(): 이는 Android KTX(Android Kotlin Extensions)에서 제공하는 확장 함수
    // 이 함수는 ViewModelProvider를 사용하여 ViewModel 인스턴스를 생성하거나 가져오는 역할
    // viewModel() 함수는 현재 액티비티나 프래그먼트의 범위 내에서 ViewModel을 가져오기 위한 간단한 방법을 제공
    val recipeViewModel: MainViewModel = viewModel()
    // MainViewModel의 인스턴스를 생성하거나 가져와서 recipeViewModel 변수에 할당하는 코드
    // 이렇게 하면 해당 ViewModel의 인스턴스가 현재 액티비티나 프래그먼트와 연결되며, 데이터를 관리하고 UI에 제공

    val viewState by recipeViewModel.categoriesState  // 이 상태가 변경되면 UI도 업데이트된다!

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
        // loading = true 일 때 보여줄 화면
            viewState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }
        // 에러가 발생할 때 보여줄 화면
            viewState.error != null -> {
                Text(text = "ERROR OCCURED")
            }
        // 로딩중도 아니고 에러도 아닐 때 보여줄 화면 -> 즉 우리가 보여줄 화면!
            else -> {
                CategoryScreen(categories = viewState.list, navigateToDetail)  // navigateToDetail 은 여기서 실행할 것이 아니므로 그냥 전달만
            }
        }
    }
}

// 전체 리스트 UI
@Composable
fun CategoryScreen(
    categories: List<Category>,
    navigateToDetail: (Category) -> Unit  // 네비게이션 책임을 상위로 넘기기
) {
    LazyVerticalGrid(
        GridCells.Fixed(2),  // 열 개수
        modifier = Modifier.fillMaxSize()) {
        // content
        items(categories) {
                category -> CategoryItem(category = category, navigateToDetail)  // CategoryItem : 각 항목을 표현하는 Composable
            // 그리드에 표시할 각 항목을 정의하는 블록
        }
    }
}

// 리스트 아이템 UI
@Composable
fun CategoryItem(
    category: Category,
    navigateToDetail: (Category) -> Unit  // 네비게이션
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { // 클릭 속성이 여기에 존재함
                navigateToDetail(category)  // 실행은 여기서!!
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)  // 가로 세로 비
        )

        Text(
            text = category.strCategory,
            color = Color.Blue,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}