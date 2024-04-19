package com.example.myrecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(navController: NavHostController) {
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState

    // 사용자가 클릭하는 화면이 어떤 것인지 알아야 한다
    // viewState를 이용해서 다른 화면으로 이동 + 레시피 화면도 보여줄 것임
    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route) {
        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(viewState = viewState, navigateToDetail = {
                // 레시피 화면에서 디테일 화면으로 데이터를 전달하는 역할
                // 모든 흐름을 currentBackStackEntry 에 저장
                // savedStateHandle로 다음 화면으로 전달돼야 할 데이터를 저장하고 회수하는 방법을 (키, 값) 형태로 제공
                // 그런데 여기서 전달하는 것은 Category 임 --> 개체를 전달할 때는 serialized 해서 보내야 한다, 그래야 통과된다.
                navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                // 키를 사용해서 디테일 화면에서 사용할 데이터를 가져오면 됨
                navController.navigate(Screen.CategoryDetailScreen.route)
            })
        }
        composable(route = Screen.CategoryDetailScreen.route) {
            // 다른 화면으로 이동을 했으므로 이제 previousBackStackEntry 가 생긴다. (내가 두번째 이므로)
            // category에 previousBackStackEntry 를 통해서 savedStateHandle에서 가져온 데이터를 넣는다! --> 즉 위에서의 it 을 넣는다!
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat") ?: Category("","","","")

            // 화면으로 데이터를 보내주기
            CategoryDetailScreen(category = category)
        }
    }
}