import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.outlined.Search

@Composable
fun WeatherScreen(repository: Repository) {

    var queriedCity by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            value = queriedCity,
            onValueChange = { queriedCity = it },
            modifier = Modifier.padding(end = 16.dp).weight(1f),
            placeholder = { Text("Any city, really...") },
            label = { Text(text = "Search for a city") },
            leadingIcon = { Icon(Icons.Filled.LocationOn, "Location") },
        )

        Button(onClick = { /* We'll deal with this later */}) {
            Icon(Icons.Outlined.Search, "Search")
        }
    }

}