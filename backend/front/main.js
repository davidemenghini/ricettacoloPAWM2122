

console.log(window.location);





function loginButtonPressed(showhide){
    if(showhide=='show'){
        document.getElementById('loginPopup').style.visibility = "visible";
        console.log("sto per provare porcodioooo");

        var post = $.post({
            url: "http://localhost:8000/main/Recipe",
            data: "ciao da js",
            success: function risposta(){
                alert("yesssss");
            },
            dataType: "text"
        });
        console.log(post);
        
    }else if(showhide=='hide'){
        document.getElementById('loginPopup').style.visibility = "hidden";
    }
}


function loginCancelPressed(){
    document.getElementById('loginPopup').style.visibility = "hidden";
}


$(".loginButton").click(function(){
    
    $.post("")
    type: "POST";
    

  });



