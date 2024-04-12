import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Biblioteca {

    private static List<Livro> livros = new ArrayList<>(); 
    private Utils utils = new Utils();

    public Livro lerLivro(Scanner scanner) {
        
        String titulo;
        do {
            System.out.print("Digite o título do livro: ");
            titulo = scanner.nextLine();
        } while (titulo.isEmpty() || titulo.isBlank());

        String autor;
        do {
            System.out.print("Digite o autor: ");
            autor = scanner.nextLine();
        } while(autor.isEmpty() || autor.isBlank());


        String editora;
        do {
            System.out.print("Digite a editora: ");
            editora = scanner.nextLine();
        } while(editora.isEmpty() || editora.isBlank());
        

        String isbn;
        int id = livros.size() + 1;
        do {
            System.out.print("Digite o ISBN: ");
            isbn = scanner.nextLine();
        } while (!utils.validarISBN(isbn));

        return new Livro(id, titulo, autor, editora, isbn,true);
        
    }

    public void adicionarLivroArray(Livro livro){
        livros.add(livro);
        System.out.println("O novo array de livros é: " + livros.toString());
    }


    public void removerLivro(int id) {
        for(Livro livro : livros) {
            if(livro.getId() == id) {
                livros.remove(livro);
                break;
            }
        }
    }

    public static void buscarLivrosPorTitulo(String titulo) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for(Livro livro: livros){
            if(livro.getTitulo().equalsIgnoreCase(titulo)){
                livrosEncontrados.add(livro);
            }
        }
        System.out.println(livrosEncontrados); 
    }

    public static void buscarLivrosPorAutor(String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for(Livro livro: livros) {
            if(livro.getAutor().equalsIgnoreCase(autor)) {
                livrosEncontrados.add(livro);
            }
        }
        System.out.println(livrosEncontrados);
    }

    public static void buscarLivrosPorISBN(String ISBN) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for(Livro livro: livros) {
            if(livro.getISBN().equalsIgnoreCase(ISBN)) {
                livrosEncontrados.add(livro);
            }
        }
        System.out.println(livrosEncontrados);
    }

    public static void buscarLivrosDisponiveis(boolean disponivel) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for(Livro livro: livros) {
            if(livro.getDisponivel() == disponivel) {
                livrosEncontrados.add(livro);
            }
        }
        System.out.println(livrosEncontrados);
    }

    public Livro encontrarLivroID(int id) {
        for (Livro livro : livros) {
            if(livro.getId() == id) {
                return livro;
            }
        }
        return null;
    }

    
    //listar livros disponiveis 
    
    public void emprestarLivro(Scanner scanner){

        listarLivros();
        System.out.println("Digite o ID do livro que deseja emprestar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Livro livro = encontrarLivroID(id);
        
        if(livro != null) {
            if(livro.getDisponivel() == true) {
                livro.setDisponivel(false);
                System.out.println("Livro emprestado:" + livro.getTitulo());
            } else {
                System.out.println("Livro já emprestado");
            }
        } 
        else {
            System.out.println("Livro não encontrado");
        }
    }

    public void devolverLivro(Scanner scanner){
        System.out.println("Digite o ID do livro que deseja devolver: ");
        int id = Integer.parseInt(scanner.nextLine());
        Livro livro = encontrarLivroID(id);
        
        if(livro != null) {
            if(livro.getDisponivel() == false) {
                livro.setDisponivel(true);
                System.out.println("Livro devolvido:" + livro.getTitulo());
            } else {
                System.out.println("Livro já está disponível");
            }
        } 
    }

    public List<Livro> listarTodosLivros() {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for(Livro livro: livros) {
            livrosEncontrados.add(livro);
        }
        return livrosEncontrados;
    }

    public void listarLivros() {
        System.out.println("Lista de Livros: ");
        for(Livro livro : livros) {
            System.out.println(livro);
        }
    }

    public void buscarLivroMenu(Scanner scanner) {
        Menu menu = new Menu();
        int opcao;

        do {
            menu.imprimirMenuBuscarLivro();

            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do livro que deseja buscar:");
                    String titulo = scanner.nextLine();
                    buscarLivrosPorTitulo(titulo);
                    break;
                case 2:
                    System.out.println("Digite o nome do autor:");
                    String autor = scanner.nextLine();
                    buscarLivrosPorAutor(autor);
                    break;
                case 3:
                    System.out.println("Digite o ISBN:");
                    String ISBN = scanner.nextLine();
                    buscarLivrosPorISBN(ISBN);
                    break;
                case 4:
                    System.out.println("Digite o nome do autor:");
                    boolean disponivel = Boolean.parseBoolean(scanner.nextLine());
                    buscarLivrosDisponiveis(disponivel);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Parâmetro inválido!");
            }

        } while (opcao < 1 || opcao > 5);
    }
}