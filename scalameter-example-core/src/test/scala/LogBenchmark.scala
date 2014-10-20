import org.scalameter.api._

object LogBenchmark
  extends PerformanceTest.Quickbenchmark {
  val sizes = Gen.range("size")(30000, 150000, 30000)

  val ranges: Gen[Range] = for {
    size <- sizes
  } yield 0 until size

  performance of "log" in {
    measure method "math.log" in {
      using(ranges) in {
        r =>
          r.foreach(v => math.log(v.toDouble))
      }
    }
    measure method "apache.log" in {
      using(ranges) in {
        r =>
          r.foreach(v => org.apache.commons.math3.util.FastMath.log(v.toDouble))
      }
    }
  }
  performance of "exp" in {
    measure method "math.exp" in {
      using(ranges) in {
        r =>
          r.foreach(v => math.exp(v.toDouble))
      }
    }
    measure method "apache.exp" in {
      using(ranges) in {
        r =>
          r.foreach(v => org.apache.commons.math3.util.FastMath.exp(v.toDouble))
      }
    }
  }
}