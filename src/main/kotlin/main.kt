import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.dragGestureFilter
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.res.vectorXmlResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowDraggableArea

object d : DragObserver {
    override fun onStart(downPosition: Offset) {
        println(downPosition)
    }

    override fun onDrag(dragDistance: Offset): Offset {
        println(dragDistance)
        return dragDistance
    }

    override fun onStop(velocity: Offset) {
        println(velocity)
    }
}

@Composable
fun MovingPiece(static: Boolean) {

    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }
    val offsetX = with(AmbientDensity.current) {
        x.toDp()
    }

    val offsetY = with(AmbientDensity.current) {
        y.toDp()
    }

    Box(modifier = Modifier.offset(x = offsetX, y = offsetY)) {
        Box(modifier = Modifier.border(3.dp, Color.Green, shape = CircleShape).width(64.dp).height(64.dp)
            .dragGestureFilter(
                object : DragObserver {
                    override fun onDrag(dragDistance: Offset): Offset {
                        x += dragDistance.x
                        y += dragDistance.y
                        return dragDistance
                    }

                    override fun onStart(downPosition: Offset) {
                        super.onStart(downPosition)
                    }

                    override fun onStop(velocity: Offset) {
                        super.onStop(velocity)
                        println(velocity)
                    }
                },
                startDragImmediately = true)
        ) {
            Image(
                imageVector = vectorXmlResource("images/pawnw.xml"),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun Square(w: Boolean, id: Int) {
    var hovered by remember {mutableStateOf(false)}
    Box(modifier = Modifier.pointerMoveFilter(
        onEnter = {
            hovered = true
            println(id)
            true
        },
        onExit = {
            hovered = false
            true
        }
    )
        .width (64.dp).height(64.dp).background(if (w) Color.White else Color.Black).
            border(if(hovered) 3.dp else 0.dp, Color.Red)) {
    }
}

fun main() = Window(title = "Jetchess Compose") {
    MaterialTheme {
        Column {
            repeat(6) { row ->
                Row {
                    repeat(6) { column ->
                        Square((row + column) % 2 == 0, (row * 6 + column))
                    }
                }
            }
        }
        MovingPiece(false)
    }
}