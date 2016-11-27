window.renderer = (function() {
  var UPDATES_PER_SECOND = 30;
  var CIRCLE_MAX_SIZE = 120;
  var CIRCLE_LIFE = 1 * UPDATES_PER_SECOND;

  var Circle = function(x, y, intensity, color) {
    this.x = x;
    this.y = y;
    this.intensity = intensity;
    this.progress = 0;
    this.color = color;
  }

  Circle.prototype.render = function(ctx) {
    var progress = this.progress++ / (CIRCLE_LIFE * 1.0);

    var radius = this.intensity * CIRCLE_MAX_SIZE * progress;
    var lineWidth = this.intensity * CIRCLE_MAX_SIZE * 0.5 * (1-progress);

    ctx.beginPath();
    ctx.arc(this.x, this.y, radius, 0, 2 * Math.PI, false);
    ctx.lineWidth = lineWidth;
    ctx.strokeStyle = this.color;
    ctx.stroke();
  }

  Circle.prototype.isDead = function() {
    return this.progress >= CIRCLE_LIFE;
  }

  var Display = function(canvas) {
    this.canvas = canvas;
    this.ctx = canvas.getContext('2d');
    this.elements = []
  }

  Display.prototype.start = function() {
    var _this = this;
    this.intervalId = window.setInterval(function() {
      _this.render();
    }, 1000/UPDATES_PER_SECOND);
  }

  Display.prototype.stop = function() {
    window.clearInterval(this.intervalId);
  }

  Display.prototype.render = function() {
    this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

    this.ctx.fillStyle = 'black';
    for (var i = 0; i < this.elements.length; i++) {
      var element = this.elements[i];
      element.render(this.ctx);

      if (element.isDead()) {
        this.elements[i] = this.elements[this.elements.length - 1];
        this.elements.pop();
      }
    }
  }

  Display.prototype.addElement = function(el) {
    this.elements.push(el);
  }

  return {
    'Circle': Circle,
    'Display': Display
  }
})();
