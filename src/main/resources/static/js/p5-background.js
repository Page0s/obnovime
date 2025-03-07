let particles = [];
let documents = [];
let logoImage;
const particleCount = 30;
const documentCount = 15;

function preload() {
  logoImage = loadImage('/images/logo.svg');
}

function setup() {
  const canvas = createCanvas(windowWidth, windowHeight);
  canvas.parent('p5-canvas');
  
  // Inicijalizacija točkica
  for (let i = 0; i < particleCount; i++) {
    particles.push(new Particle());
  }
  
  // Inicijalizacija letećih dokumenata
  for (let i = 0; i < documentCount; i++) {
    documents.push(new Document());
  }
}

function draw() {
  clear();
  
  // Ažuriranje i prikaz točkica
  for (let particle of particles) {
    particle.update();
    particle.display();
    particle.connectNeighbors(particles);
  }
  
  // Ažuriranje i prikaz dokumenata
  for (let doc of documents) {
    doc.update();
    doc.display();
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
    
    // Odbijanje od rubova
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

class Document {
  constructor() {
    this.position = createVector(random(width), random(height));
    this.velocity = createVector(random(-0.3, 0.3), random(-0.3, 0.3));
    this.size = random(16, 24);
    this.alpha = random(150, 200);
    this.rotation = random(-0.02, 0.02);
    this.angle = random(0, TWO_PI);
    this.scale = random(0.5, 0.8);
  }
  
  update() {
    this.position.add(this.velocity);
    this.angle += this.rotation;
    
    // Odbijanje od rubova
    if (this.position.x < 0 || this.position.x > width) this.velocity.x *= -1;
    if (this.position.y < 0 || this.position.y > height) this.velocity.y *= -1;
  }
  
  display() {
    push();
    translate(this.position.x, this.position.y);
    rotate(this.angle);
    
    // Prikaz loga
    tint(255, this.alpha);
    imageMode(CENTER);
    image(logoImage, 0, 0, 45 * this.scale, 45 * this.scale);
    
    pop();
  }
}
