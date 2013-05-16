import java.io._
val (w,h) = (26, 98)
val s = "WB"*(w/2)
val ss = 1 to h map (_ => s) mkString ","
val fw = new FileWriter("input.txt")
fw write "newGame %d %d " format (w h)
fw write ss
fw write "\nquit"
fw.close()
println("finished")

