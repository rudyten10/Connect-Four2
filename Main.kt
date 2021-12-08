package connectfour

// state 3/5
fun main() {
    var rows = 0
    var cols = 0

//    val col1 = mutableListOf<String>("1a", "2a", "3a", "4a", "5a", "6a")
//    val col2 = MutableList(6) { "2" }
//    val col3 = MutableList(6) { "3" }
//    val col4 = MutableList(6) { "4" }
//    val col5 = MutableList(6) { "5" }
//    val col6 = MutableList(6) { "6" }
//    val stateList = mutableListOf<MutableList<String>>(col1, col2, col3, col4, col5, col6 )
//    println(stateList.reversed())
//    println(stateList[0][0])
//    println(stateList[0][1])
//    println(stateList[0][2])
//    println(stateList[0][3])
//    println(stateList[1][0])
    var cnt = 0

    var toggleTurn = 1

    val validRegex = Regex("([0-9][0-9]?)x([0-9][0-9]?)")
    val validRangeRegex = Regex("([5-9])x([5-9])")

    //val validMoveRange = Regex("[1-5]")
    val validNumericRegex = Regex("[0-9]+")

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

    var move = ""

    val stateList = MutableList(rows) { MutableList(cols) { 0 } }
    displayGameBoard(rows, cols, stateList)

    //cnt = 0
    while (true) {
        //if (cnt++ > 25) {
        //    break
        //}


        if (toggleTurn == 1) {
            println("$fPlayerName's turn:")
        } else {
            println("$sPlayerName's turn:")
        }

        move = readLine()!!.trim().replace("\\s+".toRegex(), "").lowercase()

        if (move == "end") {
            println("Game over!")
            break
        }

        if (!move.matches(validNumericRegex)) {
            println("Incorrect column number")
            continue
        }

        val colNo: Int = move.toInt()

        //if (!move.matches(validMoveRange)) {
        if (colNo == 0 || colNo > cols) {
            println("The column number is out of range (1 - $cols)")
            continue
        }


        //---validateColumnNo()
        val tempCol = getColumn(colNo, stateList)

        //println(tempCol)
        if (!tempCol.contains(0)){
            println("Column $colNo is full")
            continue
        }

        //---validateInput()
        //println(stateList)
        makeMove(toggleTurn, colNo, stateList, tempCol)

        analazeGameRows(stateList)

        if (toggleTurn == 1) {
            toggleTurn = 2
        } else {
            toggleTurn = 1
        }


        //println(stateList)
        //stateList[3][4] = 1
        //stateList[4][4] = 1

        //println(tempCol)


        updateGameState(stateList)
        displayGameBoard(rows, cols, stateList)

    }
}

fun updateGameState(stateList: MutableList<MutableList<Int>>) {
    //println(stateList)
}

fun displayGameBoard(rows: Int, cols: Int, stateList: MutableList<MutableList<Int>>) {
    //var i = 0
//    toggleTurn: Int,
//    if (toggleTurn == 1) { // 1 == "o" and 2 == "*"
//
//    }


    for (i in 1..cols) {
        print(" $i")
    }
    //println()
    for (i in 0..rows-1) {
        println()
        for (j in 0..cols-1) {
            print("║")
            if (stateList[i][j] == 1){
                print("o")
            } else if (stateList[i][j] == 2){
                print("*")
            } else {
                print(" ")
            }
            //print("${stateList[i-1][j-1]}")
            //print("${i-1}-${j-1}  ${stateList[i-1][j-1]}")
        }
        print("║")
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


//    for (list in stateList) {
//        println("+++++++++")
//        for (inlist in list) {
//            println("$inlist ----")
//        }
//
//    }
    println()
}

fun makeMove(toggleTurn: Int, colNo: Int, stateList: MutableList<MutableList<Int>>, colList: MutableList<Int>)  { //: MutableList<Int>

    var j = 0

    for (i in colList.indices.reversed() ) {

        if (colList[i] == 0) {
            stateList[i][colNo-1] = toggleTurn // 1 == "o" and 2 == "*"
            break
        }
        j++
    }
}

fun analazeGameRows(stateList: MutableList<MutableList<Int>>)  { //: MutableList<Int>
    //println(stateList)
//    for (colIdx in stateList.indices) {
//    }


    for (colIdx in stateList[0].indices) {
        var tmpArr = getColumn(colIdx+1, stateList)
        var tmpStr = ""
        for (i in tmpArr) {
            tmpStr += i
        }

        //println("$colIdx --- $tmpStr")

        if (tmpStr.contains("1111") || tmpStr.contains("2222")) {
            //println("WINNER COLUMN YAYYYYYYY")
            break
        }
    }


    for (rowIdx in stateList.indices) {
        var tmpArr = getRow(rowIdx, stateList)
        var tmpStr = ""
        for (i in tmpArr) {
            tmpStr += i
        }

        if (tmpStr.contains("1111") || tmpStr.contains("2222")) {
            //println("WINNER ROW YAYYYYYYY")
            break
        }
       // print("ppo")

//        for (rowIdx in stateList.indices) {
//            for (colIdx in stateList[rowIdx].indices) {
//                if (stateList[rowIdx][colIdx])
//            }
//
//        }

        //if (tmpArr.contains())
    }


//    for (rowIdx in stateList.indices) {
//        for (colIdx in stateList[rowIdx].indices) {
//            //println("$rowIdx-$colIdx = ${stateList[rowIdx][colIdx]}")
//            if (colIdx+4 > stateList[rowIdx].lastIndex+1){
//                break
//            }
//            if (stateList[rowIdx][colIdx] > 0
//                && stateList[rowIdx][colIdx] == stateList[rowIdx][colIdx+1]
//                && stateList[rowIdx][colIdx] == stateList[rowIdx][colIdx+2]
//                && stateList[rowIdx][colIdx] == stateList[rowIdx][colIdx+3]) {
//                println("YAYYYYYYY")
//                break
//            }
//        }
//    }
}

fun winnerCheck() {

}

fun getColumn(colNo: Int, stateList: MutableList<MutableList<Int>>) : MutableList<Int> {
    val tmpArr = MutableList<Int>(stateList.size) { 0 }

    for (i in stateList.indices) {
        tmpArr[i] =  stateList[i][colNo-1]
    }

    return tmpArr
    //return mutableListOf(5,6,7,0,0)
}

fun getRow(rowNo: Int, stateList: MutableList<MutableList<Int>>) : MutableList<Int> {
    //val tmpArr = MutableList<Int>(stateList.size) { 0 }

    //stateList[rowNo]

    return stateList[rowNo]
    //return mutableListOf(5,6,7,0,0)
}

