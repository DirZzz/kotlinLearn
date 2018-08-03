package cpm.zjc.learn.testdemo

data class Card(val num: CardNum, val color: Color) {

}

enum class CardNum(var num: Int) {
    A(13), J(11), Q(12), K(13), N2(2), N3(3), N4(4), N5(5), N6(6), N7(7), N8(8), N9(9), N10(10)
}

enum class Color {
    HEIT, HONGT, FP, MH
}

data class Player(var cards: List<Card>) {

}

fun main(args: Array<String>) {
    CardGame.initCards()
    val cardGame = CardGame(playerNum = 3)
    cardGame.start()
}


class CardGame(playerNum: Int) {
    private val playerNum = playerNum

    companion object {
        private val cards = mutableListOf<Card>()
        fun initCards() {
            enumValues<CardNum>().forEach { num ->
                enumValues<Color>().forEach { color ->
                    val card = Card(num, color)
                    cards.add(card)
                }
            }
//            println(cards)
        }

    }
    fun start() {
        var curCards = cards
    }


}


