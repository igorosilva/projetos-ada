public class Utils {

    public boolean validarISBN(String isbn) {
        // Remover traços e espaços em branco do ISBN
        isbn = isbn.replaceAll("[\\s-]", "");

        // Verificar se o ISBN tem 10 ou 13 dígitos
        if (isbn.length() != 10 && isbn.length() != 13) { //
            return false;
        }

        // Verificar se todos os caracteres são dígitos
        for (int i = 0; i < isbn.length() - 1; i++) {
            if (!Character.isDigit(isbn.charAt(i))) { // verifica se o caractere na posição i é um digito ou letra
                return false;
            }
        }

        // ----------Se o ISBN tiver 10 dígitos------
        if (isbn.length() == 10) {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (10 - i) * Character.getNumericValue(isbn.charAt(i));
            }

        
            return soma % 11 == 0;
        } else if (isbn.length() == 13) { 
        // ------------------Se o ISBN tiver 13 dígitos-------------------------------
        // para consultar https://homepages.dcc.ufmg.br/~rodolfo/aedsi-2-12/TP1.pdf
        // PARTE COMO CALCULAR
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                if (i % 2 == 0) {
                    soma += Character.getNumericValue(isbn.charAt(i));// vai retornar o valor numerico do char em string
                                                                      // ex "1" vale 1
                } else {
                    soma += 3 * Character.getNumericValue(isbn.charAt(i));
                }
            }
            int Resto = soma % 10;
            int checkDigit = (10 - Resto) % 10;
            return Character.getNumericValue(isbn.charAt(12)) == checkDigit;
        }

        return false;
    }
    }
    // -----------------------------------------------
     // 978-6558382072 isbno Manual do luto com 13 digitos
     // 978-6555651850 isbn O nome do vento com 13 digitos
     // 8580410320 isbn o temor do sabio com 10 digitos