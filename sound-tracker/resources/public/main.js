/* I am javascript */
(function() {
  var success = function(stream) {
    var context = new AudioContext();
    var source = context.createMediaStreamSource(stream);
    var processor = context.createScriptProcessor(2048, 1, 1);

    processor.onaudioprocess = function(evt) {
      var inputBuffer = evt.inputBuffer.getChannelData(0);
      var sumSq = 0;

      var len = inputBuffer.length;
      for (var i = 0; i < len; i++) {
        sumSq += Math.abs(inputBuffer[i]);
      }

      transmit(Math.sqrt(sumSq / len))
    };

    source.connect(processor);
    processor.connect(context.destination);
  };

  var error= function(e) {
    console.log("Nah mate", e);
  }

  navigator.getUserMedia({audio: true}, success, error);

  window.audioproc = {}
})();
