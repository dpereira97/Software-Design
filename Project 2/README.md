# Design de Software
**FC47806** - Paulo Santos  
**FC47878** - Rafael Melo  
**FC47888** - Diogo Pereira

## Como executar o projeto
- Importar como **projeto Maven** os projetos **bezirk-middleware-api** e **IoT** fornecidos, e aguardar que o *Maven* faça *build*.
- Converter o projeto IoT de *Maven* para um projeto AspectJ *(Right Click Project > Configure > Convert to AspectJ Project)*.
- Produtos da linha:
  - Foram fornecidos 4 produtos da nossa linha de produtos cuja configuração está pronta a ser construida com o *AspectJ Tools*.
  - Caso pretenda, existe também uma classe *CreateProductsMain* no pacote *main* que cria um produto à escolha do utilizador.
- Após construir a configuração com um produto escolhido, é possível correr o programa com a classe **Main** no pacote *main*.
  - Quando o programa for corrido, dependendo das *features* escolhidas, poderá de ter de definir valores limite para os sensores na consola.
  - Após a definição desses valores, será aberta uma interface gráfica que mostrará os eventos e alertas para o *Output*.
  - A interface gráfica contém dois botões que permitem ao utilizador **Criar** ou **Remover** alertas personalizados.
      - Exemplo da criação de um alerta:  
        **Mensagem Alerta:** Tomar Medicamento X  
        **Data de Começo:** 21-10-2010  
        **Hora de Começo:** 21:30  
        **Data de Fim:** 25-10-2010  
        **Hora de Fim:** 21:35  
        **Intervalo (minutos):** 1

***
##### **NOTA:**
O envio de SMS's encontra-se implementado.
Todavia, existe um limite máximo de 300 SMS's gratuitos para um dos números de telefone registados no Twilio (+351 91 215 3388 - Paulo Santos). Por isso, para prevenir o *spam* de SMS durante o desenvolvimento do projeto, a chamada ao envio dos SMS's encontra-se comentada.
Se pretender que os alertas sejam enviados por SMS, basta descomentar as *linhas 35 e 36, classe SMSHandler, pacote smsMessagingFeature*:
```Java
	public void sendMessages(SMSMessageEvent evento) {
		...
		// TODO: Uncomment the lines to send SMS to fc47806. WARNING: Max 300 SMSs after that I have to pay :'(
		// ImplementedSMSSender smsSender = new ImplementedSMSSender(bezirk);
		// smsSender.send(mensagem);
	}
```
