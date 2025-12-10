document.addEventListener("DOMContentLoaded", () => {

    let timerInterval = null;

    function startTimer() {
        if (timerInterval !== null) return;

        if (!localStorage.getItem("startTime")) {
            localStorage.setItem("startTime", Date.now());
        }

        timerInterval = setInterval(() => {
            const startTime = parseInt(localStorage.getItem("startTime"));
            const elapsedSeconds = Math.floor((Date.now() - startTime) / 1000);
            document.getElementById("timer").textContent = elapsedSeconds;
        }, 1000);
    }

    function resetTimer() {
        localStorage.removeItem("startTime");
        clearInterval(timerInterval);
        timerInterval = null;
        document.getElementById("timer").textContent = "0";
    }


    if (localStorage.getItem("startTime")) {
        startTimer();
    }

    window.setDifficulty = function(level) {
        resetTimer();
        fetch(`/difficulty/${level}`)
            .then(() => window.location.reload())
            .catch(err => console.error("Difficulty-Error:", err));
    };


    const cells = document.querySelectorAll(".cellButton");
    cells.forEach(cell => {
        cell.addEventListener("click", () => {
            startTimer();

            const x = cell.getAttribute("data-x");
            const y = cell.getAttribute("data-y");

            fetch(`/uncover/${y}/${x}`)
                .then(() => window.location.reload());
        });
    });

});
