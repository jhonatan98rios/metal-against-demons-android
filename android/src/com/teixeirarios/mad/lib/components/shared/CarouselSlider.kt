package com.teixeirarios.mad.lib.components.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.teixeirarios.mad.lib.components.home.StageSelectorCard

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselSlider(
    sliderList: List<String>,
    onButtonClick: (String) -> Unit
) {

    val pagerState = rememberPagerState(initialPage = 0)

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            count = sliderList.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 65.dp),
            itemSpacing = 24.dp,
            modifier = Modifier.height(150.dp)
        ) {
            index -> StageSelectorCard(stageId = sliderList[index], onButtonClick)
        }
    }
}