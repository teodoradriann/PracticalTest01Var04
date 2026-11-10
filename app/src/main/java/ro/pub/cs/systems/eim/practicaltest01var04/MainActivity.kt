package ro.pub.cs.systems.eim.practicaltest01var04

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.pub.cs.systems.eim.practicaltest01var04.ui.theme.PracticalTest01Var04Theme

class MainActivity : ComponentActivity() {
    // tin starea in cu mutableStateOf ca androidul sa nu o mai salveze
    private var firstChecked by mutableStateOf(false)
    private var secondChecked by mutableStateOf(false)
    private var firstText by mutableStateOf("")
    private var secondText by mutableStateOf("")
    private var information by mutableStateOf("")

    private val secondActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val message = when (result.resultCode) {
                RESULT_OK -> "OK"
                RESULT_CANCELED -> "Cancel"
                else -> "Unknown (${result.resultCode})"
            }
            Toast.makeText(this, "Result: $message", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticalTest01Var04Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        firstChecked = firstChecked,
                        onFirstCheckedChange = { firstChecked = it },
                        secondChecked = secondChecked,
                        onSecondCheckedChange = { secondChecked = it },
                        firstText = firstText,
                        onFirstTextChange = { firstText = it },
                        secondText = secondText,
                        onSecondTextChange = { secondText = it },
                        information = information,
                        onDisplayInformation = {
                            information = buildList {
                                if (firstChecked) add(firstText)
                                if (secondChecked) add(secondText)
                            }.joinToString(" ")
                        },
                        onNavigate = {
                            val intent = Intent(this, SecondActivity::class.java).apply {
                                putExtra(SecondActivity.FIRST_TEXT_EXTRA, firstText)
                                putExtra(SecondActivity.SECOND_TEXT_EXTRA, secondText)
                            }
                            secondActivityLauncher.launch(intent)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // salvare manuala a controalelor cand sistemul distruge activitatea
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(FIRST_CHECKED_KEY, firstChecked)
        outState.putBoolean(SECOND_CHECKED_KEY, secondChecked)
        outState.putString(FIRST_TEXT_KEY, firstText)
        outState.putString(SECOND_TEXT_KEY, secondText)
        outState.putString(INFORMATION_KEY, information)
        Log.d(TAG, "onSaveInstanceState: $outState")
    }

    // restaurare manuala a controalelor la recrearea activitatii
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        firstChecked = savedInstanceState.getBoolean(FIRST_CHECKED_KEY, false)
        secondChecked = savedInstanceState.getBoolean(SECOND_CHECKED_KEY, false)
        firstText = savedInstanceState.getString(FIRST_TEXT_KEY, "")
        secondText = savedInstanceState.getString(SECOND_TEXT_KEY, "")
        information = savedInstanceState.getString(INFORMATION_KEY, "")
        Log.d(TAG, "onRestoreInstanceState: $savedInstanceState")
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val FIRST_CHECKED_KEY = "firstChecked"
        private const val SECOND_CHECKED_KEY = "secondChecked"
        private const val FIRST_TEXT_KEY = "firstText"
        private const val SECOND_TEXT_KEY = "secondText"
        private const val INFORMATION_KEY = "information"
    }
}

@Composable
fun MainScreen(
    firstChecked: Boolean,
    onFirstCheckedChange: (Boolean) -> Unit,
    secondChecked: Boolean,
    onSecondCheckedChange: (Boolean) -> Unit,
    firstText: String,
    onFirstTextChange: (String) -> Unit,
    secondText: String,
    onSecondTextChange: (String) -> Unit,
    information: String,
    onDisplayInformation: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            onCheckedChange = onFirstCheckedChange,
            text = firstText,
            onTextChange = onFirstTextChange,
            label = "Text 1"
        )
        CheckedTextField(
            checked = secondChecked,
            onCheckedChange = onSecondCheckedChange,
            text = secondText,
            onTextChange = onSecondTextChange,
            label = "Text 2"
        )
        Button(
            onClick = onDisplayInformation,
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
        MainScreen(
            firstChecked = true,
            onFirstCheckedChange = {},
            secondChecked = false,
            onSecondCheckedChange = {},
            firstText = "Text 1",
            onFirstTextChange = {},
            secondText = "Text 2",
            onSecondTextChange = {},
            information = "Text 1",
            onDisplayInformation = {},
            onNavigate = {}
        )
    }
}
