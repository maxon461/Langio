package com.example.langio.useful
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val CardStack: ImageVector
    get() {
        if (_CardStack != null) {
            return _CardStack!!
        }
        _CardStack = ImageVector.Builder(
            name = "CardStack",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(2f, 3.5f)
                curveTo(2f, 3.2239f, 2.2239f, 3f, 2.5f, 3f)
                horizontalLineTo(12.5f)
                curveTo(12.7761f, 3f, 13f, 3.2239f, 13f, 3.5f)
                verticalLineTo(9.5f)
                curveTo(13f, 9.7761f, 12.7761f, 10f, 12.5f, 10f)
                horizontalLineTo(2.5f)
                curveTo(2.2239f, 10f, 2f, 9.7761f, 2f, 9.5f)
                verticalLineTo(3.5f)
                close()
                moveTo(2f, 10.9146f)
                curveTo(1.4174f, 10.7087f, 1f, 10.1531f, 1f, 9.5f)
                verticalLineTo(3.5f)
                curveTo(1f, 2.6716f, 1.6716f, 2f, 2.5f, 2f)
                horizontalLineTo(12.5f)
                curveTo(13.3284f, 2f, 14f, 2.6716f, 14f, 3.5f)
                verticalLineTo(9.5f)
                curveTo(14f, 10.1531f, 13.5826f, 10.7087f, 13f, 10.9146f)
                verticalLineTo(11.5f)
                curveTo(13f, 12.3284f, 12.3284f, 13f, 11.5f, 13f)
                horizontalLineTo(3.5f)
                curveTo(2.6716f, 13f, 2f, 12.3284f, 2f, 11.5f)
                verticalLineTo(10.9146f)
                close()
                moveTo(12f, 11f)
                verticalLineTo(11.5f)
                curveTo(12f, 11.7761f, 11.7761f, 12f, 11.5f, 12f)
                horizontalLineTo(3.5f)
                curveTo(3.2239f, 12f, 3f, 11.7761f, 3f, 11.5f)
                verticalLineTo(11f)
                horizontalLineTo(12f)
                close()
            }
        }.build()
        return _CardStack!!
    }

private var _CardStack: ImageVector? = null