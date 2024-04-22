//package tech.ada.school.service;
//
//import org.springframework.stereotype.Service;
//import tech.ada.school.domain.dto.exception.NotFoundException;
//import tech.ada.school.domain.dto.v1.AlunoDto;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//@Service
//public class AlunoService {
//    private final List<AlunoDto> alunos = new ArrayList<>();
//    private int id = 1;
//
////    @Override
//    public AlunoDto criarAluno(AlunoDto novoAluno) {
//        final AlunoDto a =  new AlunoDto(
//                id++,
//                novoAluno.getNome(),
//                novoAluno.getTurma(),
//                novoAluno.getEmail(),
//                novoAluno.getNotas(),
//                novoAluno.getMediaNotas(),
//                1
//        );
//        alunos.add(a);
//        return a;
//    }
//
//    private double calcularMedia (List<Double> notas) {
//        return notas.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
//    }
//
////    @Override
//    public List<Double> postarNota(int id, double nota) throws NotFoundException {
//        Optional<AlunoDto> aluno = alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst();
//        if(aluno.isPresent()) {
//            List<Double> notas = aluno.get().getNotas();
//            notas.add(nota);
//            aluno.get().setNotas(notas);
//            aluno.get().setMediaNotas(calcularMedia(notas));
//            return notas;
//        }
//        return null;
//    }
//
////    @Override
//    public List<Double> postarNotas(int id, List<Double> notas) throws NotFoundException {
//        Optional<AlunoDto> aluno = alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst();
//        if(aluno.isPresent()) {
//            aluno.get().getNotas().addAll(notas);
//            aluno.get().setMediaNotas(calcularMedia(notas));
//            return aluno.get().getNotas();
//        }
//        return null;
//    }
//
////    @Override
//    public List<AlunoDto> listarAlunos() {
//        return alunos;
//    }
//
////    @Override
//    public List<AlunoDto> buscarTurma(String turma) {
//        Stream<AlunoDto> listaAlunos = alunos
//                .stream()
//                .filter(it -> it.getTurma().equals(turma));
//        if(!listarAlunos().isEmpty()) {
//            return listaAlunos.toList();
//        } else {
//            return null;
//        }
//    }
//
////    @Override
//    public AlunoDto buscarAluno(int id) throws NotFoundException {
//        return alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst()
//                .orElse(null);
//    }
//
////    @Override
//    public AlunoDto atualizarAluno(int id, AlunoDto pedido) {
//        final AlunoDto aluno = alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst()
//                .orElse(null);
//        if(aluno == null) {
//            return null;
//        }
//        alunos.remove(aluno);
//        final AlunoDto a = new AlunoDto(
//                pedido.getId(),
//                pedido.getNome(),
//                pedido.getTurma(),
//                pedido.getEmail(),
//                pedido.getNotas(),
//                pedido.getMediaNotas(),
//                pedido.getSemestreAtual()
//        );
//        alunos.add(a);
//        return a;
//    }
//
////    @Override
//    public AlunoDto atualizarNomeAluno(int id, String nome) {
//        final AlunoDto aluno = alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst()
//                .orElse(null);
//        if(aluno == null) {
//            return null;
//        }
//        aluno.setNome(nome);
//        return aluno;
//    }
//
////    @Override
//    public AlunoDto atualizarTurmaAluno(int id, String turma) {
//        final AlunoDto aluno = alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst()
//                .orElse(null);
//        if(aluno == null) {
//            return null;
//        }
//        aluno.setTurma(turma);
//        return aluno;
//    }
//
////    @Override
//    public AlunoDto atualizarEmailAluno(int id, String email) {
//        final AlunoDto aluno = alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst()
//                .orElse(null);
//        if(aluno == null) {
//            return null;
//        }
//        aluno.setEmail(email);
//        return aluno;
//    }
//
////    @Override
//    public AlunoDto atualizarNotasAluno(int id, List<Double> notas) {
//        final Optional<AlunoDto> aluno = alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst();
//        if(aluno.isPresent()) {
//            AlunoDto a = aluno.get();
//            a.getNotas().clear();
//            a.getNotas().addAll(notas);
//            return(a);
//        }
//        return null;
//    }
//
////    @Override
//    public AlunoDto atualizarSemestreAluno(int id, int semestre) {
//        final AlunoDto aluno = alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst()
//                .orElse(null);
//        if(aluno == null) {
//            return null;
//        }
//        aluno.setSemestreAtual(semestre);
//        return aluno;
//    }
//
////    @Override
//    public void deletarAluno(int id) throws NotFoundException {
//        Optional<AlunoDto> aluno = alunos
//                .stream()
//                .filter(it -> it.getId() == id)
//                .findFirst();
//        if(aluno.isPresent()) {
//            AlunoDto alunoRemovido = aluno.get();
//            alunos.remove(alunoRemovido);
//        }
//    }
//}
