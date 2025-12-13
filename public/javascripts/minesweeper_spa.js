// public/javascripts/minesweeper_spa.js
$(document).ready(function() {

  var gameBoard = $("#gameBoard");
  var gameState = "";

  function buildGameBoard() {
    $.ajax({
      type: "GET",
      url: "/game/getGameBoard",
      dataType: "json",
      success: function(data) {
        gameBoard.html('');
        var rows = data.rows;
        var cols = data.cols;
        var cells = data.cells;

        var gameBoardHtml = '<div id="innerBoard">';
        for (var i = 0; i < rows; i++) {
          gameBoardHtml += '<div class="game-row">';
          for (var j = 0; j < cols; j++) {
            var cell = cells[i][j];
            var state = cell.state || "covered";
            gameBoardHtml += '<div class="cell ' + state + '" data-x="' + i + '" data-y="' + j + '">';
            // show content depending on state
            gameBoardHtml += '<span class="cell-content">';
            if (state === 'bomb') {
              gameBoardHtml += '💣';
            } else if (state === 'empty') {
              gameBoardHtml += '&nbsp;';
            } else if (state === 'number') {
              gameBoardHtml += (cell.value || "");
            } else {
              // covered
              gameBoardHtml += '';
            }
            gameBoardHtml += '</span></div>';
          }
          gameBoardHtml += '</div>';
        }
        gameBoardHtml += '</div>';

        gameBoard.html(gameBoardHtml);

        // attach event handlers
        $(".cell").on('click', function() {
          var x = $(this).data('x');
          var y = $(this).data('y');
          uncoverCell(x, y);
        });

        $(".cell").on('contextmenu', function(e) {
          e.preventDefault();
          var x = $(this).data('x');
          var y = $(this).data('y');
        });

        // if game ended, show bombs
        if (gameState === "Lost" || gameState === "Won") {
          displayBombs();
        }
      },
      error: function(xhr, status, err) {
        console.error("Error fetching board:", err);
      }
    });
  }

  $('#easy').on('click', function() { setDifficulty('E'); });
  $('#medium').on('click', function() { setDifficulty('M'); });
  $('#hard').on('click', function() { setDifficulty('H'); });

  $('#undo').on('click', function(e) { e.preventDefault(); undo(); });
  $('#restart').on('click', function(e) { e.preventDefault(); restart(); });
  $('#saveGame').on('click', function(e) { e.preventDefault(); saveGame(); });
  $('#loadGamePage').on('click', function(e) { e.preventDefault(); loadGamePage(); });

  function setDifficulty(level) {
    $.ajax({
      type: 'POST',
      url: '/game/setDiff',
      data: { level: level },
      dataType: 'json',
      success: function(data) {
        gameState = data.gameState;
        console.log('Difficulty set to', level, 'gameState:', gameState);
        buildGameBoard();
      },
      error: function(xhr, status, err) {
        console.error('Error setting difficulty:', err);
      }
    });
  }

  function uncoverCell(x, y) {
    $.ajax({
      type: 'POST',
      url: '/game/uncover',
      data: { x: x, y: y },
      dataType: 'json',
      success: function(data) {
        gameState = data.gameState;
        buildGameBoard();
      },
      error: function(xhr, status, err) {
        console.error('Error uncover:', err);
      }
    });
  }



  function displayBombs() {
    $.ajax({
      type: 'GET',
      url: '/game/getBombMatrix',
      dataType: 'json',
      success: function(bombMatrix) {
        // mark bombs on the DOM
        bombMatrix.forEach(function(row, y) {
          row.forEach(function(cellValue, x) {
            var cell = $('[data-x="' + y + '"][data-y="' + x + '"]'); // note orientation
            if (cell.length) {
              var span = cell.find('.cell-content');
              span.html(cellValue === "*" ? "💣" : (cellValue === " " ? "&nbsp;" : cellValue));
              cell.removeClass('covered');
              if (cellValue === "*") { cell.addClass('bomb'); }
              else { cell.addClass('revealed'); }
            }
          });
        });
      },
      error: function(xhr, status, err) {
        console.error('Error fetching bomb matrix:', err);
      }
    });
  }

  function undo() {
    $.ajax({
      type: 'POST',
      url: '/game/undo',
      dataType: 'json',
      success: function(data) {
        gameState = data.gameState;
        buildGameBoard();
      },
      error: function(xhr, status, err) {
        console.error('Undo error:', err);
      }
    });
  }

  function restart() {
    $.ajax({
      type: 'POST',
      url: '/game/restart',
      dataType: 'json',
      success: function(data) {
        gameState = data.gameState;
        buildGameBoard();
      },
      error: function(xhr, status, err) {
        console.error('Restart error:', err);
      }
    });
  }

  function saveGame() {
    $.ajax({
      type: 'POST',
      url: '/game/save',
      dataType: 'json',
      success: function(data) {
        console.log('Saved');
      },
      error: function(xhr, status, err) {
        console.error('Save error:', err);
      }
    });
  }

  function loadGamePage() {
    $.ajax({
      type: 'GET',
      url: '/game/load',
      dataType: 'json',
      success: function(data) {
        // optional: replace head/body with server-provided fragments
        if (data.head && data.body) {
          $('head').html(data.head);
          $('body').html(data.body);
        }
      },
      error: function(xhr, status, err) {
        console.error('Load page error:', err);
      }
    });
  }

  // initial load
  buildGameBoard();
});
