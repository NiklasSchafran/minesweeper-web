console.log("test134");

$("#randomText").click(function (){
    $("#random").html("NaN");
})

const stringText = document.getElementById("randomStringText");
stringText.onclick = () => {
    const lengthInput = document.getElementById("lengthValue");
    const url = "/randomString/" + lengthInput.value;
    console.log(url)
    fetch(url).then((response) => {
        const randomString = document.getElementById("randomString");
        randomString.innerHTML = response;
    });
    
};
/*
$(document).on("click", "#random", function () {
    $(this).html("asda");
});
*/