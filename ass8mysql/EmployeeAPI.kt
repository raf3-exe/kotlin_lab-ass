package com.example.ass8mysql

import retrofit2.http.*

interface EmployeeAPI {
    @GET("allEmp")
    suspend fun retrieveEmployee(): List<Employee>

    @FormUrlEncoded
    @POST("insertEmp")
    suspend fun insertEmployee(
        @Field("emp_name") emp_name: String,
        @Field("emp_gender") emp_gender: String,
        @Field("emp_email") emp_email: String,
        @Field("emp_salary") emp_salary: Int
    ): Employee
}