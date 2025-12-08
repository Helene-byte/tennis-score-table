<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.ee/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Завершённые матчи</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .container { max-width: 700px; margin: 40px auto; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);}
        h2 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px;}
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center;}
        .search-form { margin-bottom: 20px; display: flex; gap: 10px; justify-content: center; }
        .search-form input[type="text"] { padding: 6px; border-radius: 4px; border: 1px solid #ccc; }
        .search-form button { padding: 6px 16px; border-radius: 4px; border: none; background: #007bff; color: #fff; cursor: pointer; }
        .search-form button:hover { background: #0056b3; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Завершённые матчи</h2>
        <form class="search-form" action="matches" method="get">
            <input type="text" name="filter_by_player_name" placeholder="Имя игрока" value="${param.filter_by_player_name}">
            <button type="submit">Искать</button>
        </form>
        <table>
            <tr>
                <th>ID</th>
                <th>Игрок 1</th>
                <th>Игрок 2</th>
                <th>Победитель</th>
            </tr>
            <c:forEach var="match" items="${matches}">
                <tr>
                    <td>${match.id}</td>
                    <td>${match.player1.name}</td>
                    <td>${match.player2.name}</td>
                    <td>${match.winner.name}</td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${empty matches}">
            <div style="text-align:center; color:#d00;">Матчи не найдены.</div>
        </c:if>
    </div>
</body>
</html>