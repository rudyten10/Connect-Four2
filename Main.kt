package connectfour

//stage 2/5
fun main() {
    var rows = 0
    var cols = 0

    val validRegex = Regex("([0-9][0-9]?)x([0-9][0-9]?)")
    val validRangeRegex = Regex("([5-9])x([5-9])")

    println("Connect Four")
    println("First player's name:")
    val fPlayerName = readLine()!!
    println("Second player's name:")
    val sPlayerName = readLine()!!

    // loop@ while (true) {
    while (true) {
        //println("+++++++++++")
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        //val input = "5 X 6".trim().replace(" ", "").lowercase()
        //var input = readLine()!!.trim().replace(" ", "").lowercase()
        //var input = readLine()!!.trim().replace("\\s+".toRegex(), "").lowercase()
        var input = readLine()!!.trim().replace("\\s+".toRegex(), "").lowercase()

        if (input == "") {
            rows = 6
            cols = 7
            break
        }

        if (input.matches(validRegex)) {
            val tmp = input.split("x")
            rows = tmp[0].toString().toInt()
            cols = tmp[1].toString().toInt()
            if (input.matches(validRangeRegex)) {
                break
            } else {
                if (rows < 5 ||rows > 9) {
                    println("Board rows should be from 5 to 9")
                } else if (cols < 5 || cols > 9) {
                    println("Board columns should be from 5 to 9")
                }
                //continue@loop
            }
        } else {
            //println("2222+++++++++++")
            println("Invalid input")
            //continue@loop
        }
    }

    println("$fPlayerName VS $sPlayerName")
    println("$rows X $cols board")
    //var i = 0
    for (i in 1..cols) {
        print(" $i")
    }
    //println()
    for (i in 1..rows) {
        println()
        for (i in 1..cols+1) {
            print("║ ")
        }
    }
    println()
    for (i in 1..cols*2) {
        if (i == 1)
            print("╚")

        if (i == cols*2)
            print("╝ ")
        else {
            if (i % 2 == 0)
                print("╩")
            else
                print("═")
        }
    }




}