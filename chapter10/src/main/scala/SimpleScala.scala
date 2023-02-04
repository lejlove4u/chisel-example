
object SimpleScala {
	def main(args: Array[String]) = {
		println("Hello, scala")

		// A value is a constant
		val zero = 0

		// No new assignment is possible
		// The following will not compile
		// zero = 3

// --------------------------------------------------------------------------------
// sbt:chapter10> compile
// [error] ... SimpleScala.scala:11:8: reassignment to val
// [error]                 zero = 3
// [error]                      ^
// [error] one error found
// [error] (Compile / compileIncremental) Compilation failed
// [error] Total time: 1 s, completed 2023. 2. 4 ?ㅼ쟾 9:01:42
// --------------------------------------------------------------------------------

		var x = 2
		println("before " + x)
		x = 3
		println("After " + x)

// --------------------------------------------------------------------------------
// sbt:chapter10> run
// [info] running SimpleScala
// before 2
// After 3
// [success] Total time: 0 s, completed 2023. 2. 4 ?ㅼ쟾 9:04:27
// --------------------------------------------------------------------------------

		// Loop from 0 to 9
		// Automatically creates loop value cnt
		for(cnt <- 0 until 10) {
			print(cnt)
			if(cnt == 9) {
				println()
			} else {
				print(s", ")
			}
			//print(s"${cnt}, ")
		}

// --------------------------------------------------------------------------------
// sbt:chapter10> run
// [info] running SimpleScala
// 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
// [success] Total time: 1 s, completed 2023. 2. 4 ?ㅼ쟾 9:17:18
// --------------------------------------------------------------------------------

		val city = (2000, "Frederiksberg")
		val zipCode = city._1
		val name = city._2

		println(s"ZIP code is ${zipCode}")
		println(s"Name is ${name}")
	
// --------------------------------------------------------------------------------
// sbt:chapter10> run
// [info] compiling 1 Scala source to ... classes ...
// 
// Multiple main classes detected. Select one to run:
//  [1] ScalaGen
//  [2] SimpleScala
// 
// Enter number: 2
// [info] running SimpleScala
// ZIP code is 2000
// Name is Frederiksberg
// [success] Total time: 6 s, completed 2023. 2. 4 ?ㅼ쟾 10:12:21}
// --------------------------------------------------------------------------------
	
		val numbers = Seq(1, 15, -2, 0)
		val second = numbers(1)

// --------------------------------------------------------------------------------
// printsbt:chapter10> run SimpleScala
// [info] compiling 1 Scala source to ... classes ...
// 
// Multiple main classes detected. Select one to run:
//  [1] ScalaGen
//  [2] SimpleScala
// 
// Enter number: 2
// [info] running SimpleScala SimpleScala
// The second value is 15
// [success] Total time: 2 s, completed 2023. 2. 4 ?ㅼ쟾 10:16:07ln(s"The second value is ${second}")
// --------------------------------------------------------------------------------

	}
}