public class Cards { // Esta classe é criada para cada cartão e armazena as informações de cada um deles

   // Variáveis para armazenar rank, naipe e valor da carta
   public String rank = "", suit = "";
   public int value = 0;

   Cards(String r, String s, int v) { // Construtor - inicializa valores
      this.rank = r;
      this.suit = s;
      this.value = v;
   }

   public void print() { // Debug - imprimir informações na carta
      System.out.printf("%s of %s, value %d\n", this.rank, this.suit, this.value);
   }

   public String getFileName() { // Obtenha o nome do arquivo da imagem desta carta
      if (value == 0) // Se esta for a carta virada do dealer (valor 0)
         return "res/cardImages/backCover.png";
      return String.format("res/cardImages/%s/%s.png", this.suit, this.rank); // Retorna o nome do arquivo
   }
}
