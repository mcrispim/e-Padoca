const val CATEGORIA = 0
const val PRODUTO = 1
const val PRECO = 2

const val QUANTIDADE = 3
const val TOTAL = 4

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
    val carrinho = mutableListOf<List<String>>()
    val categorias = calculateCategorias()
    var opcaoCategoria: String
    while (true) {
        opcaoCategoria = escolhaMenuPrincipal(categorias)
        if (opcaoCategoria == FINALIZAR && carrinho.isEmpty()) {
            print("Deseja mesmo cancelar compra? (SIM para cancelar)")
            if (readln().uppercase() == "SIM") {
                opcaoCategoria = CANCELAR
            }
        }
        if (opcaoCategoria == FINALIZAR || opcaoCategoria == CANCELAR) {
            break
        }
        val itens = cardapio.filter { item -> item[CATEGORIA] == opcaoCategoria }
        val opcaoItem = escolhaMenuCategoria(itens)
        val item = itens.filter { item -> item[PRODUTO] == opcaoItem }.first()
        adicionarItem(item, carrinho)
        println()
    }
    if (opcaoCategoria == CANCELAR) {
        println("Compra cancelada.")
    } else {
        mostrarCarrinho(carrinho)
    }
}

fun mostrarCarrinho(carrinho: MutableList<List<String>>) {
    var valorTotal = 0.0
    println()
    println("======================= Comanda E-padoca ========================")
    println("Item       Produto         Qtd                Valor         Total")
    for ((i, item) in carrinho.withIndex()) {
        val nome = item[PRODUTO]
        val quantidade = item[QUANTIDADE]
        val preco = item[PRECO]
        val totalItem = item[TOTAL]
        valorTotal += totalItem.replace(",", ".").toDouble()

        val nomeQuant = separarItensComPontos(nome, quantidade, 19)
        val frente = separarItensComPontos(i.toString(), nomeQuant, 30)
        val traseira = separarItensComPontos("R$ " + preco, "R$ " + totalItem, 21)
        val linha = separarItensComPontos(frente, traseira, 65)
        println(linha)
    }
    println("=================================================================")
    println(separarItensComPontos("Total ", " R$ %.2f".format(valorTotal), 65))
    println("======================= VOLTE SEMPRE ============================")
}

private fun adicionarItem(item: List<String>, carrinho: MutableList<List<String>>) {
    val valorUnitario = item[PRECO].replace(",", ".").toDouble()
    println("Opção escolhida: ${item[CATEGORIA]} - ${item[PRODUTO]} - Valor unitário: ${item[PRECO]}")
    print("Quantidade desejada (digite 0 para cancelar): ")
    val quantidade = readln().toIntOrNull() ?: 0
    if (quantidade > 0) {
        val itemCarrinho = item.toMutableList()
        itemCarrinho.add(quantidade.toString())
        itemCarrinho.add("%.2f".format(quantidade * valorUnitario).replace(".", ","))
        carrinho.add(itemCarrinho)
        println("Adicionados $quantidade ${item[PRODUTO]}(s) ao carrinho.")
        println()
    }
}

fun escolhaMenuCategoria(itens: List<List<String>>): String {
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
        println("============================================")
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
