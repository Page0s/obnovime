let particles = [];
const particleCount = 50;

function setup() {
  const canvas = createCanvas(windowWidth, windowHeight);
  canvas.parent('p5-canvas');
  
  // Initialize particles
  for (let i = 0; i < particleCount; i++) {
    particles.push(new Particle());
  }
}

function draw() {
  clear();
  
  // Update and display particles
  for (let particle of particles) {
    particle.update();
    particle.display();
    particle.connectNeighbors(particles);
  }
}

function windowResized() {
  resizeCanvas(windowWidth, windowHeight);
}

class Particle {
  constructor() {
    this.position = createVector(random(width), random(height));
    this.velocity = createVector(random(-0.5, 0.5), random(-0.5, 0.5));
    this.size = random(3, 6);
    this.alpha = random(100, 150);
  }
  
  update() {
    this.position.add(this.velocity);
    
    // Bounce off edges
    if (this.position.x < 0 || this.position.x > width) this.velocity.x *= -1;
    if (this.position.y < 0 || this.position.y > height) this.velocity.y *= -1;
  }
  
  display() {
    noStroke();
    fill(255, 255, 255, this.alpha);
    circle(this.position.x, this.position.y, this.size);
  }
  
  connectNeighbors(particles) {
    for (let other of particles) {
      if (other !== this) {
        let d = dist(this.position.x, this.position.y, other.position.x, other.position.y);
        if (d < 150) {
          let alpha = map(d, 0, 150, 50, 0);
          stroke(255, 255, 255, alpha);
          line(this.position.x, this.position.y, other.position.x, other.position.y);
        }
      }
    }
  }
}
