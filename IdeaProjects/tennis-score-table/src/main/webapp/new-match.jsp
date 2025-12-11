<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create New Match</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/new-match.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f7f7f7;
            min-height: 100vh;
            margin: 0;
            display: flex;
            flex-direction: column;
        }
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .form-box {
            background: #fff;
            padding: 32px 32px 24px 32px;
            border-radius: 10px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
            min-width: 340px;
            display: flex;
            flex-direction: column;
            gap: 18px;
        }
        h2 {
            text-align: center;
            margin-bottom: 12px;
        }
        .form-row {
            display: flex;
            flex-direction: column;
            gap: 6px;
        }
        label {
            font-weight: bold;
        }
        input[type="text"] {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 15px;
        }
        button {
            margin-top: 10px;
            padding: 10px;
            background: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.2s;
        }
        button:hover {
            background: #0056b3;
        }
        .error {
            color: #d00;
            font-size: 14px;
            margin-top: 2px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="form-box">
            <h2>Create New Match</h2>
            <form action="new-match" method="post" style="display: flex; flex-direction: column; gap: 12px;">
                <div class="form-row">
                    <label for="playerOneName">Player 1 Name:</label>
                    <input type="text" id="playerOneName" name="playerOneName" value="${playerOneName}" required>
                    <c:if test="${not empty errors.playerOneNameNotValid}">
                        <div class="error">${errors.playerOneNameNotValid}</div>
                    </c:if>
                </div>
                <div class="form-row">
                    <label for="playerTwoName">Player 2 Name:</label>
                    <input type="text" id="playerTwoName" name="playerTwoName" value="${playerTwoName}" required>
                    <c:if test="${not empty errors.playerTwoNameNotValid}">
                        <div class="error">${errors.playerTwoNameNotValid}</div>
                    </c:if>
                </div>
                <c:if test="${not empty errors.playerNamesAreSame}">
                    <div class="error">${errors.playerNamesAreSame}</div>
                </c:if>
                <button type="submit">Start Match</button>
            </form>
        </div>
    </div>
</body>
</html>