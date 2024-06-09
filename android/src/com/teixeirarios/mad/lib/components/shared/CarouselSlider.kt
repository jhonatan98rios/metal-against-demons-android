package com.teixeirarios.mad.lib.components.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.teixeirarios.mad.lib.components.home.StageModel
import com.teixeirarios.mad.lib.components.home.StageSelectorCard

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselSlider(
    pagerState: PagerState,
    stageList: List<StageModel>,
    onButtonClick: (Int) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            count = stageList.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 65.dp),
            itemSpacing = 24.dp,
            modifier = Modifier.height(250.dp)
        ) {
            index ->

            StageSelectorCard(
                stageModel = stageList[index],
                onButtonClick = onButtonClick
            )
        }
    }
}