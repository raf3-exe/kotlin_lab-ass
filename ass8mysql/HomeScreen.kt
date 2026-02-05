package com.example.ass8mysql

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: EmployeeViewModel = viewModel()
) {
    val contextForToast = LocalContext.current.applicationContext
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getAllEmployee()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    Column {
        Spacer(modifier = Modifier.height(height = 20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(weight = 0.85f)) {
                Text(
                    text = "Employee Lists:",
                    fontSize = 25.sp
                )
            }
            Button(onClick = {
                navController.navigate(Screen.Insert.route)
            }) {
                Text(text = "Add Employee")
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space = 6.dp)
        ) {
            items(
                items = viewModel.employeeList,
                key = { it.emp_name }
            ) { item ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .height(height = 130.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    ),
                    shape = RoundedCornerShape(corner = CornerSize(size = 16.dp)),
                    onClick = {
                        Toast.makeText(
                            contextForToast, "Click on ${item.emp_name}.",
                             Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Row( Modifier.fillMaxWidth()
                        .height(height = 130.dp)
                        .padding(all = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Name: ${item.emp_name}" +
                                    "\nGender: ${item.emp_gender}" +
                                    "\nEmail: ${item.emp_email}" +
                                    "\nSalary: ${item.emp_salary}",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}