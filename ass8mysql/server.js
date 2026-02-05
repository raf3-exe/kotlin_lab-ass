const express = require("express");
const mysql = require("mysql");
require("dotenv").config();
const app = express();

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// MySQL Pool
const db = mysql.createPool({
    connectionLimit: 10,
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
});

// Promise wrapper
function query(sql, params = []) {
    return new Promise((resolve, reject) => {
        db.query(sql, params, (err, results) => {
            if (err) return reject(err);
            resolve(results);
        });
    });
}

// Root
app.get("/", (req, res) => {
    res.json({ message: "Simple Employee API (Insert & Query)" });
});

// QUERY DATA (GET)
app.get("/allEmp", async (req, res) => {
    try {
        const results = await query("SELECT * FROM employee");
        res.json(results);
    } catch (err) {
        res.status(500).json({ error: true, message: err.message });
    }
});

// INSERT DATA (POST)
app.post("/insertEmp", async (req, res) => {
    try {
        const employee = req.body;
        if (!employee || Object.keys(employee).length === 0) {
            return res
                .status(400)
                .json({ error: true, message: "Please provide employee data" });
        }

        const result = await query(
            "INSERT INTO employee SET ?",
            [employee]
        );

        res.status(201).json({
            message: "Employee created",
            insertId: result.insertId,
        });
    } catch (err) {
        res.status(500).json({ error: true, message: err.message });
    }
});

// Start server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});