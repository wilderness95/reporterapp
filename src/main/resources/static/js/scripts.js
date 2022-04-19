
function switchFunc()
{


    if(document.getElementById('defSwitch').checked)
    {
        document.getElementById('lastName').style.display='none';
        document.getElementById('firstName').style.display='none';
        document.getElementById('email').style.display='none';
    } else {
        document.getElementById('lastName').style.display='block';
        document.getElementById('firstName').style.display='block';
        document.getElementById('email').style.display='block';
    }
    if(document.getElementById('defSwitch').checked){

        document.getElementById('lastName').required=false;
        document.getElementById('firstName').required=false;
        document.getElementById('email').required=false;
    }else {
        document.getElementById('lastName').required=true;
        document.getElementById('firstName').required=true;
        document.getElementById('email').required=true;

    }
}


function getIpAddress(){

    $.getJSON("https://api.ipify.org?format=json", function(data) {
    $("#ip").html(data.ip);
})
}