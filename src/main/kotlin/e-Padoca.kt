val cardapio = listOf(
    // Categoria: Pães
    listOf("Pães",      "Carioquinha",      "0,60"),
    listOf("Pães",      "Pão de forma",     "8,00"),
    listOf("Pães",      "Brioche",          "1,20"),
    listOf("Pães",      "Pão doce",         "0,70"),
    // Categoria: Salgados
    listOf("Salgados",  "Coxinha",          "1,50"),
    listOf("Salgados",  "Pastel",           "1,30"),
    listOf("Salgados",  "Empada",           "1,10"),
    // Categoria: Doces
    listOf("Doces",     "Sonho",            "0,90"),
    listOf("Doces",     "Brigadeiro",       "0,70"),
    listOf("Doces",     "Pé-de-moleque",    "0,60"),
    listOf("Doces",     "Maria-mole",       "0,90"),
    // Categoria: Bebidas
    listOf("Bebidas",   "Suco de laranja",  "1,50"),
    listOf("Bebidas",   "Água",             "1,00"),
    listOf("Bebidas",   "Café",             "0,50"),
    listOf("Bebidas",   "Refrigerante",     "1,80")
)

const val FINALIZAR = "Finalizar compra"
const val CANCELAR = "Cancelar compra"

fun main() {
    val categorias = calculateCategorias()
    var opcaoCategoria = ""
    while (opcaoCategoria != FINALIZAR && opcaoCategoria != CANCELAR) {
        opcaoCategoria = escolhaMenuPrincipal(categorias)
        val itens = cardapio.filter { item -> item[0] == opcaoCategoria }
        val opcaoItem = escolhaMenuCategoria(itens)
        println("Opção escolhida: $opcaoCategoria - $opcaoItem")
        println()
    }
    println("Opção: $opcaoCategoria")
}

fun escolhaMenuCategoria(itens: List<List<String>>): Any {
    val categoria = itens[0][0]
    var opcao = ""
    var opcaoInvalida = true
    while (opcaoInvalida) {
        println("Menu da categoria $categoria:")
        println()
        for ((i, item) in itens.withIndex()) {
            println("[${i + 1}] ${separarItensComPontos(item[1], "R$ " + item[2])}")
        }
        println()
        println("[X] Voltar ao menu principal")
        println()
        print("Selecione uma das opções acima: ")

        opcao = readln().trim().uppercase()
        if (opcao != "X" && opcao.toIntOrNull() !in 1..itens.size) {
            println("$opcao <== Opção Inválida")
            println("============================================")
            println()
        } else {
            opcaoInvalida = false
        }
    }
    return when (opcao) {
        "X" -> CANCELAR
        else -> itens[opcao.toInt() - 1][1]
    }
}

fun calculateCategorias(): List<String> {
    val categoriasSet = mutableSetOf<String>()
    for (item in cardapio) {
        categoriasSet.add(item[0])
    }
    return categoriasSet.toList()
}

fun escolhaMenuPrincipal(categorias: List<String>): String {
    var opcao = ""
    var opcaoInvalida = true
    while (opcaoInvalida) {
        println("Menu Principal")
        println()
        for ((i, categoria) in categorias.withIndex()) {
            println("[${i + 1}] $categoria")
        }
        println()
        println("[0] Finalizar compra")
        println("[X] Cancelar compra")
        println()
        print("Selecione uma das opções acima: ")

        opcao = readln().trim().uppercase()
        if (opcao != "X" && opcao.toIntOrNull() !in 0..categorias.size) {
            println("$opcao <== Opção Inválida")
            println("============================================")
            println()
        } else {
            opcaoInvalida = false
        }
    }
    return when (opcao) {
        "0" -> FINALIZAR
        "X" -> CANCELAR
        else -> categorias[opcao.toInt() - 1]
    }
}

fun separarItensComPontos(itemA: String, itemB: String, tamanho: Int = 40): String {
    if (itemA.length + itemB.length + 1 >= tamanho) {
        return "$itemA $itemB"
    }
    return "$itemA${".".repeat(tamanho - itemA.length - itemB.length)}${itemB}"
}