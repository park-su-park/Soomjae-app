package com.parksupark.soomjae.features.post.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface PostNavigator : SoomjaeNavigator

private class SoomjaePostNavigator(override val navController: NavHostController) : PostNavigator {
    override fun navigateBack() {
        overridden()
    }
}

fun soomjaePostNavigator(navController: NavHostController): PostNavigator = SoomjaePostNavigator(navController)
