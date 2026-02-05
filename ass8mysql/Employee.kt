package com.example.ass8mysql

import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("emp_name") val emp_name: String,
    @SerializedName("emp_gender") val emp_gender: String,
    @SerializedName("emp_email") val emp_email: String,
    @SerializedName("emp_salary") val emp_salary: Int
)