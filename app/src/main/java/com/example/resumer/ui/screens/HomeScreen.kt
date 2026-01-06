package com.example.resumer.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.resumer.data.model.Project
import com.example.resumer.data.model.ResumeResponse
import com.example.resumer.ui.components.ControlPanel
import com.example.resumer.ui.viewmodel.ResumeViewModel
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: ResumeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    //location permission box
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (isGranted) {
            getCurrentLocation(context, viewModel)
        }
    }

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        } else {
            getCurrentLocation(context, viewModel)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {

        //resume area -top
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.resumeData != null) {

                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(12.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = uiState.backgroundColor),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    ResumeContent(
                        data = uiState.resumeData!!,
                        fontSize = uiState.fontSize,
                        textColor = uiState.textColor
                    )
                }
            }

            //location badge
            LocationPill(
                text = uiState.userLocation,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-10).dp, y = (-20).dp)
            )
        }

        //bottom area
        ControlPanel(
            fontSize = uiState.fontSize,
            onFontSizeChange = viewModel::updateFontSize,
            currentFontColor = uiState.textColor,
            onFontColorChange = viewModel::updateTextColor,
            currentBgColor = uiState.backgroundColor,
            onBgColorChange = viewModel::updateBackgroundColor
        )
    }
}

@Composable
fun LocationPill(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primaryContainer,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun ResumeContent(
    data: ResumeResponse,
    fontSize: Float,
    textColor: Color
) {
    val scrollState = rememberScrollState()
    val dynamicLineHeight = (fontSize * 1.4).sp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(32.dp)
    ) {

        Text(
            text = data.name,
            fontSize = (fontSize + 10).sp,
            lineHeight = ((fontSize + 10) * 1.4).sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = textColor.copy(alpha = 0.5f)
        )

        val contactLine1 = listOfNotNull(
            data.phone.takeIf { it.isNotEmpty() },
            data.email.takeIf { it.isNotEmpty() },
            data.twitter.takeIf { it.isNotEmpty() }
        ).joinToString(separator = " • ")

        if (contactLine1.isNotEmpty()) {
            Text(
                text = contactLine1,
                fontSize = (fontSize - 2).sp,
                lineHeight = dynamicLineHeight,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (data.address.isNotEmpty()) {
            Text(
                text = data.address,
                fontSize = (fontSize - 2).sp,
                lineHeight = dynamicLineHeight,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (data.summary.isNotEmpty()) {
            SectionHeader("PROFESSIONAL SUMMARY", fontSize, textColor)
            Text(
                text = data.summary,
                fontSize = fontSize.sp,
                lineHeight = dynamicLineHeight,
                color = textColor,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }

        SectionHeader("SKILLS", fontSize, textColor)

        val skillsString = data.skills.joinToString(separator = " • ")

        Text(
            text = skillsString,
            fontSize = fontSize.sp,
            lineHeight = dynamicLineHeight,
            color = textColor,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        SectionHeader("PROJECTS", fontSize, textColor)

        data.projects.forEach { project ->
            ProjectItem(project, fontSize, textColor)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun ProjectItem(project: Project, fontSize: Float, textColor: Color) {
    val dynamicLineHeight = (fontSize * 1.4).sp

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = project.title,
            fontSize = fontSize.sp,
            lineHeight = dynamicLineHeight,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        val formattedStart = formatDateForResume(project.startDate)
        val formattedEnd = formatDateForResume(project.endDate)

        Text(
            text = "($formattedStart to $formattedEnd)",
            fontSize = (fontSize - 2).sp,
            lineHeight = dynamicLineHeight,
            color = textColor.copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = project.description,
            fontSize = fontSize.sp,
            lineHeight = dynamicLineHeight,
            color = textColor
        )
    }
}

@Composable
fun SectionHeader(title: String, fontSize: Float, textColor: Color) {
    Text(
        text = title.uppercase(),
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold,
        color = textColor,
    )
}

fun formatDateForResume(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH)

        val date = inputFormat.parse(dateString)
        if (date != null) {
            outputFormat.format(date)
        } else {
            dateString
        }
    } catch (e: Exception) {
        dateString
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(context: Context, viewModel: ResumeViewModel) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            viewModel.updateLocation(location.latitude, location.longitude)
        }
    }
}