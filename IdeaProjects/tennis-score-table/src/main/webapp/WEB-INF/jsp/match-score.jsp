<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <script>
        // Make contextPath and uuid available to JS <!-- UPDATED -->
        const contextPath = '${pageContext.request.contextPath}';
        const uuid = '${uuid}';
    </script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="${pageContext.request.contextPath}/images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                    <th class="table-text">Action</th> <!-- UPDATED -->
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${match.player1.name}</td>
                    <td class="table-text" id="player1-sets">${score.player1.sets}</td> <!-- UPDATED -->
                    <td class="table-text" id="player1-games">${score.player1.games}</td> <!-- UPDATED -->
                    <td class="table-text" id="player1-points">${score.player1.points}</td> <!-- UPDATED -->
                    <td class="table-text">
                        <c:choose>
                            <c:when test="${score.finished}">
                                <div class="disabled-btn">Match is finished</div> <!-- UPDATED -->
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="score-btn" onclick="scorePoint(${match.player1.id})">Score</button> <!-- UPDATED -->
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text">${match.player2.name}</td>
                    <td class="table-text" id="player2-sets">${score.player2.sets}</td> <!-- UPDATED -->
                    <td class="table-text" id="player2-games">${score.player2.games}</td> <!-- UPDATED -->
                    <td class="table-text" id="player2-points">${score.player2.points}</td> <!-- UPDATED -->
                    <td class="table-text">
                        <c:choose>
                            <c:when test="${score.finished}">
                                <div class="disabled-btn">Match is finished</div> <!-- UPDATED -->
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="score-btn" onclick="scorePoint(${match.player2.id})">Score</button> <!-- UPDATED -->
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                </tbody>
            </table>

        </section>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
    </div>
</footer>
</body>
</html>