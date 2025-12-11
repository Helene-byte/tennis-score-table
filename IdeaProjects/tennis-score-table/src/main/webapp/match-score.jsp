<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Match Score</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .container { max-width: 500px; margin: 40px auto; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);}
        h2 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px;}
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center;}
        .actions { display: flex; gap: 10px; justify-content: center;}
        .winner { color: green; font-weight: bold;}
    </style>
</head>
<body>
    <div class="container">
        <h2>Match Score</h2>
        <table>
            <tr>
                <th>Player</th>
                <th>Sets</th>
                <th>Games</th>
                <th>Points</th>
                <th>Tie-break</th>
            </tr>
            <tr>
                <td>${player1Name}</td>
                <td>${score.player1Sets}</td>
                <td>${score.player1Games}</td>
                <td>${score.player1Points}</td>
                <td>${score.player1TieBreakPoints}</td>
            </tr>
            <tr>
                <td>${player2Name}</td>
                <td>${score.player2Sets}</td>
                <td>${score.player2Games}</td>
                <td>${score.player2Points}</td>
                <td>${score.player2TieBreakPoints}</td>
            </tr>
        </table>

        <c:if test="${not score.finished}">
            <div class="actions">
                <form action="match-score?uuid=${matchId}" method="post" style="display:inline;">
                    <input type="hidden" name="winner" value="1"/>
                    <button type="submit">${player1Name} выиграл очко</button>
                </form>
                <form action="match-score?uuid=${matchId}" method="post" style="display:inline;">
                    <input type="hidden" name="winner" value="2"/>
                    <button type="submit">${player2Name} выиграл очко</button>
                </form>
            </div>
        </c:if>
        <c:if test="${score.finished}">
            <div class="winner">
                Матч завершён! Победитель:
                <c:choose>
                    <c:when test="${score.winner == 1}">${player1Name}</c:when>
                    <c:otherwise>${player2Name}</c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>
</body>
</html>