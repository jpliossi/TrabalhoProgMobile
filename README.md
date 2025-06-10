# TrabalhoProgMobile 
# TodoList App

## Integrantes do Trabalho Final
- **José Paulo Ferreira Liossi, Rudimar Neves Junior, Vinicius Carneiro de Aguiar**

---

## Descrição Geral

O **TodoList App** é uma aplicação Android que permite aos usuários gerenciar suas tarefas de forma prática e eficiente. Desenvolvido com **Android Studio**, **Java** e **Firebase**, o app oferece funcionalidades como cadastro com foto, autenticação segura, adição de tarefas, conclusão e exclusão automática de tarefas concluídas.

---

## Papéis e Usuários

### Usuário comum (registrado):

- Pode se cadastrar com e-mail, senha e uma **foto de perfil**.
- Pode fazer login com e-mail e senha.
- Pode adicionar, visualizar e concluir tarefas.
- Pode deslogar da conta e acessar novamente com outro login.

---

## Requisitos Funcionais

### Entradas:
- E-mail válido
- Senha (mínimo 6 caracteres)
- Nome da tarefa
- Foto de perfil (durante o cadastro)

### Processamento:
- Autenticação via Firebase Authentication (com armazenamento seguro da senha - hash automático)
- Validação de campos obrigatórios
- Armazenamento de tarefas por usuário autenticado
- Upload e vínculo da foto ao perfil do usuário (Firebase Storage)

### Saídas:
- Lista de tarefas atualizada
- Remoção automática de tarefas concluídas
- Mensagens de confirmação ou erro

---

## Tratamento de Erros e Testes de Caixa Preta

O sistema aplica validações para garantir segurança e usabilidade:

- Tentativa de login com e-mail não cadastrado → mensagem de erro
- Campos vazios (e-mail, senha, nome da tarefa) → bloqueio da ação e aviso
- Entrada inválida em campos → erro tratado e aviso
- Feedback visual constante ao usuário para erros e ações concluídas

---

## Tela de Cadastro com Foto

- O usuário pode capturar ou selecionar uma **foto de perfil**.
- A imagem é armazenada no **Firebase Storage**.
- A foto fica associada ao ID único do usuário e pode ser exibida no app após o login.

---

## Segurança

- Senhas armazenadas com **hash automático via Firebase Authentication**
- Acesso aos dados apenas para usuários autenticados
- Regras de segurança aplicadas no **Firebase Database** e **Storage**

---

## Tecnologias Utilizadas

- **Android Studio** – ambiente de desenvolvimento
- **Java** – linguagem utilizada
- **Firebase Authentication** – autenticação de usuários
- **Firebase Realtime Database** – armazenamento de tarefas
- **Firebase Storage** – armazenamento da foto do usuário

---

## Como Executar o Projeto

### Pré-requisitos
- Android Studio instalado
- Conta no Firebase com projeto configurado

### Passos

1. Clone este repositório:
   git clone https://github.com/jpliossi/TrabalhoProgMobile.git
2. Abra o projeto no Android Studio
3. Sincronize com o Gradle
4. Conecte um dispositivo Android ou use um emulador
5. Compile e execute o projeto
