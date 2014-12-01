
public class Vector2{

	private double x;         // x-coordinate
	private double y;		  // y-coordinate	  

	Vector2(){
		this(0.0,0.0);
	}
	
	Vector2(double x, double y){
		this.x = x;
		this.y = y;
	}

	public Vector2 normalizeCopy(){
		return new Vector2(x / this.length(),y / this.length());
	}
	
	public void normalize(){
		this.x = this.x / this.length();
		this.y = this.y / this.length();
	}
	
	public double dot(Vector2 other){
		return (this.x * other.x) + (this.y * other.y);
	}
	
	public double length(){
		return Math.sqrt((x * x) + (y * y));
	}
	
	public Vector2 mul(double m){
		return new Vector2(this.x * m, this.y * m);
	}
	
	public Vector2 add(Vector2 other){
		return new Vector2(this.x + other.x, this.y + other.y);
	}
	
	public Vector2 sub(Vector2 other){
		return new Vector2(this.x - other.x, this.y - other.y);
	}
	
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	
}
