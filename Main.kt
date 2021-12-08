package connectfour

// Stage 5/5
fun main() {
    var rows = 0
    var cols = 0
    var currentGameCnt = 0
    var toggleTurn = 1
    val validNumericRegex = Regex("[0-9]+")
    var move = ""
    var playerOneWonCnt = 0
    var playerTwoWonCnt = 0

    println("Connect Four")
    println("First player's name:")
    val fPlayerName = readLine()!!
    println("Second player's name:")
    val sPlayerName = readLine()!!

    val dim = setGameDimensions()
    rows = dim[0]
    cols = dim[1]

    val numberOfGames = setGameNumber()

    println("$fPlayerName VS $sPlayerName")
    println("$rows X $cols board")

    if (numberOfGames == 1) {
        println("Single game")
    } else {
        println("Total $numberOfGames games")
        println("Game #${currentGameCnt+1}")
    }



    var stateList = MutableList(rows) { MutableList(cols) { 0 } }


    loopMain@ while (true) { ///// Game loop
        if (currentGameCnt == numberOfGames){
            break@loopMain
        } else {
            if (numberOfGames > 1) {
                stateList = MutableList(rows) { MutableList(cols) { 0 } }
            }
            displayGameBoard(rows, cols, stateList)
        }

        while (true) {
            if (toggleTurn == 1) {
                println("$fPlayerName's turn:")
            } else {
                println("$sPlayerName's turn:")
            }

            move = readLine()!!.trim().replace("\\s+".toRegex(), "").lowercase()

            var exitCheck = gameRoundCheck(move)
            if (exitCheck) {
                break@loopMain
            }

            if (!move.matches(validNumericRegex)) {
                println("Incorrect column number")
                continue
            }

            val colNo: Int = move.toInt()

            if (colNo == 0 || colNo > cols) {
                println("The column number is out of range (1 - $cols)")
                continue
            }

            //---validateColumnNo()
            val tempCol = getColumn(colNo, stateList)

            if (!tempCol.contains(0)){
                println("Column $colNo is full")
                continue
            }

            //---validateInput()
            makeMove(toggleTurn, colNo, stateList, tempCol)

            val winner = analazeGameRows(toggleTurn, stateList)
            val draw = analyzeGameDraw(toggleTurn, stateList)

            if (winner || draw) {
                displayGameBoard(rows, cols, stateList)

                currentGameCnt++
                if (winner) {
                    if (toggleTurn == 1) {
                        println("Player $fPlayerName won")
                        playerOneWonCnt += 2
                    } else {
                        println("Player $sPlayerName won")
                        playerTwoWonCnt += 2
                    }
                }

                if (draw) {
                    println("It is a draw")
                    playerOneWonCnt++
                    playerTwoWonCnt++
                }

                if (toggleTurn == 1) {
                    toggleTurn = 2
                } else {
                    toggleTurn = 1
                }

                if (numberOfGames > 1) {
                    println("Score")
                    println("$fPlayerName: $playerOneWonCnt $sPlayerName: $playerTwoWonCnt")
                    if (numberOfGames != currentGameCnt) {
                        println("Game #${currentGameCnt + 1}")
                    } else {
                        println("Game over!") // 44444
                    }

                    break
                } else {
                    println("Game over!") //2222
                    break@loopMain
                }
            }

            if (toggleTurn == 1) {
                toggleTurn = 2
            } else {
                toggleTurn = 1
            }

            displayGameBoard(rows, cols, stateList)
        }
    } ///// Game loop
}


fun analyzeGameDraw(player:Int, stateList: MutableList<MutableList<Int>>) :Boolean { //: MutableList<Int>
    var tieGame = true
    for (i in stateList){
        if (i.contains(0)){
            tieGame = false
        }
    }
    return tieGame
}

fun gameRoundCheck(move: String): Boolean {
    if (move == "end") {
        println("Game over!") // end
        return true
    } else {
        return false
    }
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

fun analazeGameRows(player:Int, stateList: MutableList<MutableList<Int>>) :Boolean { //: MutableList<Int>

    //check for 4 across
    for (row in stateList.indices) {
        for (col in 0 until stateList[0].size - 3) {
            if (stateList[row][col] == player &&
                stateList[row][col + 1] == player &&
                stateList[row][col + 2] == player &&
                stateList[row][col + 3] == player
            ) {
                return true
            }
        }
    }

    //check for 4 up and down
    for (row in 0 until stateList.size - 3) { // - 4
        for (col in 0 until stateList[0].size) {
            if (stateList[row + 0][col] == player &&
                stateList[row + 1][col] == player &&
                stateList[row + 2][col] == player &&
                stateList[row + 3][col] == player) {
                return true
            }
        }
    }

    //check upward diagonal
    for (row in 2 until stateList.size) {
        for (col in 0 until stateList[0].size - 3) { // - 4
            if (stateList[row][col] == player &&
                stateList[row - 1][col + 1] == player &&
                stateList[row - 2][col + 2] == player &&
                stateList[row - 3][col + 3] == player) {
                return true
            }
        }
    }
    //check downward diagonal
    for (row in 0 until stateList.size - 3) { // - 4
        for (col in 0 until stateList[0].size - 3) { // - 4
            if (stateList[row][col] == player &&
                stateList[row + 1][col + 1] == player &&
                stateList[row + 2][col + 2] == player &&
                stateList[row + 3][col + 3] == player) {
                return true
            }
        }
    }
    return false
}



fun getColumn(colNo: Int, stateList: MutableList<MutableList<Int>>) : MutableList<Int> {
    val tmpArr = MutableList<Int>(stateList.size) { 0 }

    for (i in stateList.indices) {
        tmpArr[i] =  stateList[i][colNo-1]
    }

    return tmpArr
}


fun setGameNumber(): Int {
    val validGameInputRangeRegex = Regex("[1-9]+")

    while (true) {
        println("Do you want to play single or multiple games?")
        println("For a single game, input 1 or press Enter")
        println("Input a number of games:")
        var input = readLine()!!.trim().replace("\\s+".toRegex(), "").lowercase()

        if (input == ""){
            //numberOfGames = 1
            //break
            return 1
        }

        if (input.matches(validGameInputRangeRegex)) {
            return input.toInt()
            //numberOfGames = input.toInt()
            //break
        } else {
            println("Invalid input")
        }
    }
}

fun setGameDimensions(dftRows: Int = 6, dftCols: Int = 7): List<Int> {
    val validRegex = Regex("([0-9][0-9]?)x([0-9][0-9]?)")
    val validRangeRegex = Regex("([5-9])x([5-9])")

    var rows = 0
    var cols = 0

    while (true) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        var input = readLine()!!.trim().replace("\\s+".toRegex(), "").lowercase()

        if (input == "") {
            rows = dftRows
            cols = dftCols
            //break
            return listOf<Int>(rows, cols)
        }

        if (input.matches(validRegex)) {
            val tmp = input.split("x")
            rows = tmp[0].toString().toInt()
            cols = tmp[1].toString().toInt()
            if (input.matches(validRangeRegex)) {
                //break
                return listOf<Int>(rows, cols)
            } else {
                if (rows < 5 || rows > 9) {
                    println("Board rows should be from 5 to 9")
                } else if (cols < 5 || cols > 9) {
                    println("Board columns should be from 5 to 9")
                }
            }
        } else {
            println("Invalid input")
        }
    }
}
