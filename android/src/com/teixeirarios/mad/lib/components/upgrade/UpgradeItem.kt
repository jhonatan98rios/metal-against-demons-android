package com.teixeirarios.mad.lib.components.upgrade


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UpgradeItem (title: String, description: String, value: String, runnable: () -> Unit) {

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Column () {
            Text(text = title, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.width(144.dp)
            )
        }

        Row (
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .width(80.dp)
                .clickable { runnable() }
        ) {

            Text(
                text = value,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 8.dp, top = 4.dp)
            )

            Text(
                text = "+",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp
            )
        }
    }
}