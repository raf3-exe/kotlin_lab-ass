package com.example.ass8mysql

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EmployeeViewModel : ViewModel() {
    var employeeList = mutableStateListOf<Employee>()
        private set

    fun getAllEmployee() {
        viewModelScope.launch {
            try {
                val response = EmployeeClient.EmployeeAPI.retrieveEmployee()
                employeeList.clear()
                employeeList.addAll(response)
            } catch (e: Exception) {
                Log.e("EmployeeViewModel", "Get Error: ${e.message}")
            }
        }
    }

    fun insertEmployee(employee: Employee, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                EmployeeClient.EmployeeAPI.insertEmployee(
                    employee.emp_name,
                    employee.emp_gender,
                    employee.emp_email,
                    employee.emp_salary
                )
                onSuccess()
                getAllEmployee()
            } catch (e: Exception) {
                Log.e("EmployeeViewModel", "Insert Error: ${e.message}")
            }
        }
    }
}