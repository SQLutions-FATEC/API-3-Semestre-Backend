# API-3-Semestre-Backend

## Ferramentas Necessárias

Para o desenvolvimento do backend, você vai precisar das seguintes ferramentas:

- Intellij IDEA
- Docker
- Java 21
- Git

## Clonando o Repositório

No IntelliJ IDEA, vamos clonar o nosso repositório na máquina. Procure pela opção “Clone repository”.

Depois de inserir a URL do repositório (https://github.com/SQLutions-FATEC/API-3-Semestre-Backend.git), clique em “Clone”.

## Configurando o Java 21

Certifique-se de que você tem o Java 21 instalado na sua máquina. É recomendável que seja utilizada a versão `azul-21.0.6`, disponibilizada no IntelliJ IDEA.

Para isso, clique na engrenagem no canto superior da tela, depois em “Project Structure”.

Olhe em “SDK” se você já possui a versão 21 do Java. Se não for o caso, vá em “Add SDK”, depois em “Download JDK”.

Selecione a versão 21, e a distribuição Azul Zulu Community, logo em seguida.

Clique em “Download”. Ao voltar para a aba de “Project Structure”, clique em “Apply”.

## Instalando o Docker

Para este semestre, vamos utilizar o Docker. Esta ferramenta vai subir um banco de dados isolado na sua máquina. Assim, evitamos qualquer dificuldade com problemas de compatibilidade. Para baixá-lo, siga a [documentação oficial](https://docs.docker.com/desktop/setup/install/windows-install/).

Depois de instalar o Docker na sua máquina, vá até o arquivo `compose.yaml` e clique nas setinhas do lado da linha 1 ou execute `docker compose up -d` no terminal.

## Executando o Spring

Para executar o Spring, basta executar a aplicação no IntelliJ.

Com o projeto sendo executado, visite a [documentação dos endpoints](http://localhost:8080/api/swagger-ui/index.html#/)

Bom trabalho, dev! Qualquer dúvida, comunique ela utilizando o grupo da sqlutions no Whatsapp!