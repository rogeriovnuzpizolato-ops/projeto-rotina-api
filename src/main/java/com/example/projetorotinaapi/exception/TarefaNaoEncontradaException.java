package com.example.projetorotinaapi.exception;

public class TarefaNaoEncontradaException extends RuntimeException{
    public TarefaNaoEncontradaException(long id){
        super("Tarefa não encontrada com id "+ id);
    }
}
