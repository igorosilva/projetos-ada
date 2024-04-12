public class Livro{
    private int id;
    private String titulo; 
    private String autor; 
    private String editora; 
    private String ISBN;
    private boolean disponivel; 

    public Livro(int id, String titulo, String autor, String editora, String ISBN, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.ISBN = ISBN;
        this.disponivel = disponivel;
    }

    public Livro(int id, String titulo, String autor, String editora, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Titulo: " + titulo + 
                ", Autor: " + autor +
                ", Editora: " + editora +
                ", ISBN: " + ISBN +
                ", Disponivel: " + (disponivel ? "Sim" : "Não");
    }

    public Livro getLivro() {
        return this;   
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}