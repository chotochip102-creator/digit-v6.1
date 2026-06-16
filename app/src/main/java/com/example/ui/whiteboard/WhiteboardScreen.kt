package com.example.ui.whiteboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Redo
import androidx.compose.material.icons.automirrored.outlined.Undo
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.ripple
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlinx.coroutines.launch

data class StrokePoint(val offset: Offset, val pressure: Float)

data class DrawnStroke(
    val points: List<StrokePoint>,
    val color: Color,
    val baseWidth: Float
)

val PrimaryIndigo = Color(0xFF4F46E5)
val SecondaryEmerald = Color(0xFF10B981)
val SurfaceGrey = Color(0xFFF8F9FA)
val SurfaceContainerWhite = Color(0xFFFFFFFF)
val GridLineColor = Color(0xFFD9DADB)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WhiteboardScreen(
    onNavigateBack: () -> Unit = {}
) {
    var paths by remember { mutableStateOf(listOf<DrawnStroke>()) }
    var undonePaths by remember { mutableStateOf(listOf<DrawnStroke>()) }
    
    var currentStroke by remember { mutableStateOf<List<StrokePoint>?>(null) }
    var updateTrigger by remember { mutableStateOf(0) }
    
    var selectedColor by remember { mutableStateOf(PrimaryIndigo) }
    var selectedStrokeWidth by remember { mutableStateOf(8f) }
    var activeTool by remember { mutableStateOf("Pen") } // "Select", "Pen", "Eraser"
    var showMobileMenu by remember { mutableStateOf(false) }
    var showPencilSettings by remember { mutableStateOf(false) }
    var showCalculator by remember { mutableStateOf(false) }
    var calcOffset by remember { mutableStateOf(Offset(200f, 200f)) }
    var selectedPenType by remember { mutableStateOf("Pen") }
    val coroutineScope = rememberCoroutineScope()

    val scaleAnim = remember { androidx.compose.animation.core.Animatable(1f) }
    val offsetXAnim = remember { androidx.compose.animation.core.Animatable(0f) }
    val offsetYAnim = remember { androidx.compose.animation.core.Animatable(0f) }

    val scale = scaleAnim.value
    val offsetX = offsetXAnim.value
    val offsetY = offsetYAnim.value

    val colors = listOf(PrimaryIndigo, Color(0xFF0F766E), Color(0xFF78350F), Color.Black)
    val eraserColor = SurfaceGrey
    
    val canvasModifier = Modifier.fillMaxSize().graphicsLayer {
        scaleX = scale
        scaleY = scale
        translationX = offsetX
        translationY = offsetY
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val isMobile = maxWidth < 600.dp

        // Drawing Area
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(activeTool) {
                    if (activeTool == "Select") {
                        detectTransformGestures { centroid, pan, zoom, _ ->
                            coroutineScope.launch {
                                val newScale = (scaleAnim.value * zoom).coerceIn(0.5f, 3f)
                                val scaleRatio = newScale / scaleAnim.value
                                
                                val newOffsetX = centroid.x - (centroid.x - (offsetXAnim.value + pan.x)) * scaleRatio
                                val newOffsetY = centroid.y - (centroid.y - (offsetYAnim.value + pan.y)) * scaleRatio
                                
                                scaleAnim.snapTo(newScale)
                                offsetXAnim.snapTo(newOffsetX)
                                offsetYAnim.snapTo(newOffsetY)
                            }
                        }
                    } else if (activeTool in listOf("Pen", "Brush", "Highlighter", "Pencil", "Eraser")) {
                        awaitEachGesture {
                            val down = awaitFirstDown()

                            val mappedX = (down.position.x - offsetXAnim.value) / scaleAnim.value
                            val mappedY = (down.position.y - offsetYAnim.value) / scaleAnim.value
                            var stroke = listOf(StrokePoint(Offset(mappedX, mappedY), 1f))
                            currentStroke = stroke

                            do {
                                val event = awaitPointerEvent()
                                val ptr = event.changes.firstOrNull { it.id == down.id }
                                if (ptr != null && ptr.pressed) {
                                    val newPoints = mutableListOf<StrokePoint>()
                                    val addPoint = { pos: Offset, hwPressure: Float ->
                                        val pmX = (pos.x - offsetXAnim.value) / scaleAnim.value
                                        val pmY = (pos.y - offsetYAnim.value) / scaleAnim.value
                                        val pmO = Offset(pmX, pmY)

                                        val lastPoint = if (newPoints.isNotEmpty()) newPoints.last() else stroke.lastOrNull()
                                        val distance = if (lastPoint != null) {
                                            val dx = pmX - lastPoint.offset.x
                                            val dy = pmY - lastPoint.offset.y
                                            kotlin.math.sqrt(dx * dx + dy * dy)
                                        } else 0f

                                        val speed = distance.coerceIn(0f, 50f)
                                        val speedFactor = 1f - (speed / 50f)

                                        val hp = if (hwPressure.isNaN() || hwPressure == 0f) 1f else hwPressure
                                        val targetPressure = if (activeTool == "Eraser") 1f else (hp * 0.4f + speedFactor * 0.6f).coerceIn(0.2f, 1.5f)

                                        newPoints.add(StrokePoint(pmO, targetPressure))
                                    }

                                    ptr.consume()
                                    // Historical values might be experimental but they exist. Handle properties gently.
                                    ptr.historical.forEach { h -> 
                                        addPoint(h.position, 1f) 
                                    }
                                    addPoint(ptr.position, 1f)

                                    stroke = stroke + newPoints
                                    currentStroke = stroke
                                    updateTrigger++
                                }
                            } while (event.changes.any { it.pressed })

                            currentStroke?.let { pts ->
                                val finalColor = if (activeTool == "Eraser") eraserColor else if (activeTool == "Highlighter") selectedColor.copy(alpha = 0.4f) else selectedColor
                                val finalWidth = if (activeTool == "Eraser") 40f else if (activeTool == "Highlighter") selectedStrokeWidth * 1.5f else selectedStrokeWidth
                                paths = paths + DrawnStroke(pts, finalColor, finalWidth)
                                undonePaths = emptyList()
                            }
                            currentStroke = null
                        }
                    }
                }
                .graphicsLayer {
                    transformOrigin = androidx.compose.ui.graphics.TransformOrigin(0f, 0f)
                    scaleX = scale
                    scaleY = scale
                    translationX = offsetX
                    translationY = offsetY
                }
        ) {
            val dummy = updateTrigger
            val drawStrokeSegments = { strokePoints: List<StrokePoint>, color: Color, baseWidth: Float ->
                if (strokePoints.isNotEmpty()) {
                    if (color.alpha < 1f || activeTool == "Highlighter") {
                    if (strokePoints.size == 1) {
                        drawCircle(color, radius = baseWidth / 2f, center = strokePoints.first().offset)
                    } else {
                        val path = Path().apply {
                            moveTo(strokePoints[0].offset.x, strokePoints[0].offset.y)
                            for (i in 1 until strokePoints.size - 1) {
                                val p0 = strokePoints[i - 1]
                                val p1 = strokePoints[i]
                                val midX = (p0.offset.x + p1.offset.x) / 2f
                                val midY = (p0.offset.y + p1.offset.y) / 2f
                                quadraticTo(p0.offset.x, p0.offset.y, midX, midY)
                            }
                            val last = strokePoints.last()
                            lineTo(last.offset.x, last.offset.y)
                        }
                        drawPath(path, color, style = Stroke(width = baseWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
                    }
                } else {
                    if (strokePoints.size == 1) {
                        drawCircle(color, radius = baseWidth * strokePoints.first().pressure / 2f, center = strokePoints.first().offset)
                    } else if (strokePoints.size == 2) {
                        val p1 = strokePoints[0]
                        val p2 = strokePoints[1]
                        val dist = kotlin.math.sqrt(
                            (p2.offset.x - p1.offset.x) * (p2.offset.x - p1.offset.x) + 
                            (p2.offset.y - p1.offset.y) * (p2.offset.y - p1.offset.y)
                        )
                        val steps = kotlin.math.max(1, (dist * 2f).toInt())
                        for (s in 0..steps) {
                            val t = s.toFloat() / steps
                            val x = p1.offset.x + (p2.offset.x - p1.offset.x) * t
                            val y = p1.offset.y + (p2.offset.y - p1.offset.y) * t
                            val pressure = p1.pressure + (p2.pressure - p1.pressure) * t
                            drawCircle(color, radius = baseWidth * pressure / 2f, center = Offset(x, y))
                        }
                    } else {
                        var prevMidX = strokePoints[0].offset.x
                        var prevMidY = strokePoints[0].offset.y
                        var prevPressure = strokePoints[0].pressure
                        
                        for (i in 1 until strokePoints.size) {
                            val p1 = strokePoints[i - 1]
                            val p2 = strokePoints[i]
                            val midX = (p1.offset.x + p2.offset.x) / 2f
                            val midY = (p1.offset.y + p2.offset.y) / 2f
                            val midPressure = (p1.pressure + p2.pressure) / 2f
                            
                            val dist = kotlin.math.sqrt(
                                 (midX - prevMidX) * (midX - prevMidX) + 
                                 (midY - prevMidY) * (midY - prevMidY)
                            )
                            val steps = kotlin.math.max(1, (dist * 1.5f).toInt())
                            for (s in 0..steps) {
                                val t = s.toFloat() / steps
                                val invT = 1f - t
                                val x = invT * invT * prevMidX + 2 * invT * t * p1.offset.x + t * t * midX
                                val y = invT * invT * prevMidY + 2 * invT * t * p1.offset.y + t * t * midY
                                val p = invT * invT * prevPressure + 2 * invT * t * p1.pressure + t * t * midPressure
                                drawCircle(color, radius = baseWidth * p / 2f, center = Offset(x, y))
                            }
                            prevMidX = midX
                            prevMidY = midY
                            prevPressure = midPressure
                        }
                        
                        val lastP = strokePoints.last()
                        val dist = kotlin.math.sqrt(
                             (lastP.offset.x - prevMidX) * (lastP.offset.x - prevMidX) + 
                             (lastP.offset.y - prevMidY) * (lastP.offset.y - prevMidY)
                        )
                        val steps = kotlin.math.max(1, (dist * 1.5f).toInt())
                        for (s in 0..steps) {
                            val t = s.toFloat() / steps
                            val x = prevMidX + (lastP.offset.x - prevMidX) * t
                            val y = prevMidY + (lastP.offset.y - prevMidY) * t
                            val p = prevPressure + (lastP.pressure - prevPressure) * t
                            drawCircle(color, radius = baseWidth * p / 2f, center = Offset(x, y))
                        }
                    }
                }
            }
            }
            
            paths.forEach { stroke ->
                drawStrokeSegments(stroke.points, stroke.color, stroke.baseWidth)
            }
            currentStroke?.let { pts ->
                val finalColor = if (activeTool == "Eraser") eraserColor else if (activeTool == "Highlighter") selectedColor.copy(alpha = 0.4f) else selectedColor
                val finalWidth = if (activeTool == "Eraser") 40f else if (activeTool == "Highlighter") selectedStrokeWidth * 1.5f else selectedStrokeWidth
                drawStrokeSegments(pts, finalColor, finalWidth)
            }
        }

        // Floating Interactive Card "Quadratic Theorem"
        if (!isMobile) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 120.dp, top = 80.dp)
                    .offset(y = (-60).dp)
                    .shadow(16.dp, RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(24.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.width(4.dp).height(16.dp).background(PrimaryIndigo, RoundedCornerShape(2.dp)))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("QUADRATIC THEOREM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryIndigo, letterSpacing = 1.sp)
                        Spacer(modifier = Modifier.width(48.dp))
                        Icon(Icons.Default.MoreHoriz, null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("x = (-b ± √(b² - 4ac)) / 2a", fontSize = 24.sp, fontWeight = FontWeight.Medium, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                    Spacer(modifier = Modifier.height(24.dp))
                    Row {
                        Surface(color = SecondaryEmerald.copy(alpha = 0.2f), shape = RoundedCornerShape(percent = 50)) {
                            Text("Algebra II", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF047857), modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(color = Color(0xFFD97706).copy(alpha = 0.2f), shape = RoundedCornerShape(percent = 50)) {
                            Text("Core Concept", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB45309), modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                        }
                    }
                }
            }
        }

        // --- UI OVERLAYS ---
        
        if (isMobile) {
            // MOBILE UI
            
            // Header
            Surface(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.Black)
                    }
                    Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color(0xFFE5E7EB)))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Math Lesson 3.2", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                    Spacer(modifier = Modifier.weight(1f))
                    Box {
                        IconButton(onClick = { showMobileMenu = true }) {
                            Icon(Icons.Default.Menu, "Menu", tint = Color.Black)
                        }
                        DropdownMenu(
                            expanded = showMobileMenu,
                            onDismissRequest = { showMobileMenu = false },
                            shape = RoundedCornerShape(16.dp),
                            containerColor = Color.White,
                            modifier = Modifier.width(300.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Tools", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1F2937)) // Dark Gray
                                    IconButton(
                                        onClick = { showMobileMenu = false },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Close, "Close", tint = Color.Gray, modifier = Modifier.size(20.dp))
                                    }
                                }
                                
                                val menuItems = listOf(
                                    "Save" to Icons.Default.Save,
                                    "Capture" to Icons.Default.CameraAlt,
                                    "Gallery" to Icons.Default.PhotoLibrary,
                                    "Add PDF" to Icons.Default.PictureAsPdf,
                                    "Settings" to Icons.Default.Settings
                                )
                                
                                menuItems.forEach { (text, icon) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { showMobileMenu = false }
                                            .padding(horizontal = 24.dp, vertical = 14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(icon, contentDescription = text, tint = Color(0xFF4B5563), modifier = Modifier.size(22.dp))
                                        Text(
                                            text = text, 
                                            fontWeight = FontWeight.Bold, 
                                            color = Color(0xFF374151), 
                                            fontSize = 15.sp,
                                            modifier = Modifier.weight(1f), 
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.width(22.dp)) // To completely zero-out the icon weight for dead center text
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFF3F4F6))
                                        .padding(vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Lumina Canvas v2.4", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                                }
                            }
                        }
                    }
                }
            }

            // Right-Side Controls
            Column(
                modifier = Modifier.align(Alignment.TopEnd).padding(top = 76.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = { 
                            coroutineScope.launch {
                                scaleAnim.animateTo((scaleAnim.value + 0.2f).coerceAtMost(3f))
                            }
                        }, modifier = Modifier.size(40.dp)) { Icon(Icons.Default.ZoomIn, "Zoom In", tint = Color(0xFF374151)) }
                        Box(modifier = Modifier.width(20.dp).height(1.dp).background(Color(0xFFE5E7EB)))
                        IconButton(onClick = { 
                            coroutineScope.launch {
                                scaleAnim.animateTo((scaleAnim.value - 0.2f).coerceAtLeast(0.5f))
                            }
                        }, modifier = Modifier.size(40.dp)) { Icon(Icons.Default.ZoomOut, "Zoom Out", tint = Color(0xFF374151)) }
                    }
                }
                
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    IconButton(onClick = { 
                        coroutineScope.launch {
                            scaleAnim.animateTo(1f)
                            offsetXAnim.animateTo(0f)
                            offsetYAnim.animateTo(0f)
                        }
                    }, modifier = Modifier.size(48.dp)) { Icon(Icons.Default.Map, "Minimap", tint = Color(0xFF374151)) }
                }
                
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    IconButton(onClick = { 
                        showCalculator = !showCalculator
                    }, modifier = Modifier.size(48.dp)) { Icon(Icons.Outlined.Calculate, "Calculator", tint = if (showCalculator) Color(0xFF1E9B44) else Color(0xFF374151)) }
                }
            }

            // Bottom Toolbar (Consolidated)
            Surface(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp).fillMaxWidth(0.9f).height(64.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedToolButton(
                        active = activeTool == "Select",
                        onClick = { activeTool = "Select" },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Outlined.Navigation, "Select", modifier = Modifier.rotate(-45f).offset(x = (-2).dp, y = (-2).dp), tint = if (activeTool == "Select") Color.White else Color(0xFF374151))
                    }
                    
                    Box {
                        AnimatedToolButton(
                            active = activeTool in listOf("Pen", "Brush", "Highlighter", "Pencil"),
                            onClick = { activeTool = selectedPenType },
                            onLongClick = { showPencilSettings = true },
                            modifier = Modifier.size(40.dp)
                        ) {
                            val icon = when (selectedPenType) {
                                "Brush" -> Icons.Default.Brush
                                "Highlighter" -> Icons.Default.BorderColor
                                "Pencil" -> Icons.Default.Create
                                else -> Icons.Default.Edit
                            }
                            Icon(icon, "Pen", tint = if (activeTool in listOf("Pen", "Brush", "Highlighter", "Pencil")) Color.White else Color(0xFF374151), modifier = Modifier.size(20.dp))
                        }
                        
                        DropdownMenu(
                            expanded = showPencilSettings,
                            onDismissRequest = { showPencilSettings = false },
                            shape = RoundedCornerShape(20.dp),
                            containerColor = Color.White
                        ) {
                            PencilSettingsContent(
                                selectedColor = selectedColor,
                                onColorSelected = { selectedColor = it },
                                selectedStrokeWidth = selectedStrokeWidth,
                                onStrokeWidthChanged = { selectedStrokeWidth = it },
                                selectedPenType = selectedPenType,
                                onPenTypeSelected = { 
                                    selectedPenType = it
                                    activeTool = it
                                }
                            )
                        }
                    }
                    
                    AnimatedToolButton(
                        active = activeTool == "Eraser",
                        onClick = { activeTool = "Eraser" },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Canvas(modifier = Modifier.size(20.dp)) {
                            val path = Path().apply {
                                moveTo(size.width * 0.1f, size.height * 0.7f)
                                lineTo(size.width * 0.7f, size.height * 0.7f)
                                lineTo(size.width * 0.9f, size.height * 0.3f)
                                lineTo(size.width * 0.3f, size.height * 0.3f)
                                close()
                            }
                            val color = if (activeTool == "Eraser") Color.White else Color(0xFF374151)
                            drawPath(path, color = color, style = Stroke(width = 2.dp.toPx(), join = StrokeJoin.Round))
                            drawLine(color, Offset(size.width*0.45f, size.height*0.7f), Offset(size.width*0.65f, size.height*0.3f), strokeWidth = 2.dp.toPx())
                        }
                    }
                    
                    IconButton(
                        onClick = {
                            if (paths.isNotEmpty()) {
                                undonePaths = undonePaths + paths.last()
                                paths = paths.dropLast(1)
                            }
                        },
                        enabled = paths.isNotEmpty()
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.Undo, "Undo", tint = if(paths.isNotEmpty()) Color(0xFF374151) else Color(0xFFD1D5DB))
                    }
                    
                    AnimatedToolButton(
                        active = true,
                        activeColor = Color(0xFF5CE1C6),
                        shape = CircleShape,
                        onClick = {},
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, "AI Magic", tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
            }
        } else {
            // TABLET UI
            
            // Contextual Header
            Surface(
                modifier = Modifier.fillMaxWidth().height(64.dp),
                color = SurfaceContainerWhite.copy(alpha = 0.95f),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Math lesson 3.2", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.DarkGray)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(">", color = Color.LightGray)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Geometry Session", fontSize = 16.sp, color = Color.Gray)
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = {}) {
                            Icon(Icons.Default.Save, "Save", modifier = Modifier.size(18.dp), tint = Color.DarkGray)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Save", color = Color.DarkGray, fontWeight = FontWeight.Medium)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = {}) { Icon(Icons.Default.Settings, "Settings", tint = Color.DarkGray) }
                        IconButton(onClick = {}) { Icon(Icons.Default.AccountCircle, "Profile", tint = Color.DarkGray) }
                    }
                }
            }

            // Left Sidebar
            Surface(
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 24.dp).width(64.dp),
                shape = RoundedCornerShape(32.dp),
                color = SurfaceContainerWhite.copy(alpha = 0.95f),
                shadowElevation = 12.dp
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    WhiteboardToolIcon(Icons.Outlined.CallMade, activeTool == "Select", onClick = { activeTool = "Select" })
                    Box {
                        val icon = when (selectedPenType) {
                            "Brush" -> Icons.Default.Brush
                            "Highlighter" -> Icons.Default.BorderColor
                            "Pencil" -> Icons.Default.Create
                            else -> Icons.Default.Edit
                        }
                        WhiteboardToolIcon(
                            icon = icon, 
                            active = activeTool in listOf("Pen", "Brush", "Highlighter", "Pencil"),
                            onLongClick = { showPencilSettings = true },
                            onClick = { activeTool = selectedPenType }
                        )
                        DropdownMenu(
                            expanded = showPencilSettings,
                            onDismissRequest = { showPencilSettings = false },
                            shape = RoundedCornerShape(20.dp),
                            containerColor = Color.White
                        ) {
                            PencilSettingsContent(
                                selectedColor = selectedColor,
                                onColorSelected = { selectedColor = it },
                                selectedStrokeWidth = selectedStrokeWidth,
                                onStrokeWidthChanged = { selectedStrokeWidth = it },
                                selectedPenType = selectedPenType,
                                onPenTypeSelected = { 
                                    selectedPenType = it
                                    activeTool = it
                                }
                            )
                        }
                    }
                    WhiteboardToolIcon(Icons.Outlined.AutoFixNormal, activeTool == "Eraser", onClick = { activeTool = "Eraser" })
                    WhiteboardToolIcon(Icons.Outlined.Image, false) {}
                    WhiteboardToolIcon(Icons.Outlined.CameraAlt, false) {}
                    WhiteboardToolIcon(Icons.Outlined.PictureAsPdf, false) {}
                }
            }

            // Bottom Center Bar
            Surface(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp).height(64.dp),
                shape = RoundedCornerShape(32.dp),
                color = SurfaceContainerWhite.copy(alpha = 0.95f),
                shadowElevation = 12.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (paths.isNotEmpty()) {
                                undonePaths = undonePaths + paths.last()
                                paths = paths.dropLast(1)
                            }
                        },
                        enabled = paths.isNotEmpty()
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.Undo, "Undo")
                    }
                    IconButton(
                        onClick = {
                            if (undonePaths.isNotEmpty()) {
                                paths = paths + undonePaths.last()
                                undonePaths = undonePaths.dropLast(1)
                            }
                        },
                        enabled = undonePaths.isNotEmpty()
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.Redo, "Redo")
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .size(28.dp)
                                .background(color, CircleShape)
                                .border(
                                    width = if (selectedColor == color) 3.dp else 0.dp,
                                    color = if (selectedColor == color) PrimaryIndigo.copy(alpha = 0.5f) else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    selectedColor = color
                                    if (activeTool !in listOf("Pen", "Brush", "Highlighter", "Pencil")) activeTool = selectedPenType
                                }
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Medium", fontSize = 14.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium)
                }
            }

            // Right Sidebar (Zoom & Minimap)
            Surface(
                modifier = Modifier.align(Alignment.TopEnd).padding(top = 80.dp, end = 24.dp).width(56.dp),
                shape = RoundedCornerShape(28.dp),
                color = SurfaceContainerWhite.copy(alpha = 0.95f),
                shadowElevation = 12.dp
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = { 
                        coroutineScope.launch {
                            scaleAnim.animateTo((scaleAnim.value + 0.2f).coerceAtMost(3f))
                        }
                    }) { Icon(Icons.Default.ZoomIn, "Zoom In") }
                    HorizontalDivider(modifier = Modifier.width(24.dp), color = Color.LightGray)
                    IconButton(onClick = { 
                        coroutineScope.launch {
                            scaleAnim.animateTo((scaleAnim.value - 0.2f).coerceAtLeast(0.5f))
                        }
                    }) { Icon(Icons.Default.ZoomOut, "Zoom Out") }
                    HorizontalDivider(modifier = Modifier.width(24.dp), color = Color.LightGray)
                    IconButton(onClick = { 
                        coroutineScope.launch {
                            scaleAnim.animateTo(1f)
                            offsetXAnim.animateTo(0f)
                            offsetYAnim.animateTo(0f)
                        }
                    }) { Icon(Icons.Default.Map, "Minimap") }
                    HorizontalDivider(modifier = Modifier.width(24.dp), color = Color.LightGray)
                    IconButton(onClick = { showCalculator = !showCalculator }) { 
                        Icon(Icons.Outlined.Calculate, "Calculator", tint = if (showCalculator) Color(0xFF4F46E5) else Color.DarkGray)
                    }
                }
            }
            
            // Bottom Left AI Button
            AnimatedToolButton(
                active = true,
                activeColor = SecondaryEmerald,
                shape = CircleShape,
                onClick = {},
                modifier = Modifier.align(Alignment.BottomStart).padding(start = 24.dp, bottom = 24.dp).size(56.dp)
            ) {
                Icon(Icons.Default.AutoAwesome, "AI Magic", tint = Color.White)
            }
        }
        
        if (showCalculator) {
            CalculatorWidget(
                onClose = { showCalculator = false },
                modifier = Modifier
                    .offset { androidx.compose.ui.unit.IntOffset(calcOffset.x.toInt(), calcOffset.y.toInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            calcOffset += dragAmount
                        }
                    }
            )
        }
    }
}

