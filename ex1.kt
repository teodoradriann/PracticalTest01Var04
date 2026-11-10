package ro.pub.cs.systems.eim.practicaltest01var04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.pub.cs.systems.eim.practicaltest01var04.ui.theme.PracticalTest01Var04Theme

// 1. 2 casete + 2 campuri de text, buton "Display Information" care afiseaza
// continutul campurilor bifate, buton sus in dreapta pentru navigare
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticalTest01Var04Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        // TODO: startActivity(Intent(this, SecondaryActivity::class.java))
                        onNavigate = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    var firstChecked by remember { mutableStateOf(false) }
    var secondChecked by remember { mutableStateOf(false) }
    var firstText by remember { mutableStateOf("") }
    var secondText by remember { mutableStateOf("") }
    var information by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = onNavigate) {
                Text("Navigate to secondary activity")
            }
        }
        CheckedTextField(
            checked = firstChecked,
            onCheckedChange = { firstChecked = it },
            text = firstText,
            onTextChange = { firstText = it },
            label = "Text 1"
        )
        CheckedTextField(
            checked = secondChecked,
            onCheckedChange = { secondChecked = it },
            text = secondText,
            onTextChange = { secondText = it },
            label = "Text 2"
        )
        Button(
            onClick = {
                information = buildList {
                    if (firstChecked) add(firstText)
                    if (secondChecked) add(secondText)
                }.joinToString(" ")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Display Information")
        }
        Text(text = information)
    }
}

@Composable
fun CheckedTextField(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text(label) },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PracticalTest01Var04Theme {
        MainScreen(onNavigate = {})
    }
}
