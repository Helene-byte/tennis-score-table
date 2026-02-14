<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

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
        <h1>Matches</h1>
        <div class="input-container">
            <form method="get" action="${pageContext.request.contextPath}/matches">
                <input class="input-filter" name="filter_by_player_name"
                       value="${param.filter_by_player_name}" placeholder="Filter by name" type="text" /> <!-- UPDATED -->
                <button class="btn-filter" type="submit">Search</button>
                <a href="${pageContext.request.contextPath}/matches">
                    <button class="btn-filter" type="button">Reset Filter</button>
                </a>
            </form>
        </div>

        <!-- Show not found message if present -->
        <c:if test="${not empty matches_response_dto.notFoundMessage}"> <!-- UPDATED -->
            <div class="error-message">${matches_response_dto.notFoundMessage}</div> <!-- UPDATED -->
        </c:if> <!-- UPDATED -->

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:forEach var="match" items="${matches_response_dto.matches}"> <!-- UPDATED -->
                <tr>
                    <td>${match.player1.name}</td>
                    <td>${match.player2.name}</td>
                    <td><span class="winner-name-td">${match.winner.name}</span></td>
                </tr>
            </c:forEach>
        </table>
        <div class="pagination" style="margin-top: 20px;">
            <c:if test="${matches_response_dto.currentPage > 1}"> <!-- UPDATED -->
                <a href="${pageContext.request.contextPath}/matches?page=${matches_response_dto.currentPage-1}&filter_by_player_name=${param.filter_by_player_name}" class="btn-filter">Prev</a> <!-- UPDATED -->
            </c:if>
            <span style="margin: 0 10px;">Page ${matches_response_dto.currentPage} of ${matches_response_dto.lastPageNumber}</span> <!-- UPDATED -->
            <c:if test="${matches_response_dto.currentPage < matches_response_dto.lastPageNumber}"> <!-- UPDATED -->
                <a href="${pageContext.request.contextPath}/matches?page=${matches_response_dto.currentPage+1}&filter_by_player_name=${param.filter_by_player_name}" class="btn-filter">Next</a> <!-- UPDATED -->
            </c:if>
            <!-- Optional: Show page numbers for advanced pagination -->
            <c:forEach var="pageNum" items="${matches_response_dto.pagesToShow}"> <!-- UPDATED -->
                <a href="${pageContext.request.contextPath}/matches?page=${pageNum}&filter_by_player_name=${param.filter_by_player_name}" class="btn-filter">${pageNum}</a> <!-- UPDATED -->
            </c:forEach> <!-- UPDATED -->
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>