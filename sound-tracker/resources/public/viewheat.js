function drawcircle(canvas, x, y, level)
{
    var context = canvas.getContext('2d');
    var centerX = x;
    var centerY = y;
    var radius = 70;
    
    context.globalAlpha = 0.5;
    context.beginPath();
    context.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
    context.fillStyle = 'green';
    context.fill();
    context.lineWidth = 5;
    context.strokeStyle = '#003300';
    context.stroke();
}

function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

function drawrequest(request) {
    var canvas = document.getElementById('heatcanvas');
    var rows = request.split("|");
    var len = rows.length;
    console.log("request");
    clearInterval();
    var i = 0;
    setInterval(function(){
       
       if(i >= len)
       {
           clearInterval();
       }
       else
       {
           console.log("request");
           var row = rows[i];
           var rowbody = row.split(",");
           var x = parseInt(row[0]);
           var y = parseInt(row[1])
           var time = row[2];
           var level = parseFloat(row[3]);

            console.log("wwa");
           drawcircle(canvas, x, y, level);
           i++;
       }
    }, 200);

    return false;
}

function formposted()
{
    var request = httpGet("/out");
    console.log(request);
    try
    {
        drawrequest(request);
    }
    catch(err)
    {
       document.getElementById("errors").innerHTML = err.message;
    }
    return false;
}

