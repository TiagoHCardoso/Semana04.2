// Ainda não resolvido
public class Main {

	public static Filosofos[] filosofos = new Filosofos[5];
	
	public static volatile Object[] hashi = new Object[5];

  public static volatile boolean[] mutex = new boolean[5]; 
	
	public static void main(String[] args) {
    // Todos os hashis são inicializados.
    for(int i = 0; i < 5; i++)
      mutex[i]= true;

    // Todos os hashis são inicializados.
    for(int i = 0; i < 5; i++)
      hashi[i]= new Object();
    
    // Todos os filósofos são inicializados.
    for(int i = 0; i < 5; i++)
      filosofos[i] = new Filosofos(i);
    
    // Todos os filósofos-thread são acordados.
    filosofos[1].start();
    filosofos[3].start();
    passarTempo();
    filosofos[0].start();
    filosofos[2].start();
    filosofos[4].start();
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * *  
     *  É necessário que se passe um tempo para que os filó-
     *sofos [0], [2] e [4]  sejam introduzidos. Isto  impede  
     *que ocorra o Deadlock logo de cara.
     *  O Deadlock ocorreria quando todos pegassem o hashi 
     *da esquerda, não podendo entrar na zona sincronizada
     *aninhada para pegar o recurso da direita.
     * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	}

  public static void passarTempo(){
    for(int i = 0; i < 10000; i++){
      System.out.print("."); 
    }
  }
}

class Filosofos extends Thread {
	private final int id;
  private final String[] filosofos = new String[] {
    "Piton", 
    "Leandro Kernel", 
    "Sergio Cortana", 
    "Windows Nunes", 
    "Adobe Hitler"};

  public Filosofos(int id) {
    this.id = id;
  }

	public void run() {
    // Mantem os filófofos repetindo a ação de pegar os hashis.
		while(true){
      synchronized(Main.hashi[id]) {
          // Aqui o primeiro a pegar o hashi [id] não permitirá que
          // outro o pegue.
          System.out.print("\n" + filosofos[id] + " com hashi (" + 
            id + ") na mão.");

        synchronized(Main.hashi[(id+1)%5]) {
          // Aqui o primeiro a pegar o hashi [(id+1)%5] não permitirá que
          // outro o pegue.
          System.out.print("\n" + filosofos[id] + " com hashi (" + 
            (id+1)%5 + ") na mão. Comendo... Já já estarei filosofando...");
        }
      }
      /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
       *  O  hashi [id]  significa  que  o filósofo [0]  pegará  o hashi [0]; 
       *o filósofo [1] pegará o hashi [1]; o filósofo [2] pegará o hashi [2]; 
       *o filósofo [3] pegará o hashi [3]; o filósofo [4] pegará o hashi [4].
       *Ou seja o hashi que se convencionou ser o da esquerda.
       *  O hashi [(id+1)%5] significa que o filósofo [0] pegará o hashi [1]; 
       *o filósofo [1] pegará o hashi [2]; o filósofo [2] pegará o hashi [3]; 
       *o filósofo [3] pegará o hashi [4]; o filósofo [4] pegará o hashi [0]. 
       *Ou seja o hashi que se convencionou ser o da direita.
       * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    }
	}

}