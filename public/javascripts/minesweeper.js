document.addEventListener("DOMContentLoaded", () => {

    // Alle clickable Cells finden
    const cells = document.querySelectorAll(".cellButton");

    cells.forEach(cell => {
        cell.addEventListener("click", () => {
            const x = cell.getAttribute("data-x");
            const y = cell.getAttribute("data-y");

            fetch(`/uncover/${x}/${y}`)
                .then(() => window.location.reload());
        });
    });

    function setDifficulty(level) {
        console.log("setDifficulty called with:", level);
        
        startTimer();
        
        fetch(`/difficulty/${level}`)
            .then(() => window.location.reload())
            .catch(err => console.error("Difficulty-Error:", err));
    }

    // 👉 WICHTIG: Funktion global machen
    window.setDifficulty = setDifficulty;


    let timerInterval = null;
    let elapsedSeconds = 0;

    function startTimer() {
        if (timerInterval !== null) return; // Timer nicht doppelt starten

        timerInterval = setInterval(() => {
            elapsedSeconds++;
            document.getElementById("timer").textContent = elapsedSeconds;
        }, 1000);
    }

    function stopTimer() {
        clearInterval(timerInterval);
        timerInterval = null;
    }

    function resetTimer() {
        stopTimer();
        elapsedSeconds = 0;
        document.getElementById("timer").textContent = "0";
    }

});
