function drawcircle(canvas)
{
    var context = canvas.getContext('2d');
    var centerX = canvas.width / 2;
    var centerY = canvas.height / 2;
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


function formposted()
{
    var canvas = document.getElementById('heatcanvas');
    drawcircle(canvas);
    return false;
}
