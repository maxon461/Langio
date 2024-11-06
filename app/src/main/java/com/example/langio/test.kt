import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun test() {

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Define points A and B
            val pointA = Offset(x = 0f, y = 0f)
            val pointB = Offset(x = size.width, y = size.height)

            // Define the control point for the curve to control its bend
            val controlPoint = Offset(x = (pointA.x + pointB.x) / 2, y = 0f)

            // Create a Path and add a quadratic Bezier curve from A to B
            val path = Path().apply {
                moveTo(pointA.x, pointA.y)
                quadraticTo(controlPoint.x, controlPoint.y, pointB.x, pointB.y)
            }

            // Draw the curved line
            drawPath(
                path = path,
                color = Color.Magenta,
                style = Stroke(width = 4.dp.toPx())
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun testPreview() {
    test()
}
