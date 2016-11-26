/* I am javascript */
window.audioprocess = (function() {
  var exp = {};

  var success = function(stream) {
    var context = new AudioContext();
    var source = context.createMediaStreamSource(stream);
    var processor = context.createScriptProcessor(2048, 1, 1);

    var rmsBuffer = new Array(100, 0.5);
    var rmsBufferIndex = 0;

    var intervalTimeout = null;
    exp.start = function() {
      intervalTimeout = window.setInterval(function() {
          var cpy = rmsBuffer.slice();
          cpy.sort();
          if (exp.transmit)
            exp.transmit(cpy[cpy.length / 2]);
        }, 100);
    };

    exp.stop = function() {
      window.clearInterval(intervalTimeout);
    }

    processor.onaudioprocess = function(evt) {
      var inputBuffer = evt.inputBuffer.getChannelData(0);
      var sumSq = 0;

      var len = inputBuffer.length;
      for (var i = 0; i < len; i++) {
        sumSq += Math.abs(inputBuffer[i]);
      }

      rmsBufferIndex = (rmsBufferIndex + 1) % rmsBuffer.length;
      rmsBuffer[rmsBufferIndex] = Math.sqrt(sumSq / len);
    };

    source.connect(processor);
    processor.connect(context.destination);

    if (exp.success)
      exp.success();
  };

  var error= function(e) {
    console.log("Nah mate", e);
    if (audioproc.onconnecterror)
      audioproc.onconnecterror(e);
  }

  navigator.getUserMedia({audio: true}, success, error);

  return exp
})();
