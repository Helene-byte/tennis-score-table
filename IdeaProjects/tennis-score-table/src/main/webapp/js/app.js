// JavaScript for toggling the dropdown menu
document.addEventListener("DOMContentLoaded", function () {
    const navToggle = document.querySelector(".nav-toggle");
    const navLinks = document.querySelector(".nav-links");

    navToggle.addEventListener("click", function () {
        navLinks.classList.toggle("active");
    });
});

function scorePoint(player_id) {
    const uuid = new URLSearchParams(window.location.search).get("uuid");

    fetch(`${contextPath}/match-score`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `uuid=${uuid}&player_id=${player_id}`
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("player1-sets").innerText = data.player1.sets;
        document.getElementById("player1-games").innerText = data.player1.games;
        document.getElementById("player1-points").innerText = data.player1.points;
        document.getElementById("player2-sets").innerText = data.player2.sets;
        document.getElementById("player2-games").innerText = data.player2.games;
        document.getElementById("player2-points").innerText = data.player2.points;

        if (data.finished) {
            const buttons = document.querySelectorAll('.score-btn');
            buttons.forEach(button => {
                button.innerText = "Match is finished";
                button.onclick = null;
                button.classList.add('disabled-btn');
            });
        }
    })
    .catch(error => {
        console.error("Error updating score:", error);
    });
}
