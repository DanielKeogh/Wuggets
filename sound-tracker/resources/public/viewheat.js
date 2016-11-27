var playNoise = (function() {
  var HeatMap = function(canvas) {
    this.display = new renderer.Display(canvas);
  }

  HeatMap.prototype.addNoise = function(noiseNodes) {
    for (var i = 0; i < noiseNodes.length; i++) {
      var node = noiseNodes[i];

      var hash = (node.x + 23) *
              (node.x + 23) *
              (node.y + 23) *
              (node.y + 23);

      hash = ("AAAAAA" + hash);
      hash = '#' + hash.substring(hash.length - 6, hash.length);

      this.display.addElement(new renderer.Circle(node.x, node.y, node.level * 2, hash));
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

function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

function formposted()
{
    var request = httpGet("/out");
    console.log(request);
    var canvas = document.getElementById('heatcanvas');
    playNoise(canvas, request);

    return false;
}
