const inputField = document.getElementById("chat-input");
const outputArea = document.getElementById("chat-area");

const socketRoute = document.getElementById("ws-route").value;
const socket = new WebSocket(socketRoute.replace("http", "ws"));