package com.teixeirarios.mad.lib.components.home

import androidx.compose.runtime.Composable
import com.teixeirarios.mad.lib.components.shared.CarouselSlider
import com.teixeirarios.mad.lib.domain.entities.stage.StageModel

@Composable
fun StageSelector(
    stageList: List<StageModel>,
    onButtonClick: (String) -> Unit
){
    val sliderList = stageList.map { it.id }
    CarouselSlider(sliderList, onButtonClick)
}