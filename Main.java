public class Main {

	public static Filosofos[] filosofos = new Filosofos[5];
	
	public static volatile Object[] hashi = new Object[5];

  // Criação de um mutex
  public static volatile Object mutex = new Object();
	
	public static void main(String[] args) {
    for(int i = 0; i < 5; i++)
      hashi[i] = new Object();
    
    for(int i = 0; i < 5; i++)
      filosofos[i] = new Filosofos(i);
    
    filosofos[1].start();
    filosofos[3].start();
    passarTempo();
    filosofos[0].start();
    filosofos[2].start();
    filosofos[4].start();
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
		while(true){
      /*
        Este novo synchronized tem o papel de um mutex - uma
        das soluções possíveis pra o problema do jantar dos 
        filósofos. Entretanto, ele cria o problema de apenas
        um filósofo poder comer por vez. Uma melhor solução seriam
        os semáforos.
      */
      synchronized(Main.mutex){
        synchronized(Main.hashi[id]) {
            System.out.print("\n" + filosofos[id] + " com hashi (" + 
              id + ") na mão.");

          synchronized(Main.hashi[(id+1)%5]) {
            System.out.print("\n" + filosofos[id] + " com hashi (" + 
              (id+1)%5 + ") na mão. Comendo... Já já estarei filosofando...");
          }
        }
      }
	  }
  }
}
