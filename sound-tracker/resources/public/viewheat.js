var playNoise = (function() {
  var HeatMap = function(canvas) {
    this.display = new renderer.Display(canvas);
  }

  HeatMap.prototype.addNoise = function(noiseNodes) {
    for (var i = 0; i < noiseNodes.length; i++) {
      var node = noiseNodes[i];

      this.display.addElement(new renderer.Circle(node.x, node.y, node.level * 2));
    }
  }

  var getNoiseNodes = function(text) {
    return text.split('|').map(function(line) {
      var data = line.split(',');
      return {
        'x': parseInt(data[0]),
        'y': parseInt(data[1]),
        'time': new Date(data[2]),
        'level': parseFloat(data[3])
      }
    });
  }

  return function(canvas, text) {
    var heatMap = new HeatMap(canvas);
    var nodes = getNoiseNodes(text).sort(function(n) { return n.time; });

    var timeoutFunction = function() {
      var firstNodeTime = nodes[0].time;

      var sliceEnd = 0;
      while (sliceEnd < nodes.length && nodes[sliceEnd].time == firstNodeTime) {
        sliceEnd++;
      }

      heatMap.addNoise(nodes.slice(0, sliceEnd + 1));
      nodes = nodes.slice(sliceEnd + 1);

      if (nodes.length == 0) {
        window.clearInterval(intervalId);
      }
    }

    heatMap.display.start();
    var intervalId = setInterval(timeoutFunction, 100);
  }
})();

function drawcircle(canvas, x, y, level)
{
    var context = canvas.getContext('2d');
    var centerX = x;
    var centerY = y;
    var radius = 500 * level;

    context.globalAlpha = 0.3;
    context.beginPath();
    context.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
    context.fillStyle = 'red';
    context.fill();
    context.lineWidth = 5;
//    context.strokeStyle = '#003300';
//    context.stroke();
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

    clearInterval();
    var i = 0;
    var lastTime = "";
    setInterval(function(){

       if(i >= len)
       {
           clearInterval();
       }
       else
       {
           canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height);
           while (i < len)
           {
               console.log('nooby');
              var rowBody = rows[i];
              var row = rowBody.split(",");
              var x = parseInt(row[0]);
              var y = parseInt(row[1])
              var time = row[2];
              var level = parseFloat(row[3]);

              if(lastTime.length == 0)
              {
                  lastTime = time;
              }

              if (time === lastTime)
              {
                console.log(time);
                drawcircle(canvas, x, y, level);
                i++;
              }
              else
              {
                  console.log('notnooby');
                  break;
              }
           }
           lastTime = "";
       }
    }, 50);

    return false;
}

function formposted()
{
    var request = httpGet("/out");
    console.log(request);
    var canvas = document.getElementById('heatcanvas');
    playNoise(canvas, request);

    return false;
}