@Composable
fun WhiteboardToolIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    active: Boolean,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    AnimatedToolButton(
        active = active,
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = Modifier.size(48.dp).padding(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (active) Color.White else Color.DarkGray,
            modifier = Modifier.size(22.dp)
        )
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun AnimatedToolButton(
    active: Boolean,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(12.dp),
    activeColor: Color = PrimaryIndigo,
    inactiveColor: Color = Color.Transparent,
    content: @Composable BoxScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = androidx.compose.animation.core.spring(
            dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
            stiffness = androidx.compose.animation.core.Spring.StiffnessLow
        ),
        label = "scale"
    )

    Surface(
        modifier = modifier.graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(shape)
            .then(
                if (onLongClick != null) {
                    Modifier.combinedClickable(
                        interactionSource = interactionSource,
                        indication = ripple(),
                        onClick = onClick,
                        onLongClick = onLongClick
                    )
                } else {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = ripple(),
                        onClick = onClick
                    )
                }
            ),
        shape = shape,
        color = if (active) activeColor else inactiveColor,
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}

@Composable
fun PencilSettingsContent(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    selectedStrokeWidth: Float,
    onStrokeWidthChanged: (Float) -> Unit,
    selectedPenType: String,
    onPenTypeSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .width(280.dp)
            .padding(20.dp), 
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Tool Types
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF3F4F6) // Light Gray
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, 
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(
                    "Pen" to Icons.Default.Edit, 
                    "Brush" to Icons.Default.Brush, 
                    "Highlighter" to Icons.Default.BorderColor, 
                    "Pencil" to Icons.Default.Create
                ).forEach { (type, icon) ->
                    val isSelected = selectedPenType == type
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .height(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) PrimaryIndigo else Color.Transparent)
                            .clickable { onPenTypeSelected(type) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon, 
                            contentDescription = type, 
                            tint = if (isSelected) Color.White else Color(0xFF4B5563),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Size Slider
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Size", fontSize = 14.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium)
                Text("${selectedStrokeWidth.toInt()}px", fontSize = 14.sp, color = Color.Gray)
            }
            Slider(
                value = selectedStrokeWidth,
                onValueChange = onStrokeWidthChanged,
                valueRange = 2f..40f,
                colors = SliderDefaults.colors(
                    thumbColor = PrimaryIndigo,
                    activeTrackColor = PrimaryIndigo,
                    inactiveTrackColor = Color(0xFFE5E7EB)
                )
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Colors
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Color", fontSize = 14.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, 
                modifier = Modifier.fillMaxWidth()
            ) {
                val paletteColors = listOf(PrimaryIndigo, Color(0xFF0F766E), Color(0xFF10B981), Color(0xFFF97316), Color(0xFF1F2937))
                paletteColors.forEach { color ->
                    val isSelected = selectedColor == color
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (isSelected) 2.dp else 0.dp, 
                                color = if (isSelected) PrimaryIndigo else Color.Transparent, 
                                shape = CircleShape
                            )
                            .clickable { onColorSelected(color) },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(if (isSelected) 26.dp else 34.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorWidget(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFF9FAFB),
        shadowElevation = 16.dp
    ) {
        Column(modifier = Modifier.padding(24.dp).width(260.dp)) {
            // Display
            Column(
                modifier = Modifier.fillMaxWidth().clickable(onClick = onClose),
                horizontalAlignment = Alignment.End
            ) {
                Text("4,840 + 120 / 30", fontSize = 18.sp, color = Color.Gray, fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.height(4.dp))
                Text("= 4,844", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Buttons
            val greenAction = Color(0xFF1EA34B) // Tailwind green 600

            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Left 3 columns
                Column(modifier = Modifier.weight(3f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CalcBtn("C", Modifier.weight(1f))
                        CalcBtnIcon(Icons.AutoMirrored.Filled.ArrowBack, Modifier.weight(1f))
                        CalcBtn("/", Modifier.weight(1f), bg = greenAction, fg = Color.White)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CalcBtn("7", Modifier.weight(1f))
                        CalcBtn("8", Modifier.weight(1f))
                        CalcBtn("9", Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CalcBtn("4", Modifier.weight(1f))
                        CalcBtn("5", Modifier.weight(1f))
                        CalcBtn("6", Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CalcBtn("1", Modifier.weight(1f))
                        CalcBtn("2", Modifier.weight(1f))
                        CalcBtn("3", Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CalcBtnIcon(Icons.Default.SyncAlt, Modifier.weight(1f), fg = greenAction) // swap icon
                        CalcBtn("0", Modifier.weight(1f))
                        CalcBtn(".", Modifier.weight(1f))
                    }
                }
                // Right column
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    CalcBtn("*", Modifier.fillMaxWidth().aspectRatio(1f), bg = greenAction, fg = Color.White)
                    CalcBtn("-", Modifier.fillMaxWidth().aspectRatio(1f), bg = greenAction, fg = Color.White)
                    CalcBtn("+", Modifier.fillMaxWidth().aspectRatio(1f), bg = greenAction, fg = Color.White)
                    CalcBtn("=", Modifier.fillMaxWidth().weight(1f), bg = greenAction, fg = Color.White, isTall = true)
                }
            }
        }
    }
}

@Composable
fun CalcBtn(
    text: String, 
    modifier: Modifier = Modifier, 
    bg: Color = Color.White, 
    fg: Color = Color(0xFF374151),
    isTall: Boolean = false
) {
    Surface(
        modifier = modifier.then(if(!isTall) Modifier.aspectRatio(1f) else Modifier),
        shape = RoundedCornerShape(8.dp),
        color = bg,
        shadowElevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().clickable {}) {
            Text(text, fontSize = 22.sp, fontWeight = FontWeight.Medium, color = fg)
        }
    }
}

@Composable
fun CalcBtnIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector, 
    modifier: Modifier = Modifier, 
    bg: Color = Color.White, 
    fg: Color = Color(0xFF6B7280)
) {
    Surface(
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(8.dp),
        color = bg,
        shadowElevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().clickable {}) {
            Icon(icon, contentDescription = null, tint = fg, modifier = Modifier.size(20.dp))
        }
    }
}

