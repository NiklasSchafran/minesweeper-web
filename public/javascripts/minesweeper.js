$(document).ready(function() {

    let timerInterval = null;

    function startTimer() {
        if (timerInterval !== null) return;

        if (!localStorage.getItem("startTime")) {
            localStorage.setItem("startTime", Date.now());
        }

        timerInterval = setInterval(() => {
            const startTime = parseInt(localStorage.getItem("startTime"));
            const elapsedSeconds = Math.floor((Date.now() - startTime) / 1000);
            $("#timer").text(elapsedSeconds);
        }, 1000);
    }

    function resetTimer() {
        localStorage.removeItem("startTime");
        clearInterval(timerInterval);
        timerInterval = null;
        $("#timer").text("0");
    }

    if (localStorage.getItem("startTime")) {
        startTimer();
    }

    window.setDifficulty = function(level) {
        console.log("SetDifficulty called → Level:", level);
        resetTimer();
        startTimer();
        $.ajax({
            url: `/difficulty/${level}`,
            method: "GET",
            success: function() {
                loadGame();
            }
        });
    };

    function loadGame() {
        $.ajax({
            url: "/loadGame",
            method: "GET",
            dataType: "json",
            success: function(data) {
                updateGrid(data);
            }
        });
    }

    function updateGrid(data) {
        const matrix = data.matrix.rows;

        $("#gameTable tr").each(function(y) {
            $(this).find("button.cellButton").each(function(x) {
                const value = matrix[y][x];

                let display = "□";
                switch(value) {
                    case "-": display = "□"; break;
                    case " ": display = "&nbsp;"; break;
                    case "*": display = "💣"; break;
                    default: display = value; break;
                }
                $(this).html(display);
            });
        });
    }

    $(".cellButton").click(function() {
        const x = $(this).data("x");
        const y = $(this).data("y");

        startTimer();

        $.ajax({
            url: `/uncover/${y}/${x}`,
            method: "GET",
            success: function() {
                loadGame();
            }
        });
    });

    $("#loadBtn").click(function() {
        loadGame();
    });

    loadGame();
});
