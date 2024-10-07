package io.bahuma.kassenkumpel.feature_pointofsale.presentation.products.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.bahuma.kassenkumpel.core.model.ProductColor
import io.bahuma.kassenkumpel.utils.formatPrice

@Composable
fun ProductCard(
    name: String,
    color: Color,
    price: Double,
    count: Int,
    modifier: Modifier = Modifier
) {
    val topPadding = 10.dp
    BadgedBox(
        modifier = modifier
            .requiredSize(135.dp + topPadding)
            .padding(top = topPadding),
        badge = {
            if (count > 0) {
                Badge(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    modifier = Modifier
                        .height(24.dp)
                        .widthIn(24.dp, 48.dp)
                        .offset(x = (-8).dp)
                ) {
                    Text(
                        text = count.toString(),
                        fontSize = 16.sp
                    )
                }
            }
        }
    ) {
        Card(
            border = BorderStroke(2.dp, Color.LightGray),
            colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(10.dp, 15.dp, 10.dp, 10.dp)
                    .fillMaxSize()
            ) {
                // Name
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black
                )

                Spacer(modifier.weight(1f))

                // Price
                Text(
                    text = formatPrice(price),
                    fontSize = 16.sp,

                    )

                Spacer(modifier.height(8.dp))


                // Color
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color)
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    Surface(modifier = Modifier.padding(20.dp)) {
        ProductCard(
            "Kalbsleberkässemmel", ProductColor.MINT.color, 1.5, 5
        )
    }
}