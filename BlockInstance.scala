//> using scala 3.3
//> using option -unchecked -deprecation -Wunused:all -Wvalue-discard -Ysafe-init
//> using lib se.lth.cs::introprog::1.3.1

@main
def run: Unit = 
  BlockInstance().start()


class BlockInstance extends introprog.BlockGame(
  title                 = "BlockInstance",
  dim                   = (20,20),
  blockSize             = 30,
  background            = java.awt.Color.BLACK,
  framesPerSecond       = 50,
  messageAreaHeight     = 1,
  messageAreaBackground =  java.awt.Color.DARK_GRAY
):

    var movesPerSecond: Double = 1

    def millisBetweenMoves: Int = 
        (1000 / movesPerSecond).round.toInt max 1

    var _timestampLastMove: Long = System.currentTimeMillis

    def timestampLastMove = _timestampLastMove

    var x = 0
    var y = 0

    def move(): Unit = 
        if x == dim._1-1 then 
            x = -1
            y += 1 
        x = x+1

    def erase(): Unit =
        drawBlock(x, y, java.awt.Color.BLACK)
    
    def draw(): Unit =
        drawBlock(x, y, java.awt.Color.CYAN)

    def update(): Unit  =
        if System.currentTimeMillis >
                _timestampLastMove + millisBetweenMoves
        then
            move()
            _timestampLastMove = System.currentTimeMillis()
    
    var loopCounter: Int = 0


    override def gameLoopAction(): Unit = 
        erase()
        update()
        draw()
        clearMessageArea()
        drawTextInMessageArea(s"Loop number: $loopCounter", 5, 0, java.awt.Color.PINK)
        loopCounter += 1

    final def start(): Unit = 
        pixelWindow.show()  // möjliggör omstart även om fönstret stängts...
        gameLoop(stopWhen = x == 19 && y == 19)