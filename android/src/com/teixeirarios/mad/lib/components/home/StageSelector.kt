package com.teixeirarios.mad.lib.components.home

import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.teixeirarios.mad.lib.components.shared.CarouselSlider


@OptIn(ExperimentalPagerApi::class)
@Composable
fun StageSelector(
    stageList: List<StageModel>,
    onButtonClick: (Int) -> Unit
){
    val pagerState = rememberPagerState(initialPage = 0)
    CarouselSlider(pagerState, stageList, onButtonClick)
}