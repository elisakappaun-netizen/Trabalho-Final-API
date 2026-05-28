package br.com.serratec.trabalhofinalapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.trabalhofinalapi.config.MailConfig;
import br.com.serratec.trabalhofinalapi.model.Notificacao;
import br.com.serratec.trabalhofinalapi.repository.NotificacaoRepository;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository repository;

    @Autowired 
    private MailConfig mailConfig;

    public Notificacao inserir(Notificacao notificacao) {
        return repository.save(notificacao);
    }

    public Notificacao alterar(Notificacao notificacaoNova, Long id) {
        Notificacao notificaVelha = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação Não Encontrada"));
                notificaVelha.setNotificacao(notificacaoNova.getNotificacao());
                return repository.save(notificaVelha);
    }
    public List<Notificacao> listar(){
        return repository.findAll();
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }

    public void enviarNotificacao(String mensagem, String email){
        Notificacao notificacao = new Notificacao();

        notificacao.setNotificacao(mensagem);
        repository.save(notificacao);

        mailConfig.enviarEmail(mensagem, email,"O seu Status da Ordem de Serviço foi Alterado!");

    }
}
