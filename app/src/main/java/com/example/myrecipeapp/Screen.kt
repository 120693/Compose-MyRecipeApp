package com.example.myrecipeapp

sealed class Screen(val route:String) { // 문자열 경로를 넘긴다
    // 레시피 화면과 디테일 화면을 한 곳에 모아 놓기 위하여
    // sealed : 서브 클래스 타입에 맞는 타입이 있다는 것을 알고있을 때 사용 <-------- 뭔말?
    // 런타임보다 컴파일 시간에 일치하도록 제한함으로써 안전하게 함
    // 다양한 경로가 여기에 있을 경우 주로 사용

    // 화면마다 변수를 만들기 <------- 강추!
    // 여기서는 sealed class Screen을 이용해서 개체로 만들기
    object RecipeScreen: Screen("recipescreen")
    object CategoryDetailScreen: Screen("detailscreen")
}