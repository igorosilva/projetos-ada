import java.util.Scanner;

public class Aplicacao{

    public static void main(String[] args) {

        Menu menu = new Menu(); 

        Biblioteca biblioteca = new Biblioteca();

        Scanner scanner = new Scanner(System.in);

        int selecao = 0; 

        do{
            menu.imprimirMenuInicial();
             
            selecao = Integer.parseInt(scanner.nextLine());

            switch(selecao){
                case 1:
                    biblioteca.adicionarLivroArray(biblioteca.lerLivro(scanner));
                    break;
                case 2:
                    biblioteca.buscarLivroMenu(scanner);
                    // scanner.close();
                    break;
                case 3:
                    biblioteca.emprestarLivro(scanner);
                    break;
                case 4: 
                    biblioteca.devolverLivro(scanner);
                    break;
                case 5: 
                    biblioteca.listarLivros();
                    break;
                case 6: 
                    break;
            }
        } while (selecao != 6);
        scanner.close();
    }
}