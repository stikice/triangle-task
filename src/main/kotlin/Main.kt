import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun main(args: Array<String>){
    val a = Point(0, 0)
    val b = Point(0, 5)
    val c = Point(5, 0)
    val usualTriangle = Triangle(a,b,c)
    usualTriangle.print()
    println("Perimeter is " + usualTriangle.perimeter())
    println("Area is " + usualTriangle.area())
    val degree = 360
    usualTriangle.rotate(degree)
    println("Triangle after rotation on $degree degrees:")
    usualTriangle.print()
}

class Triangle (var a: Point, var b:Point, var c:Point) {
    init {
        if(a.collinear(b,c)){
            throw Exception("Triangle could not be created (Points lie on the same line)")
        }else{
            this.b = b
            this.c = c
        }
    }
    fun print(){
        println("Triangle with coordinates $a, $b, $c")
    }

    private fun side(a: Point, b: Point): Double{
        return sqrt((b.x - a.x).toDouble().pow(2) +(b.y-a.y).toDouble().pow(2))
    }

    fun perimeter(): Double{
        return side(a,b)+side(b,c)+side(a,c)
    }

    fun area(): Double{
        val s: Double = perimeter()/2
        return sqrt(s*(s-side(a,b))*(s-side(b,c))*(s-side(a,c)))
    }

    fun rotate(degree: Int) {
        val centroid = Point((a.x+b.x+c.x)/3, (a.y+b.y+c.y)/3)
        val cos: Double
        val sin: Double
        when (degree) {
            0 -> {
                sin = 0.0
                cos = 1.0
            }
            90 -> {
                sin = 1.0
                cos = 0.0
            }
            180 -> {
                sin = 0.0
                cos = -1.0
            }
            270 -> {
                sin = -1.0
                cos = 0.0
            }
            360 -> {
                sin = 0.0
                cos = 1.0
            }
            else -> {
                val rad: Double = Math.toRadians(degree.toDouble())
                cos = cos(rad)
                sin = sin(rad)
            }
        }
        this.a = Point(cos*(a.x-centroid.x)+sin*(a.y-centroid.y)+centroid.x, -sin*(a.x-centroid.x)+cos*(a.y-centroid.y)+centroid.y)
        this.b = Point(cos*(b.x-centroid.x)+sin*(b.y-centroid.y)+centroid.x, -sin*(b.x-centroid.x)+cos*(b.y-centroid.y)+centroid.y)
        this.c = Point(cos*(c.x-centroid.x)+sin*(c.y-centroid.y)+centroid.x, -sin*(c.x-centroid.x)+cos*(c.y-centroid.y)+centroid.y)
    }

}

class Point(var x: Double = 0.0, var y: Double = 0.0){
    override fun toString(): String {
        return "($x, $y)"
    }
    constructor(x: Int, y: Int) : this() {
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

    fun collinear(p2: Point, p3: Point): Boolean {
        val area: Double = this.x*(p2.y-p3.y)+p2.x*(p3.y-this.y)+p3.x*(this.y-p2.y)
        return area == 0.0
    }
}
