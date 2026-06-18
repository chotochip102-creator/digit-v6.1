package com.example.ui.role
import com.example.ui.components.SmartText

import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

object RoleColors {
    val BrandIndigoLight = Color(0xFF54578c)
    val BrandIndigoDark = Color(0xFF787DB9)
    val BrandTealLight = Color(0xFF1a5f7a)
    val BrandTealDark = Color(0xFF288BA6)
    val SurfaceStartLight = Color(0xFFF7F9FB)
    val SurfaceStartDark = Color(0xFF121316)
    val SurfaceEndLight = Color(0xFFE8EEF6)
    val SurfaceEndDark = Color(0xFF1E2024)
    val TextHeadlineLight = Color(0xFF1D1B20)
    val TextHeadlineDark = Color(0xFFEAEAEA)
    val TextMutedLight = Color(0xFF49454F)
    val TextMutedDark = Color(0xFFB0B0B0)
    val CardBackgroundLight = Color.White
    val CardBackgroundDark = Color(0xFF2B2C30)
}

@Composable
fun RoleSelectionScreen(
    onNavigateToStudent: () -> Unit,
    onNavigateToTeacher: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val surfaceStart = if (isDark) RoleColors.SurfaceStartDark else RoleColors.SurfaceStartLight
    val surfaceEnd = if (isDark) RoleColors.SurfaceEndDark else RoleColors.SurfaceEndLight
    val textHeadline = if (isDark) RoleColors.TextHeadlineDark else RoleColors.TextHeadlineLight
    val textMuted = if (isDark) RoleColors.TextMutedDark else RoleColors.TextMutedLight
    val brandIndigo = if (isDark) RoleColors.BrandIndigoDark else RoleColors.BrandIndigoLight
    val brandTeal = if (isDark) RoleColors.BrandTealDark else RoleColors.BrandTealLight

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(surfaceStart, surfaceEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(top = 16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Digit Logo",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    SmartText( // FIXED
                        text = "Digit",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = brandIndigo,
                        letterSpacing = (-0.5).sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.7f))

            // Welcome Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SmartText( // FIXED
                    "স্বাগতম!",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = textHeadline
                )
                Spacer(modifier = Modifier.height(8.dp))
                SmartText( // FIXED
                    "এগিয়ে যেতে আপনার রোল নির্বাচন করুন",
                    fontSize = 18.sp,
                    color = textMuted
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Role selection cards
            RoleCard(
                title = "শিক্ষার্থীর লগইন",
                subtitle = "আপনার ড্যাশবোর্ড অ্যাক্সেস করুন",
                iconContainerColor = brandIndigo,
                iconShape = CircleShape,
                arrowColor = brandIndigo,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.School,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                },
                onClick = onNavigateToStudent
            )
            
            Spacer(modifier = Modifier.height(20.dp))

            RoleCard(
                title = "শিক্ষকের লগইন",
                subtitle = "ক্লাস এবং গ্রেড পরিচালনা করুন",
                iconContainerColor = brandTeal,
                iconShape = RoundedCornerShape(20.dp),
                arrowColor = brandTeal,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Badge, // Substitute for badge
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                },
                onClick = onNavigateToTeacher
            )

            Spacer(modifier = Modifier.weight(1.3f))
        }
    }
}

@Composable
fun borderStroke() = androidx.compose.foundation.BorderStroke(1.dp, Color.Black.copy(alpha = 0.05f))

@Composable
fun RoleCard(
    title: String,
    subtitle: String,
    iconContainerColor: Color,
    iconShape: androidx.compose.ui.graphics.Shape,
    arrowColor: Color,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(targetValue = if (isPressed) 0.95f else 1.0f, label = "scale")

    val isDark = isSystemInDarkTheme()
    val cardBackground = if (isDark) RoleColors.CardBackgroundDark else RoleColors.CardBackgroundLight
    val textHeadline = if (isDark) RoleColors.TextHeadlineDark else RoleColors.TextHeadlineLight
    val textMuted = if (isDark) RoleColors.TextMutedDark else RoleColors.TextMutedLight
    val borderColor = if (isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.02f)
    val shadowColor = if (isDark) Color.Black.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.05f)

    Surface(
        shape = RoundedCornerShape(40.dp),
        color = cardBackground,
        shadowElevation = if (isDark) 0.dp else 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.material3.ripple()
            ) { onClick() },
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon container
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(iconShape)
                    .background(iconContainerColor),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            
            Spacer(modifier = Modifier.width(20.dp))
            
            // Text Content
            Column(modifier = Modifier.weight(1f)) {
                SmartText( // FIXED
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textHeadline
                )
                Spacer(modifier = Modifier.height(4.dp))
                SmartText( // FIXED
                    text = subtitle,
                    fontSize = 14.sp,
                    color = textMuted,
                    lineHeight = 18.sp
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Trailing arrow button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFFE2E8F0), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Proceed",
                    tint = arrowColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
