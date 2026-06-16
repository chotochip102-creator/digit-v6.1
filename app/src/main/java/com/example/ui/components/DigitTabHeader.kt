package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

@Composable
fun DigitTabHeader(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val isDark = isSystemInDarkTheme()
    val pillBg = if (isDark) Color(0xFF282A2D) else Color.White
    val brandColor = if (isDark) Color(0xFF787DB9) else Color(0xFF54578c)
    val dividerColor = if (isDark) Color(0xFF4A4C50) else Color(0xFFE0E0E0)
    val statusBarBg = if (isDark) Color.Black else Color.White

    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(statusBarBg)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(percent = 50),
                color = pillBg,
                shadowElevation = if (isDark) 0.dp else 4.dp,
                border = if (isDark) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3C3F45)) else null,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Leading: Logo + Wordmark
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "Digit Logo",
                                modifier = Modifier.fillMaxSize(1.5f)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Digit",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = brandColor,
                            letterSpacing = (-0.5).sp
                        )
                    }
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Divider
                        Box(
                            modifier = Modifier
                                .height(24.dp)
                                .width(1.dp)
                                .background(dividerColor)
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        // Trailing section
                        actions()
                    }
                }
            }
        }
    }
}
