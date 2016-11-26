 function generateArray(size, value) {
  var arr = [];
  for (var i = 0; i < size; i++)
    arr[i] = value;

  return arr;
}

var fixName = function(n) {
  n = "ZZZ" + (n | "")
  return n.substring(n.length-3, n.length);
}

var getTransmitter = function(name, x, y){
  var buffer = [];
  var result = function(n) {
    buffer.push(n);
    if (buffer.length >= 10) {
      $.post('/in', {
        'source': fixName(this.name | "UNK"),
        'levels': buffer,
        'x': this.x | 0,
        'y': this.y | 0
      });

      buffer = [];
    }
  }

  result.name = fixName(name);
  result.x = x;
  result.y = y;

  return result;
};
